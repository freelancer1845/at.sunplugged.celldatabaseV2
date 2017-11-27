
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.Update;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckForUpdatesHandler {
  private static final Logger LOG = LoggerFactory.getLogger(CheckForUpdatesHandler.class);



  private static final String REPOSITORY_LOC = ConfigurationScope.INSTANCE.getNode("UpdateHandler")
      .get("Repo",
          "file:///c:/Users/jasch/SunpluggedJob/at.sunplugged.celldatabaseV2/at.sunplugged.celldatabaseV2/releng/at.sunplugged.celldatabaseV2.update/target/repository");

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
    configureUpdate(operation, agent, monitor);


    // check for updates, this causes I/O
    final IStatus status = operation.resolveModal(monitor);

    LOG.debug("Resolution Result Message: " + operation.getResolutionResult()
        .getMessage());
    LOG.debug("Resolutin Details: " + operation.getResolutionDetails());
    LOG.debug("Updates found: " + Arrays.toString(operation.getPossibleUpdates()));
    LOG.debug("Status Message: " + status.getMessage());
    if (status.getException() != null) {
      LOG.debug("Status Error ", status.getException());
    }
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

  private UpdateOperation configureUpdate(final UpdateOperation operation, IProvisioningAgent agent,
      IProgressMonitor monitor) {
    // create uri and check for validity
    URI uri = null;
    try {
      uri = new URI(REPOSITORY_LOC);
    } catch (final URISyntaxException e) {
      LOG.error("Repository location is not valid.", e);
      return null;
    }

    IMetadataRepositoryManager manager =
        (IMetadataRepositoryManager) agent.getService(IMetadataRepositoryManager.SERVICE_NAME);
    IMetadataRepository repository;
    try {

      LOG.debug("Currently installed defining Plugin version: " + Platform.getProduct()
          .getDefiningBundle()
          .getVersion()
          .toString());
      repository = manager.loadRepository(uri, monitor);
      IQueryResult<IInstallableUnit> units =
          repository.query(QueryUtil.createIUAnyQuery(), monitor);
      for (IInstallableUnit unit : units) {
        LOG.debug("Unit: " + unit.getId() + ": " + unit.getVersion());
      }
      if (operation.getPossibleUpdates().length == 0) {
        LOG.debug("No Possible Updates found...");
      }
      for (Update pu : operation.getPossibleUpdates()) {
        LOG.debug("Possible Update: " + pu.toString());
      }
    } catch (ProvisionException | OperationCanceledException e) {
      LOG.debug("Failed in load repoistory debug code.", e);
    }


    // set location of artifact and metadata repo
    operation.getProvisioningContext()
        .setArtifactRepositories(new URI[] {uri});
    operation.getProvisioningContext()
        .setMetadataRepositories(new URI[] {uri});
    return operation;
  }



}
