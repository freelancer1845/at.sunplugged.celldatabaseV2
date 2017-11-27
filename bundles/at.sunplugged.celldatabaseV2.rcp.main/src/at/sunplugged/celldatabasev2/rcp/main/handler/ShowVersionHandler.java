
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ShowVersionHandler {
  @Execute
  public void execute(Shell shell) {
    MessageDialog.openInformation(shell, "Version", "Current Version: " + Platform.getProduct()
        .getDefiningBundle()
        .getVersion());
  }

}
