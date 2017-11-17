
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.nio.file.Paths;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseServiceException;

public class SaveAsHandler {

  private static final Logger LOG = LoggerFactory.getLogger(SaveAsHandler.class);


  @Execute
  public void execute(Shell parent, DatabaseService databaseService, EPartService partService) {
    LOG.debug("Saving Database into (new) file...");
    MPart viewerPart = partService.getParts()
        .stream()
        .filter(part -> part.getElementId()
            .equals("at.sunplugged.celldatabasev2.rcp.main.part.modelviewer"))
        .findAny()
        .orElse(null);
    FileDialog fileDialog = new FileDialog(parent, SWT.SAVE);

    if (fileDialog.open() != null) {
      String savePath = Paths.get(fileDialog.getFilterPath(), fileDialog.getFileName())
          .toString();
      Job saveJob = Job.create("Save Database Job", new ICoreRunnable() {

        @Override
        public void run(IProgressMonitor monitor) throws CoreException {
          try {
            databaseService.saveDatabase(savePath);
            if (viewerPart != null) {
              viewerPart.setDirty(false);
            }
            LOG.debug("Database saved.");
          } catch (DatabaseServiceException e) {
            LOG.error("Database saving failed...", e);
          }
        }
      });
      saveJob.setPriority(Job.LONG);
      saveJob.schedule();
    }
  }

}
