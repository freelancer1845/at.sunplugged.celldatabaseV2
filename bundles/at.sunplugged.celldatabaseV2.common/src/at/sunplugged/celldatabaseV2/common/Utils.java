package at.sunplugged.celldatabaseV2.common;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class Utils {

  public static void setPrefIfNotSet(IEclipsePreferences node, String pref, String value) {
    if (node.get(pref, "noneValue").equals("noneValue")) {
      node.put(pref, value);
    }
  }

  private Utils() {

  }
}
