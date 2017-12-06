
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabasev2.rcp.main.dialogs.PythonSettingsDialog;

public class PythonSettingsHandler {

  @Execute
  public void execute(Shell shell) {
    PythonSettingsDialog dialog = new PythonSettingsDialog(shell);
    dialog.setBlockOnOpen(true);
    dialog.open();
  }

}
