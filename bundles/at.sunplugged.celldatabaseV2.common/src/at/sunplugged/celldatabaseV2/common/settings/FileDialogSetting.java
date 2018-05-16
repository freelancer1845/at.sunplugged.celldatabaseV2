package at.sunplugged.celldatabaseV2.common.settings;

public class FileDialogSetting {

  private Boolean useLastPath = true;

  private String lastPath = "";

  private String defaultPath = "";

  private String[] filterExtensions = {};

  private String[] filterNames = {};


  public FileDialogSetting() {}


  public Boolean getUseLastPath() {
    return useLastPath;
  }

  public Boolean isUseLastPath() {
    return useLastPath;
  }


  public void setUseLastPath(Boolean useLastPath) {
    this.useLastPath = useLastPath;
  }


  public String getLastPath() {
    return lastPath;
  }


  public void setLastPath(String lastPath) {
    this.lastPath = lastPath;
  }


  public String getDefaultPath() {
    return defaultPath;
  }


  public void setDefaultPath(String defaultPath) {
    this.defaultPath = defaultPath;
  }


  public String[] getFilterExtensions() {
    return this.filterExtensions;
  }

  public void setFilterExtensions(String[] filterExtensions) {
    this.filterExtensions = filterExtensions;
  }

  public String[] getFilterNames() {
    return filterNames;
  }

  public void setFilterNames(String[] filterNames) {
    this.filterNames = filterNames;
  }


}
