package at.sunplugged.celldatabasev2.rcp.main.dialogs;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.PythonSettings;

public class PythonSettingsDialog extends AbstractSettingsDialog {



  public PythonSettingsDialog(Shell parentShell) {
    super(parentShell, "Python Settings", "Configure paths for Python and Python Scripts",
        PrefNodes.PYTHON);
  }



  @Override
  protected void createFields(Composite container) {
    createLabeledFileChooser(container, "Python Path", PythonSettings.PYTHON_PATH);
    createLabeledFileChooser(container, "LabView Python Script",
        PythonSettings.LABVIEW_IMPORT_SCRIPT_PATH);

    createLabeledFileChooser(container, "Plot Script Path", PythonSettings.PLOT_SCRIPT_PATH);

  }

}

