package at.sunplugged.celldatabasev2.rcp.main.dialogs;

import java.util.Arrays;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import at.sunplugged.celldatabaseV2.common.settings.FileDialogSetting;
import at.sunplugged.celldatabaseV2.common.settings.SettingsAccessor;
import at.sunplugged.celldatabaseV2.common.ui.dialog.FileDialogWithLastOpen;
import at.sunplugged.celldatabaseV2.common.ui.dialog.FileDialogWithLastOpen.IDs;

public class GeneralSettingsDialog extends AbstractSettingsDialog {

  public GeneralSettingsDialog(Shell parentShell) {
    super(parentShell, "General Settings", "General Settings Dialog");
  }

  @Override
  protected void createFields(Composite container) {

    Arrays.stream(FileDialogWithLastOpen.IDs.values())
        .forEach(id -> createDialogSetting(container, id));


    // createLabviewImportPathSetting(container);

  }

  private void createLabviewImportPathSetting(Composite container) {

    Button checkBox = createLabeledCheckbox(container, "Use predefinied LabviewImport path",
        settings -> settings.getLabviewImportDefaultSettings().isUseImportPath(), (settings,
            newValue) -> settings.getLabviewImportDefaultSettings().setUseImportPath(newValue));
    Text text = createLabeledDirectoryChooser(container, "Predefined LaviewImport path",
        settings -> settings.getLabviewImportDefaultSettings().getImportPath(),
        (settings, newValue) -> settings.getLabviewImportDefaultSettings().setImportPath(newValue));
    text.setEnabled(checkBox.getSelection());
    checkBox.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (checkBox.getSelection() == true) {
          text.setEnabled(true);
        } else {
          text.setEnabled(false);
        }
      }

    });

  }

  private void createDialogSetting(Composite container, IDs id) {
    checkPathIdExistsAndCreate(id);

    Group group = new Group(container, SWT.NONE);
    group.setText(id.getWrittenName());
    group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    group.setLayout(new GridLayout(2, false));

    Button checkBox = createLabeledCheckbox(group, "Use predefinied path",
        settings -> !settings.getDialogPaths().get(id).isUseLastPath(),
        (settings, newValue) -> settings.getDialogPaths().get(id).setUseLastPath(!newValue));
    Text text = createLabeledDirectoryChooser(group, "Predefined path",
        settings -> settings.getDialogPaths().get(id).getDefaultPath(),
        (settings, newValue) -> settings.getDialogPaths().get(id).setDefaultPath(newValue));
    text.setEnabled(checkBox.getSelection());
    checkBox.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (checkBox.getSelection() == true) {
          text.setEnabled(true);
        } else {
          text.setEnabled(false);
        }
      }

    });
  }

  private void checkPathIdExistsAndCreate(IDs id) {
    Map<IDs, FileDialogSetting> paths =
        SettingsAccessor.getInstance().getSettings().getDialogPaths();
    if (paths.containsKey(id) == false) {
      paths.put(id, IDs.generateFileDialogSetting(id));
    }
  }



}
