
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabase.export.ui.wizards.SummaryExportWizard;
import datamodel.Database;

public class ExportExcelGroupSummaries {
  @Execute
  public void execute(Shell parentShell, Database database) {
    WizardDialog wizardDialog = new WizardDialog(parentShell, new SummaryExportWizard(database));
    wizardDialog.open();
  }

}
