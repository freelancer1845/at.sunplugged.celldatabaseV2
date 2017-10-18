
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.List;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabaseV2.sprodaccess.api.SprodAccessService;
import at.sunplugged.celldatabaseV2.sprodaccess.ui.wizard.SprodImportWizard;
import datamodel.CellGroup;
import datamodel.CellResult;

public class ImportFromSprodHandler {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) CellGroup cellGroup,
      Shell parentShell, SprodAccessService sprodAccessService, EditingDomain domain,
      CommandStack stack) {

    SprodImportWizard wizard = new SprodImportWizard(sprodAccessService);

    WizardDialog dialog = new WizardDialog(parentShell, wizard);
    if (dialog.open() == Window.OK) {
      List<CellResult> cellResults = wizard.getCellResults();

      Command cmd = AddCommand.create(domain, cellGroup, null, cellResults);
      stack.execute(cmd);
    }



  }


  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    return selection instanceof CellGroup;
  }

}
