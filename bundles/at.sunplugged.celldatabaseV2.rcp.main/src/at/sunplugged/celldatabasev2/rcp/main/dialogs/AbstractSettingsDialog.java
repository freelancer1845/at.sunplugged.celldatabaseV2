package at.sunplugged.celldatabasev2.rcp.main.dialogs;

import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Function;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.settings.JSONSettings;
import at.sunplugged.celldatabaseV2.common.settings.SettingsAccessor;



public abstract class AbstractSettingsDialog extends TitleAreaDialog {



  protected Logger LOG = LoggerFactory.getLogger(getClass());

  private GridDataFactory gridDataFactory;

  private JSONSettings settings;

  private final String title;

  private final String message;

  private LinkedList<PrefChangeCommand<?>> changes = new LinkedList<>();

  public AbstractSettingsDialog(Shell parentShell, String title, String message) {
    super(parentShell);
    setBlockOnOpen(true);
    gridDataFactory = GridDataFactory.fillDefaults();
    gridDataFactory.grab(true, false);

    this.title = title;
    this.message = message;
    this.settings = SettingsAccessor.getInstance().getSettings();
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

    GridLayout layout = new GridLayout(1, false);
    container.setLayout(layout);

    createFields(container);
    return area;
  }

  protected abstract void createFields(Composite container);

  protected Button createLabeledCheckbox(Composite parent, String labelName,
      Function<JSONSettings, Boolean> getter, BiConsumer<JSONSettings, Boolean> setter) {
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

    cb.setSelection(getter.apply(settings));

    cb.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

        doPrefChange(setter, cb.getSelection(), !cb.getSelection());
      }

    });

    return cb;
  }

  protected Text createLabeledFileChooser(Composite parent, String labelName,
      Function<JSONSettings, String> getter, BiConsumer<JSONSettings, String> setter) {
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
    text.setText(getter.apply(settings));
    text.setLayoutData(gridDataFactory.create());
    text.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        doPrefChange(setter, text.getText(), getter.apply(settings));
      }
    });
    Button button = new Button(container, SWT.PUSH);
    button.setText("Browse");
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        FileDialog fd = new FileDialog(parent.getShell(), SWT.OPEN);
        fd.setFilterPath(getter.apply(settings));
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
      Function<JSONSettings, String> getter, BiConsumer<JSONSettings, String> setter) {
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
    text.setText(getter.apply(settings));
    text.setLayoutData(gridDataFactory.create());
    text.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        doPrefChange(setter, text.getText(), getter.apply(settings));
      }
    });
    Button button = new Button(container, SWT.PUSH);
    button.setText("Browse");
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        DirectoryDialog dd = new DirectoryDialog(getShell());
        dd.setFilterPath(getter.apply(settings));
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
    SettingsAccessor.getInstance().flushSettingsIgnore();

    super.okPressed();
  }

  @Override
  protected void cancelPressed() {
    changes.descendingIterator().forEachRemaining(item -> item.undo());

    super.cancelPressed();


  }


  protected <T> void doPrefChange(BiConsumer<JSONSettings, T> setter, T newValue, T oldValue) {
    PrefChangeCommand<T> cmd = new PrefChangeCommand<T>(setter, oldValue, newValue);
    cmd.redo();
    changes.add(cmd);
  }


  private final class PrefChangeCommand<T> {

    private final BiConsumer<JSONSettings, T> setter;

    private final T oldValue;

    private final T newValue;



    public PrefChangeCommand(BiConsumer<JSONSettings, T> setter, T oldValue, T newValue) {
      this.setter = setter;
      this.oldValue = oldValue;
      this.newValue = newValue;
    }



    public void undo() {
      setter.accept(settings, oldValue);
    }

    public void redo() {
      setter.accept(settings, newValue);
    }


  }
}
