
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.nio.file.Paths;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseServiceException;
import datamodel.Database;

public class ImportDatabaseHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ImportDatabaseHandler.class);


  @Execute
  public void execute(DatabaseService databaseService, Shell parent) {
    LOG.debug("Import database...");

    FileDialog fileDialog = new FileDialog(parent, SWT.OPEN);
    if (fileDialog.open() != null) {
      String importDatabasePath = Paths.get(fileDialog.getFilterPath(), fileDialog.getFileName())
          .toString();
      Job importJob = Job.create("Import Database", new ICoreRunnable() {

        @Override
        public void run(IProgressMonitor monitor) throws CoreException {
          try {
            databaseService.importDatabase(importDatabasePath);
          } catch (DatabaseServiceException e) {
            LOG.error("Failed to import database...", e);
          }
          LOG.debug("Importing database finished.");
        }
      });
      importJob.setPriority(Job.LONG);
      importJob.schedule();

    }
  }


  @CanExecute
  public boolean canExecute(@Optional Database database) {

    return database != null;
  }

}
