
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.command.ChangeCommand;
import at.sunplugged.celldatabasev2.rcp.main.imperativeexp.SelectionUtils;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;

public class InvertDataHandler {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection,
      CommandStack stack) {

    Map<Class<?>, List<?>> items = SelectionUtils.getAllFromSelectionOfAnyType(selection,
        CellGroup.class, CellResult.class, CellMeasurementDataSet.class);


    @SuppressWarnings("unchecked")
    List<CellGroup> groups = (List<CellGroup>) items.get(CellGroup.class);

    @SuppressWarnings("unchecked")
    List<CellResult> results = (List<CellResult>) items.get(CellResult.class);

    @SuppressWarnings("unchecked")
    List<CellMeasurementDataSet> dataSets =
        (List<CellMeasurementDataSet>) items.get(CellMeasurementDataSet.class);

    Set<CellMeasurementDataSet> dataSetsSet = new HashSet<>();

    dataSetsSet.addAll(dataSets);

    results.forEach(result -> addDataSetsToSet(dataSetsSet, result));
    groups.forEach(
        group -> group.getCellResults().forEach(result -> addDataSetsToSet(dataSetsSet, result)));

    Collection<Notifier> notifiers = new ArrayList<>(dataSetsSet);
    Command cmd = new ChangeCommand(notifiers) {

      @Override
      protected void doExecute() {
        dataSetsSet.forEach(set -> invertSet(set));
      }
    };

    stack.execute(cmd);

  }



  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    return SelectionUtils.isSelectionOnlyOfTheseTypes(selection, CellGroup.class, CellResult.class,
        CellMeasurementDataSet.class);
  }

  private void addDataSetsToSet(Set<CellMeasurementDataSet> set, CellResult result) {
    if (result.getDarkMeasuremenetDataSet() != null) {
      set.add(result.getDarkMeasuremenetDataSet());
    }
    if (result.getLightMeasurementDataSet() != null) {
      set.add(result.getLightMeasurementDataSet());
    }
  }

  private void invertSet(CellMeasurementDataSet set) {
    set.getData().forEach(point -> point.setCurrent(point.getCurrent() * -1));
  }
}
