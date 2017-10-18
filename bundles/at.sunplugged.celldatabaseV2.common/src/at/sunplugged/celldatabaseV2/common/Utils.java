package at.sunplugged.celldatabaseV2.common;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class Utils {

  public static void setPrefIfNotSet(IEclipsePreferences node, String pref, String value) {
    if (node.get(pref, "noneValue").equals("noneValue")) {
      node.put(pref, value);
    }
  }

  public static void setDefaultSettings(boolean b) {
    RegexPatterns.setDefaults(b);
    PythonSettings.setDefaults(b);
  }

  private Utils() {

  }
}
