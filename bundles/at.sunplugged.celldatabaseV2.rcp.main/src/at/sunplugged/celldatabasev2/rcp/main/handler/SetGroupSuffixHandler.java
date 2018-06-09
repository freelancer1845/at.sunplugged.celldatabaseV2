
package at.sunplugged.celldatabasev2.rcp.main.handler;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import datamodel.CellGroup;
import datamodel.DatamodelPackage;

public class SetGroupSuffixHandler {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) CellGroup group, Shell parentShell,
      EditingDomain domain, CommandStack stack) {
    InputDialog dialog = new InputDialog(parentShell, "Edit Suffix",
        "Set Name suffix for group \"" + group.getName() + "\"", group.getNameSuffix(), null);
    if (dialog.open() == Window.OK) {
      String newSuffix = dialog.getValue();
      if (newSuffix.equals(group.getName()) == false) {
        Command cmd = SetCommand.create(domain, group,
            DatamodelPackage.Literals.CELL_GROUP__NAME_SUFFIX, newSuffix);
        stack.execute(cmd);
      }
    }

  }

  @Evaluate
  public boolean isVisible(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    if (selection instanceof CellGroup) {
      return true;
    }

    return false;
  }

}
