package at.sunplugged.celldatabaseV2.common;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class RegexPatterns {

  public static final String LABVIEW_DARK = "labview.dark";

  public static final String LABVIEW_LIGHT = "labview.light";

  public static final String LABVIEW_ENDING = "labview.ending";

  private static final String DEFAULT_LABVIEW_ENDING = "-[01]\\.txt$";

  public static final String LABVIEW_FILE = "labview.file";

  private static final String DEFAULT_LABVIEW_FILE = "^[0-9]+-[0-9]+[a-zA-Z]+_[0-9]+-[01]\\.txt$";

  public static final String LABVIEW_GROUP_COMPLEMENT = "labview.group.complement";

  private static final String DEFAULT_LABVIEW_GROUPCOMPLMENET = "_[0-9]+$";

  public static void setDefaults(boolean overwrite) {
    IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(PrefNodes.REGEX_PATTERNS);

    if (overwrite == false) {
      Utils.setPrefIfNotSet(node, LABVIEW_ENDING, DEFAULT_LABVIEW_ENDING);
      Utils.setPrefIfNotSet(node, LABVIEW_FILE, DEFAULT_LABVIEW_FILE);
      Utils.setPrefIfNotSet(node, LABVIEW_GROUP_COMPLEMENT, DEFAULT_LABVIEW_GROUPCOMPLMENET);
    } else {
      node.put(LABVIEW_GROUP_COMPLEMENT, DEFAULT_LABVIEW_GROUPCOMPLMENET);
    }

  }

  private RegexPatterns() {

  }

}
