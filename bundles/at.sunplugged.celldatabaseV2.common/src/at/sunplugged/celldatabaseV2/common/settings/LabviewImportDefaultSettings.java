package at.sunplugged.celldatabaseV2.common.settings;

public class LabviewImportDefaultSettings {

  private static final int DEFAULT_CELLS = 1;

  private static final double DEFAULT_POWER = 960.0;

  private static final double DEFAULT_AREA = 1.6;

  public static LabviewImportDefaultSettings createDefaults() {
    LabviewImportDefaultSettings def = new LabviewImportDefaultSettings();
    def.setArea(DEFAULT_AREA);
    def.setPower(DEFAULT_POWER);
    def.setCells(DEFAULT_CELLS);
    return def;
  }

  public LabviewImportDefaultSettings() {}

  private Double area;

  private Double power;

  private Integer cells;

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



}
