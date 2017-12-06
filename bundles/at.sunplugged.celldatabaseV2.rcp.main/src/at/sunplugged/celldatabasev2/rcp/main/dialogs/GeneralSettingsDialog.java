package at.sunplugged.celldatabasev2.rcp.main.dialogs;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import at.sunplugged.celldatabaseV2.common.GeneralSettings;
import at.sunplugged.celldatabaseV2.common.PrefNodes;

public class GeneralSettingsDialog extends AbstractSettingsDialog {

  public GeneralSettingsDialog(Shell parentShell) {
    super(parentShell, "General Settings", "General Settings Dialog", PrefNodes.GENERAL);
  }

  @Override
  protected void createFields(Composite container) {

    createLabviewImportPathSetting(container);

  }

  private void createLabviewImportPathSetting(Composite container) {
    Button checkBox = createLabeledCheckbox(container, "Use predefinied LabviewImport path",
        GeneralSettings.USE_LABVIEW_IMPORT_PATH);
    Text text = createLabeledDirectoryChooser(container, "Predefined LaviewImport path",
        GeneralSettings.LABVIEW_IMPORT_PATH);
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
