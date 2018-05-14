package at.sunplugged.celldatabasev2.rcp.main.dialogs;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GeneralSettingsDialog extends AbstractSettingsDialog {

  public GeneralSettingsDialog(Shell parentShell) {
    super(parentShell, "General Settings", "General Settings Dialog");
  }

  @Override
  protected void createFields(Composite container) {

    createLabviewImportPathSetting(container);

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



}
