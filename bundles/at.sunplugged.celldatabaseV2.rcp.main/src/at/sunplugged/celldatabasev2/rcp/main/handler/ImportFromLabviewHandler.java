
package at.sunplugged.celldatabasev2.rcp.main.handler;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabaseV2.labviewimport.ui.wizard.LabviewImportWizard;
import datamodel.CellGroup;

public class ImportFromLabviewHandler {
  @Execute
  public void execute(Shell shell, @Named(IServiceConstants.ACTIVE_SELECTION) CellGroup group,
      EditingDomain domain, CommandStack stack) {

    LabviewImportWizard wizard = new LabviewImportWizard();
    WizardDialog dialog = new WizardDialog(shell, wizard);
    if (dialog.open() == Window.OK) {
      Command cmd = AddCommand.create(domain, group, null, wizard.getCellResults());
      stack.execute(cmd);
    }
  }

  @CanExecute
  public boolean canExecute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) CellGroup group) {
    return group != null;
  }

}
