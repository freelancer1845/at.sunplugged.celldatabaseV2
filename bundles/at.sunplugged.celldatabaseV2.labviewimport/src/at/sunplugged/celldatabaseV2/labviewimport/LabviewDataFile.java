package at.sunplugged.celldatabaseV2.labviewimport;

public final class LabviewDataFile {

  private final String absolutPathLight;

  private final String nameLight;

  private final String absolutPathDark;

  private final String nameDark;

  private final String name;

  private Double area;

  private Double powerInput;

  public LabviewDataFile(String name, String absolutPathLight, String nameLight,
      String absolutPathDark, String nameDark) {
    super();
    this.name = name;
    this.absolutPathLight = absolutPathLight;
    this.nameLight = nameLight;
    this.absolutPathDark = absolutPathDark;
    this.nameDark = nameDark;
  }

  public String getName() {
    return name;
  }

  public String getAbsolutPathLight() {
    return absolutPathLight;
  }

  public String getNameLight() {
    return nameLight;
  }

  public String getAbsolutPathDark() {
    return absolutPathDark;
  }

  public String getNameDark() {
    return nameDark;
  }

  public Double getArea() {
    return area;
  }

  public void setArea(Double area) {
    this.area = area;
  }

  public Double getPowerInput() {
    return powerInput;
  }

  public void setPowerInput(Double powerInput) {
    this.powerInput = powerInput;
  }

}

