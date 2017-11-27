
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckForUpdatesHandler {
  private static final Logger LOG = LoggerFactory.getLogger(CheckForUpdatesHandler.class);



  private static final String REPOSITORY_LOC =
      "C:/Users/jasch/SunpluggedJob/at.sunplugged.celldatabaseV2/at.sunplugged.celldatabaseV2/releng/at.sunplugged.celldatabaseV2.update/target/repository";


  @Execute
  public void execute(final IProvisioningAgent agent, final Shell shell, final UISynchronize sync) {
    Job j = new Job("Update Job") {
      @Override
      protected IStatus run(final IProgressMonitor monitor) {
        return checkForUpdates(agent, shell, sync, monitor);
      }
    };
    j.schedule();
  }


  private IStatus checkForUpdates(final IProvisioningAgent agent, final Shell shell,
      final UISynchronize sync, IProgressMonitor monitor) {

    // configure update operation
    final ProvisioningSession session = new ProvisioningSession(agent);
    final UpdateOperation operation = new UpdateOperation(session);
    configureUpdate(operation);

    // check for updates, this causes I/O
    final IStatus status = operation.resolveModal(monitor);

    LOG.debug("Resolution Result Message: " + operation.getResolutionResult()
        .getMessage());
    LOG.debug("Resolutin Details: " + operation.getResolutionDetails());

    // failed to find updates (inform user and exit)
    if (status.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE) {
      showMessage(shell, sync);
      return Status.CANCEL_STATUS;
    }

    // run installation
    final ProvisioningJob provisioningJob = operation.getProvisioningJob(monitor);
    // updates cannot run from within Eclipse IDE!!!
    if (provisioningJob == null) {
      LOG.error("Trying to update from the Eclipse IDE? This won't work!");
      return Status.CANCEL_STATUS;
    }
    configureProvisioningJob(provisioningJob, shell, sync);

    provisioningJob.schedule();
    return Status.OK_STATUS;

  }

  private void configureProvisioningJob(ProvisioningJob provisioningJob, final Shell shell,
      final UISynchronize sync) {

    // register a job change listener to track
    // installation progress and notify user upon success
    provisioningJob.addJobChangeListener(new JobChangeAdapter() {
      @Override
      public void done(IJobChangeEvent event) {
        if (event.getResult()
            .isOK()) {
          sync.syncExec(new Runnable() {

            @Override
            public void run() {
              MessageDialog.openInformation(shell, "Updates installed",
                  "Updates have been installed. Restart the program!");
            }
          });

        }
        super.done(event);
      }
    });

  }

  private void showMessage(final Shell parent, final UISynchronize sync) {
    sync.syncExec(new Runnable() {

      @Override
      public void run() {
        MessageDialog.openWarning(parent, "No update",
            "No updates for the current installation have been found.");
      }
    });
  }

  private UpdateOperation configureUpdate(final UpdateOperation operation) {
    // create uri and check for validity
    URI uri = null;
    try {
      uri = new URI(REPOSITORY_LOC);
    } catch (final URISyntaxException e) {
      LOG.error("Repository location is not valid.", e);
      return null;
    }

    // set location of artifact and metadata repo
    operation.getProvisioningContext()
        .setArtifactRepositories(new URI[] {uri});
    operation.getProvisioningContext()
        .setMetadataRepositories(new URI[] {uri});
    return operation;
  }



}
