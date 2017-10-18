package at.sunplugged.celldatabaseV2.common;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class PythonSettings {

  public static final String PYTHON_PATH = "pythonPath";

  public static final String LABVIEW_IMPORT_SCRIPT_PATH = "labViewImportScriptPath";

  private static final String DEFAULT_LABVIEW_IMPORT_SCRIPT_PATH = "";

  public static final String PLOT_SCRIPT_PATH = "plotScriptPyth";

  private static final String DEFAULT_PLOT_SCRIPT_PATH = "";


  public static void setDefaults(boolean overwrite) {
    IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(PrefNodes.PYTHON);
    if (overwrite == false) {
      Utils.setPrefIfNotSet(node, PythonSettings.PLOT_SCRIPT_PATH, DEFAULT_PLOT_SCRIPT_PATH);
      Utils.setPrefIfNotSet(node, PythonSettings.LABVIEW_IMPORT_SCRIPT_PATH,
          DEFAULT_LABVIEW_IMPORT_SCRIPT_PATH);
    }
  }

  private PythonSettings() {

  }
}
