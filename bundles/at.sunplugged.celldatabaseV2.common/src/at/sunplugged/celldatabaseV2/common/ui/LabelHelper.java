package at.sunplugged.celldatabaseV2.common.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class LabelHelper {

  public static CLabel createErrorLabel(Composite parent, String message) {
    CLabel label = new CLabel(parent, SWT.NONE);

    label.setText("Error: " + message);
    label.setImage(Display.getDefault().getSystemImage(SWT.ICON_ERROR));

    if (parent.getLayout() instanceof GridLayout) {
      label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
    }

    return label;
  }

  public static Composite createErrorComposite(Composite parent, String message) {
    Composite error = new Composite(parent, SWT.NONE);
    error.setLayout(new GridLayout());
    createErrorLabel(error, message);
    return error;
  }


  private LabelHelper() {
    // TODO Auto-generated constructor stub
  }

}
