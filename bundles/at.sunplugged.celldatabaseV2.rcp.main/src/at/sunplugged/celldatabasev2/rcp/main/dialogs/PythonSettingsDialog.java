package at.sunplugged.celldatabasev2.rcp.main.dialogs;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.PythonSettings;

public class PythonSettingsDialog extends TitleAreaDialog {

  private GridDataFactory gridDataFactory;

  private IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode(PrefNodes.PYTHON);

  private Text pythonPathText;

  private String pythonPath;

  private Text labViewScriptPathText;

  private String labViewScriptPath;

  private Text plotScriptPathText;

  private String plotScriptPath;

  public PythonSettingsDialog(Shell parentShell) {
    super(parentShell);
    setBlockOnOpen(true);
    gridDataFactory = GridDataFactory.fillDefaults();
    gridDataFactory.grab(true, false);
  }

  @Override
  public void create() {
    super.create();
    setTitle("Python Settings");
    setMessage("Configure paths for Python and Python Scripts", IMessageProvider.INFORMATION);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite area = (Composite) super.createDialogArea(parent);

    Composite container = new Composite(area, SWT.NONE);
    container.setLayoutData(gridDataFactory.create());

    GridLayout layout = new GridLayout(2, false);
    container.setLayout(layout);

    pythonPathText = createLabeledFileChooser(container, "Python Path", PythonSettings.PYTHON_PATH);
    labViewScriptPathText = createLabeledFileChooser(container, "LabView Python Script",
        PythonSettings.LABVIEW_IMPORT_SCRIPT_PATH);
    plotScriptPathText =
        createLabeledFileChooser(container, "Plot Script Path", PythonSettings.PLOT_SCRIPT_PATH);

    return area;
  }

  private Text createLabeledFileChooser(Composite parent, String labelName, String preference) {
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

  @Override
  protected void okPressed() {
    pythonPath = pythonPathText.getText();
    labViewScriptPath = labViewScriptPathText.getText();
    plotScriptPath = plotScriptPathText.getText();
    super.okPressed();
  }

  public String getPythonPathText() {
    return pythonPath;
  }

  public String getLabViewScriptPathText() {
    return labViewScriptPath;
  }

  public String getPlotScriptPathText() {
    return plotScriptPath;
  }

  public IEclipsePreferences getPreferences() {
    return preferences;
  }

}

