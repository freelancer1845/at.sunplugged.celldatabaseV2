package at.sunplugged.celldatabaseV2.common.settings;

import java.util.HashSet;
import java.util.Set;

public class JSONSettings {

  public static JSONSettings defaults() {
    JSONSettings set = new JSONSettings();
    set.setRecentDatabases(new HashSet<>());
    set.setLabviewImportDefaultSettings(LabviewImportDefaultSettings.createDefaults());
    return set;
  }



  private Set<String> recentDatabases;

  private LabviewImportDefaultSettings labviewImportDefaultSettings;



  public Set<String> getRecentDatabases() {
    if (recentDatabases == null) {
      this.recentDatabases = new HashSet<String>();
    }
    return recentDatabases;
  }

  public void setRecentDatabases(Set<String> recentDatabases) {
    this.recentDatabases = recentDatabases;
  }

  public LabviewImportDefaultSettings getLabviewImportDefaultSettings() {
    if (labviewImportDefaultSettings == null) {
      this.labviewImportDefaultSettings = LabviewImportDefaultSettings.createDefaults();
    }
    return labviewImportDefaultSettings;
  }

  public void setLabviewImportDefaultSettings(
      LabviewImportDefaultSettings labviewImportDefaultSettings) {
    this.labviewImportDefaultSettings = labviewImportDefaultSettings;
  }

}
