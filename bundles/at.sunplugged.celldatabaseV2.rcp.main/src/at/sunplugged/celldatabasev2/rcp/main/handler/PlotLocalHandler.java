
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.ArrayList;
import java.util.List;
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
import datamodel.CellResult;

public class PlotLocalHandler {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Object object,
      EPartService partService, EModelService modelService, MApplication app) {

    List<CellResult> cellResults = new ArrayList<>();
    if (object instanceof CellResult) {
      cellResults.add((CellResult) object);
    } else if (object instanceof Object[]) {
      Object[] results = (Object[]) object;
      for (Object cellResult : results) {
        cellResults.add((CellResult) cellResult);
      }
    }

    MPart plotPart;
    plotPart = partService.createPart("at.sunplugged.celldatabasev2.rcp.main.partdescriptor.chart");
    plotPart.setLabel("Plot");
    plotPart.getTransientData().put("data", cellResults);

    MPartStack partStack =
        (MPartStack) modelService.find("at.sunplugged.celldatabasev2.rcp.main.partstack.1", app);
    partStack.getChildren().add(plotPart);
    partService.showPart(plotPart, PartState.ACTIVATE);

  }


  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object object) {
    if (object instanceof CellResult) {
      return true;
    } else if (object instanceof Object[]) {
      Object[] objects = (Object[]) object;
      if (objects[0] instanceof CellResult) {
        return true;
      } else {
        return false;
      }

    } else {
      return false;
    }
  }

}
