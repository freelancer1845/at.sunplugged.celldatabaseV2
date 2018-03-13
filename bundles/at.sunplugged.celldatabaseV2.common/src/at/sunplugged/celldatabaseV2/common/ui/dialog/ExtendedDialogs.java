package at.sunplugged.celldatabaseV2.common.ui.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class ExtendedDialogs {


  public static int openLabviewCalculatorErrorDialog(Shell shell, String name, String error) {
    return MessageDialog.open(MessageDialog.ERROR, shell, "Error at " + name,
        "Automatic calculation failed (" + error + ").\nChoose what to do.", SWT.NONE,
        new String[] {"See Data", "Select Fit Ranges", "Exclude", "Cancle Evaluation"});
  }

  private ExtendedDialogs() {
    // TODO Auto-generated constructor stub
  }

}
