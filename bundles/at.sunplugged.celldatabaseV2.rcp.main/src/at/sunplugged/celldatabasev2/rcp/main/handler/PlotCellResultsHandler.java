
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import at.sunplugged.celldatabaseV2.plotting.PlotHelper;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;

public class PlotCellResultsHandler {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    List<CellResult> cellResults = new ArrayList<>();
    List<CellMeasurementDataSet> dataSets = new ArrayList<>();
    if (selection instanceof Object[]) {
      Arrays.stream((Object[]) selection).sequential().forEach(object -> {
        if (object instanceof CellGroup) {
          CellGroup cellGroup = (CellGroup) object;
          cellGroup.getCellResults().stream().sequential().forEach(cellResult -> {
            if (cellResults.contains(cellResult) == false) {
              cellResults.add(cellResult);
            }
          });
        } else if (object instanceof CellResult) {
          CellResult cellResult = (CellResult) object;
          if (cellResults.contains(cellResult) == false) {
            cellResults.add(cellResult);
          }
        } else if (object instanceof CellMeasurementDataSet) {
          CellMeasurementDataSet dataSet = (CellMeasurementDataSet) object;
          if (dataSets.contains(object) == false) {
            dataSets.add(dataSet);
          }
        }
      });
    } else if (selection instanceof CellGroup) {
      CellGroup cellGroup = (CellGroup) selection;
      cellResults.addAll(cellGroup.getCellResults());
    } else if (selection instanceof CellResult) {
      CellResult cellResult = (CellResult) selection;
      cellResults.add(cellResult);
    } else if (selection instanceof CellMeasurementDataSet) {
      CellMeasurementDataSet dataSet = (CellMeasurementDataSet) selection;
      dataSets.add(dataSet);
    }

    if (cellResults.isEmpty() == false || dataSets.isEmpty() == false) {
      PlotHelper.plotCellResults(cellResults, dataSets);
    }
  }

  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    if (selection == null) {
      return false;
    } else {
      return true;
    }

  }

}
