
package at.sunplugged.celldatabasev2.rcp.main.handler;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabaseV2.labviewimport.ui.wizard.LabviewImportWizard;
import datamodel.CellGroup;

public class ImportFromLabviewHandler {
  @Execute
  public void execute(Shell shell, @Named(IServiceConstants.ACTIVE_SELECTION) CellGroup group) {
    LabviewImportWizard wizard = new LabviewImportWizard();
    WizardDialog dialog = new WizardDialog(shell, wizard);
    if (dialog.open() == Window.OK) {
      group.getCellResults().addAll(wizard.getCellResults());
    }
  }

}
