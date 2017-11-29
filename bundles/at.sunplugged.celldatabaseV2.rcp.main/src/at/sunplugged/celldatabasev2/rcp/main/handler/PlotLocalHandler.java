
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;

public class PlotLocalHandler {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Object object,
      EPartService partService, EModelService modelService, MApplication app) {

    List<CellMeasurementDataSet> dataSets = new ArrayList<>();
    if (object instanceof CellResult) {
      dataSets.add(((CellResult) object).getLightMeasurementDataSet());
    } else if (object instanceof CellMeasurementDataSet) {
      dataSets.add((CellMeasurementDataSet) object);
    } else if (object instanceof CellGroup) {
      dataSets.addAll(((CellGroup) object).getCellResults().stream()
          .map(result -> result.getLightMeasurementDataSet()).collect(Collectors.toList()));
    } else if (object instanceof Object[]) {
      Object[] results = (Object[]) object;
      for (Object subObject : results) {
        if (subObject instanceof CellResult) {
          dataSets.add(((CellResult) subObject).getLightMeasurementDataSet());
        } else if (subObject instanceof CellMeasurementDataSet) {
          dataSets.add((CellMeasurementDataSet) subObject);
        } else if (subObject instanceof CellGroup) {
          dataSets.addAll(((CellGroup) subObject).getCellResults().stream()
              .map(result -> result.getLightMeasurementDataSet()).collect(Collectors.toList()));
        }
      }
    }

    MPart plotPart;
    plotPart = partService.createPart("at.sunplugged.celldatabasev2.rcp.main.partdescriptor.chart");
    plotPart.setLabel("Plot");
    plotPart.getTransientData().put("data", dataSets);

    MPartStack partStack =
        (MPartStack) modelService.find("at.sunplugged.celldatabasev2.rcp.main.partstack.1", app);
    partStack.getChildren().add(plotPart);
    partService.showPart(plotPart, PartState.ACTIVATE);

  }


  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object object) {
    if (object instanceof CellResult) {
      return true;
    } else if (object instanceof CellGroup) {
      return true;
    } else if (object instanceof CellMeasurementDataSet) {
      return true;
    } else if (object instanceof Object[]) {
      Object[] objects = (Object[]) object;
      if (objects[0] instanceof CellResult) {
        return true;
      } else if (object instanceof CellGroup) {
        return true;
      } else if (object instanceof CellMeasurementDataSet) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

}
