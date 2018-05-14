
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseServiceException;
import at.sunplugged.celldatabasev2.rcp.main.parts.ModelViewerPart;
import at.sunplugged.celldatabasev2.rcp.main.wizards.StartupWizard;
import datamodel.Database;

public class SwitchDatabaseHandler {
  @Execute
  public void execute(EPartService partService, Shell shell, DatabaseService databaseService,
      IEclipseContext context) throws DatabaseServiceException {
    MPart modelPart =
        partService.findPart("at.sunplugged.celldatabasev2.rcp.main.part.modelviewer");
    if (modelPart.isDirty() == true) {
      int answer = MessageDialog.open(SWT.ICON_QUESTION, shell, "Unsaved changes",
          "Unsaved changes to current database. Save?", SWT.NONE,
          new String[] {"Yes", "No", "Cancel"});
      if (answer == 0) {
        saveDatabase(partService, modelPart);
        switchDialog(shell, databaseService, context, modelPart);
      } else if (answer == 1) {
        switchDialog(shell, databaseService, context, modelPart);
      } else {
        return;
      }
    } else {
      switchDialog(shell, databaseService, context, modelPart);
    }
  }



  private void saveDatabase(EPartService partService, MPart modelPart) {
    partService.savePart(modelPart, false);
  }

  private void switchDialog(Shell shell, DatabaseService databaseService, IEclipseContext context,
      MPart modelPart) throws DatabaseServiceException {
    StartupWizard startupWizard = new StartupWizard();
    WizardDialog dialog = new WizardDialog(shell, startupWizard);
    if (dialog.open() == Window.OK) {
      databaseService.openDatabase(startupWizard.getDatabasePath());
      while (context.getActiveChild() != null) {
        context = context.getActiveChild();
      }
      context.set(Database.class, databaseService.getDatabase());
      ((ModelViewerPart) modelPart.getObject()).setNewDatabase(databaseService.getDatabase());
      modelPart.setDirty(false);
    }

  }

}
