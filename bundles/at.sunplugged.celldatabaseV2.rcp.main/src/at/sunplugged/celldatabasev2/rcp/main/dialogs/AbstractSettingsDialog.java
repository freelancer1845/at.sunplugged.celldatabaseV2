package at.sunplugged.celldatabasev2.rcp.main.dialogs;

import java.util.LinkedList;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class AbstractSettingsDialog extends TitleAreaDialog {



  protected Logger LOG = LoggerFactory.getLogger(getClass());

  private GridDataFactory gridDataFactory;

  private IEclipsePreferences preferences;

  private final String title;

  private final String message;

  private LinkedList<PrefChangeCommand> changes = new LinkedList<>();

  public AbstractSettingsDialog(Shell parentShell, String title, String message, String node) {
    super(parentShell);
    setBlockOnOpen(true);
    gridDataFactory = GridDataFactory.fillDefaults();
    gridDataFactory.grab(true, false);

    this.title = title;
    this.message = message;
    this.preferences = ConfigurationScope.INSTANCE.getNode(node);
  }


  @Override
  public void create() {
    super.create();
    setTitle(title);
    setMessage(message, IMessageProvider.INFORMATION);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite area = (Composite) super.createDialogArea(parent);

    Composite container = new Composite(area, SWT.NONE);
    container.setLayoutData(gridDataFactory.create());

    GridLayout layout = new GridLayout(2, false);
    container.setLayout(layout);

    createFields(container);
    return area;
  }

  protected abstract void createFields(Composite container);

  protected Button createLabeledCheckbox(Composite parent, String labelName, String preference) {
    Label label = new Label(parent, SWT.NONE);
    label.setText(labelName);
    {
      GridData gd = gridDataFactory.create();
      gd.horizontalAlignment = SWT.LEFT;
      gd.verticalAlignment = SWT.CENTER;
      gd.grabExcessHorizontalSpace = false;
      label.setLayoutData(gd);
    }
    Button cb = new Button(parent, SWT.CHECK);
    cb.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

    cb.setSelection(preferences.getBoolean(preference, false));
    cb.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

        doPrefChange(preference, cb.getSelection());
      }

    });

    return cb;
  }

  protected Text createLabeledFileChooser(Composite parent, String labelName, String preference) {
    Label label = new Label(parent, SWT.NONE);
    label.setText(labelName);
    {
      GridData gd = gridDataFactory.create();
      gd.horizontalAlignment = SWT.LEFT;
      gd.verticalAlignment = SWT.CENTER;
      gd.grabExcessHorizontalSpace = false;
      label.setLayoutData(gd);
    }
    Composite container = new Composite(parent, SWT.NONE);
    {
      GridData gd = gridDataFactory.create();
      container.setLayoutData(gd);
      container.setLayout(new GridLayout(2, false));
    }
    Text text = new Text(container, SWT.BORDER);
    text.setText(preferences.get(preference, ""));
    text.setLayoutData(gridDataFactory.create());
    text.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        doPrefChange(preference, text.getText());
      }
    });
    Button button = new Button(container, SWT.PUSH);
    button.setText("Browse");
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        FileDialog fd = new FileDialog(parent.getShell(), SWT.OPEN);
        fd.setFilterPath(preferences.get(preference, "C:\\"));
        String path = fd.open();
        if (path != null) {
          text.setText(path);
        }
      }
    });
    GridData gd = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
    gd.widthHint = 75;
    button.setLayoutData(gd);

    return text;
  }

  protected Text createLabeledDirectoryChooser(Composite parent, String labelName,
      String preference) {
    Label label = new Label(parent, SWT.NONE);
    label.setText(labelName);
    {
      GridData gd = gridDataFactory.create();
      gd.horizontalAlignment = SWT.LEFT;
      gd.verticalAlignment = SWT.CENTER;
      gd.grabExcessHorizontalSpace = false;
      label.setLayoutData(gd);
    }
    Composite container = new Composite(parent, SWT.NONE);
    {
      GridData gd = gridDataFactory.create();
      container.setLayoutData(gd);
      container.setLayout(new GridLayout(2, false));
    }
    Text text = new Text(container, SWT.BORDER);
    text.setText(preferences.get(preference, ""));
    text.setLayoutData(gridDataFactory.create());
    text.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        doPrefChange(preference, text.getText());
      }
    });
    Button button = new Button(container, SWT.PUSH);
    button.setText("Browse");
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        DirectoryDialog dd = new DirectoryDialog(getShell());
        dd.setFilterPath(preferences.get(preference, "C:\\"));
        String path = dd.open();
        if (path != null) {
          text.setText(path);
        }
      }
    });
    GridData gd = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
    gd.widthHint = 75;
    button.setLayoutData(gd);

    return text;
  }

  @Override
  protected void okPressed() {
    try {
      preferences.flush();

    } catch (BackingStoreException e) {

    }
    super.okPressed();
  }

  @Override
  protected void cancelPressed() {
    changes.descendingIterator().forEachRemaining(item -> item.undo());

    super.cancelPressed();


  }

  public IEclipsePreferences getPreferences() {
    return preferences;
  }

  protected void doPrefChange(String setting, String newValue) {
    PrefChangeCommand cmd =
        new PrefChangeCommand(preferences, setting, preferences.get(setting, ""), newValue);
    preferences.put(setting, newValue);
    changes.add(cmd);
  }

  protected void doPrefChange(String setting, boolean newValue) {
    doPrefChange(setting, String.valueOf(newValue));
  }

  private final class PrefChangeCommand {

    private final IEclipsePreferences preferences;

    private final String setting;

    private final String oldValue;

    private final String newValue;



    public PrefChangeCommand(IEclipsePreferences preferences, String setting, String oldValue,
        String newValue) {
      this.preferences = preferences;
      this.setting = setting;
      this.oldValue = oldValue;
      this.newValue = newValue;
    }



    public void undo() {
      preferences.put(setting, oldValue);
    }

    public void redo() {
      preferences.put(setting, newValue);
    }


  }
}
