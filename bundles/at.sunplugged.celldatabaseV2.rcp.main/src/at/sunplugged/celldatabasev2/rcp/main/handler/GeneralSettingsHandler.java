
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabasev2.rcp.main.dialogs.GeneralSettingsDialog;

public class GeneralSettingsHandler {
  @Execute
  public void execute(Shell shell) {
    GeneralSettingsDialog dialog = new GeneralSettingsDialog(shell);
    dialog.setBlockOnOpen(true);
    dialog.open();
  }

}
