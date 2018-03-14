package at.sunplugged.celldatabaseV2.common;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class VariableInputDialog extends TitleAreaDialog {

  private final String title;

  private final String message;

  private final String[] fieldLabels;

  private final String[] intialValues;

  private final IInputValidator[] validators;

  private final Boolean[] completeFields;

  private String[] values;

  private Text[] inputTexts;



  public VariableInputDialog(Shell parentShell, String title, String message, String[] fieldLables,
      String[] intialValues, IInputValidator[] validators) {
    super(parentShell);
    this.title = title;
    this.message = message;
    this.fieldLabels = fieldLables;
    this.intialValues = intialValues;
    this.inputTexts = new Text[fieldLabels.length];
    if (validators == null) {
      this.validators = new IInputValidator[fieldLables.length];
      for (int i = 0; i < this.validators.length; i++) {
        this.validators[i] = new IInputValidator() {

          @Override
          public String isValid(String newText) {
            return null;
          }
        };
      }
    } else {
      this.validators = validators;
    }
    this.values = new String[fieldLables.length];
    this.completeFields = Stream.generate(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        return new Boolean(false);
      }
    }).limit(fieldLables.length).toArray(Boolean[]::new);
  }

  @Override
  public void create() {
    super.create();
    setTitle(title);
    setMessage(message);
    getButton(IDialogConstants.OK_ID).setEnabled(false);

    Arrays.stream(inputTexts).forEach(text -> text.notifyListeners(SWT.Modify, new Event()));
  }



  @Override
  protected Control createDialogArea(Composite parent) {

    Composite composite = new Composite(parent, SWT.NONE);

    composite.setLayout(new GridLayout(2, false));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    for (int i = 0; i < fieldLabels.length; i++) {
      Label label = new Label(composite, SWT.NONE);
      label.setText(fieldLabels[i]);
      label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

      final Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);
      text.setText(intialValues[i]);
      text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

      final int index = i;
      text.addModifyListener(new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
          if (validators.length > index) {
            String error = validators[index].isValid(text.getText());
            if (error == null) {
              text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
              text.setToolTipText("");
              values[index] = text.getText();
              updateComplete(index, true);
            } else {
              text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
              text.setToolTipText(error);
              values[index] = null;
              updateComplete(index, false);
            }
          }
        }
      });
      text.addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent e) {
          Display.getDefault().asyncExec(new Runnable() {
            public void run() {
              text.setSelection(0, text.getText().length());
            }
          });
        }

        public void focusLost(FocusEvent e) {
          text.clearSelection();
        }
      });


      inputTexts[i] = text;
    }

    return composite;
  }

  public String[] getValues() {
    return values;
  }


  private void updateComplete(int index, boolean value) {
    this.completeFields[index] = value;
    if (Arrays.stream(completeFields).anyMatch(field -> field == false)) {
      getButton(IDialogConstants.OK_ID).setEnabled(false);
    } else {
      getButton(IDialogConstants.OK_ID).setEnabled(true);
    }
  }

  public static class IntegerInputValidator implements IInputValidator {

    private final int min;

    private final int max;


    public IntegerInputValidator() {
      this.min = Integer.MIN_VALUE;
      this.max = Integer.MAX_VALUE;
    }


    public IntegerInputValidator(int min, int max) {
      this.min = min;
      this.max = max;
    }

    @Override
    public String isValid(String newText) {
      try {
        int value = Integer.valueOf(newText);
        if (value < min) {
          return "Input < min(" + min + ")";
        } else if (value > max) {
          return "Input > max(" + max + ")";
        } else {
          return null;
        }
      } catch (NumberFormatException e) {
        return "Input not formatted correct";
      }
    }


  }

  public static class DoubleInputValidator implements IInputValidator {

    private final double max;

    private final double min;

    public DoubleInputValidator() {
      max = Double.MAX_VALUE;
      min = Double.MIN_VALUE;
    }

    public DoubleInputValidator(double min, double max) {
      this.min = min;
      this.max = max;
    }


    @Override
    public String isValid(String newText) {
      try {
        double value = Double.valueOf(newText);
        if (value < min) {
          return "Input < min(" + min + ")";
        } else if (value > max) {
          return "Input > max(" + max + ")";
        } else {
          return null;
        }
      } catch (NumberFormatException e) {
        return "Input not formatted correct";
      }
    }

  }

}
