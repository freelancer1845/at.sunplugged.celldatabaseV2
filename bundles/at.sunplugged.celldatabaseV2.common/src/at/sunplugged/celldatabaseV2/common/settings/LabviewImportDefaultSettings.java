package at.sunplugged.celldatabaseV2.common.settings;

public class LabviewImportDefaultSettings {

  private static final String DEFAULT_LAST_IMPORT_PATH = "";

  private static final boolean DEFAULT_USE_IMPORT_PATH = false;

  private static final int DEFAULT_CELLS = 1;

  private static final double DEFAULT_POWER = 960.0;

  private static final double DEFAULT_AREA = 1.6;

  private static final String DEFAULT_IMPORT_PATH = DEFAULT_LAST_IMPORT_PATH;

  public static LabviewImportDefaultSettings createDefaults() {
    LabviewImportDefaultSettings def = new LabviewImportDefaultSettings();
    return def;
  }

  public LabviewImportDefaultSettings() {}

  private Double area = DEFAULT_AREA;

  private Double power = DEFAULT_POWER;

  private Integer cells = DEFAULT_CELLS;

  private String importPath = DEFAULT_IMPORT_PATH;

  private Boolean useImportPath = DEFAULT_USE_IMPORT_PATH;

  private String lastImportPath = DEFAULT_LAST_IMPORT_PATH;

  public Double getArea() {
    return area;
  }

  public void setArea(Double area) {
    this.area = area;
  }

  public Double getPower() {
    return power;
  }

  public void setPower(Double power) {
    this.power = power;
  }

  public Integer getCells() {
    return cells;
  }

  public void setCells(Integer cells) {
    this.cells = cells;
  }

  public String getImportPath() {
    return importPath;
  }

  public void setImportPath(String importPath) {
    this.importPath = importPath;
  }

  public Boolean isUseImportPath() {
    return this.useImportPath;
  }

  public void setUseImportPath(Boolean useImportPath) {
    this.useImportPath = useImportPath;
  }


  public String getLastImportPath() {
    return lastImportPath;
  }

  public void setLastImportPath(String lastImportPath) {
    this.lastImportPath = lastImportPath;
  }



}
