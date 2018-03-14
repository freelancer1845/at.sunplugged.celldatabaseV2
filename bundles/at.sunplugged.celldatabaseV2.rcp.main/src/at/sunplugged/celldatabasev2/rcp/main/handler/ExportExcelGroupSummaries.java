
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabaseV2.export.ui.wizards.SummaryExportWizard;
import datamodel.CellGroup;

public class ExportExcelGroupSummaries {
  @Execute
  public void execute(Shell parentShell,
      @Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {

    List<CellGroup> groups = getGroupsFromSelection(selection);
    WizardDialog wizardDialog = new WizardDialog(parentShell, new SummaryExportWizard(groups));
    wizardDialog.open();
  }

  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    if (selection == null) {
      return false;
    }
    List<CellGroup> groups = getGroupsFromSelection(selection);
    if (groups.size() == 0) {
      return false;
    } else {
      return true;
    }
  }


  private List<CellGroup> getGroupsFromSelection(Object selection) {
    List<CellGroup> groups = new ArrayList<>();
    if (!(selection instanceof Object[])) {
      groups.add((CellGroup) selection);
    } else if (selection instanceof Object[]) {
      Object[] items = (Object[]) selection;

      IntStream.range(0, items.length).filter(idx -> items[idx] instanceof CellGroup)
          .mapToObj(idx -> (CellGroup) items[idx]).forEach(group -> groups.add(group));
    }

    return groups;
  }
}
