package at.sunplugged.celldatabaseV2.common.ui.dialog;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabaseV2.common.settings.FileDialogSetting;
import at.sunplugged.celldatabaseV2.common.settings.SettingsAccessor;

public class FileDialogWithLastOpen {

  private final IDs pathId;

  private final FileDialog backingDialog;


  public FileDialogWithLastOpen(Shell parent, int style, IDs pathId) {
    this.backingDialog = new FileDialog(parent, style);
    this.pathId = pathId;
    loadSettings();
  }

  public FileDialogWithLastOpen(Shell parent, IDs pathId) {
    this.backingDialog = new FileDialog(parent);
    this.pathId = pathId;
    loadSettings();
  }


  public String open() {
    String result = this.backingDialog.open();
    if (result != null) {
      SettingsAccessor.getInstance().getSettings().getDialogPaths().get(pathId)
          .setLastPath(this.backingDialog.getFilterPath());
      SettingsAccessor.getInstance().flushSettingsIgnore();
    }
    return result;
  }


  private void loadSettings() {
    FileDialogSetting setting =
        SettingsAccessor.getInstance().getSettings().getDialogPaths().get(this.pathId);
    if (setting == null) {
      setting = generateDefaultSettings();
    }
    this.backingDialog.setFilterExtensions(setting.getFilterExtensions());
    this.backingDialog.setFilterNames(setting.getFilterNames());
    if (setting.isUseLastPath() == true) {
      this.backingDialog.setFilterPath(setting.getLastPath());
    } else {
      this.backingDialog.setFilterPath(setting.getDefaultPath());
    }

  }

  private FileDialogSetting generateDefaultSettings() {
    FileDialogSetting setting;
    setting = IDs.generateFileDialogSetting(pathId);
    SettingsAccessor.getInstance().getSettings().getDialogPaths().put(pathId, setting);
    return setting;
  }

  public void setFileName(String fileName) {
    this.backingDialog.setFileName(fileName);
  }

  public String getFilterPath() {
    return this.backingDialog.getFilterPath();
  }

  public String getFileName() {
    return this.backingDialog.getFileName();
  }

  public static enum IDs {
    CELL_RESULTS_EXPORT("Cell Results Export path"), CELL_GROUPS_EXPORT(
        "Cell Groups Export path"), LABVIEW_IMPORT("Labview import path");

    private String writtenName;

    private IDs(String writtenName) {
      this.writtenName = writtenName;
    }

    public String getWrittenName() {
      return writtenName;
    }

    public static FileDialogSetting generateFileDialogSetting(IDs id) {
      FileDialogSetting setting = new FileDialogSetting();
      switch (id) {
        case CELL_RESULTS_EXPORT:
          setting.setFilterExtensions(new String[] {"*.xlsx"});
          setting.setFilterNames(new String[] {"Microsoft Open XML Format"});
          break;
        case LABVIEW_IMPORT:
          setting.setFilterExtensions(new String[] {"*.txt", "*.*"});
          setting.setFilterNames(new String[] {"Text Files", "All"});
          break;
        case CELL_GROUPS_EXPORT:
          setting.setFilterExtensions(new String[] {"*.xlsx"});
          setting.setFilterNames(new String[] {"Microsoft Open XML Format"});
        default:
          break;
      }
      return setting;
    }
  }

  public String[] getFileNames() {
    return this.backingDialog.getFileNames();
  }

}
