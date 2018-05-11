package at.sunplugged.celldatabaseV2.common.settings;

import java.util.HashSet;
import java.util.Set;

public class JSONSettings {

  public static JSONSettings defaults() {
    JSONSettings set = new JSONSettings();
    set.setRecentDatabases(new HashSet<>());
    set.getRecentDatabases().add("database.xmi");
    return set;
  }



  private Set<String> recentDatabases;



  public Set<String> getRecentDatabases() {
    return recentDatabases;
  }

  public void setRecentDatabases(Set<String> recentDatabases) {
    this.recentDatabases = recentDatabases;
  }

}
