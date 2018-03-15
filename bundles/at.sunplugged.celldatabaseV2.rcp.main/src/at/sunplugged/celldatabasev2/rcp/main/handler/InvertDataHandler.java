
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.Arrays;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.command.ChangeCommand;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;

public class InvertDataHandler {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) CellResult result,
      CommandStack stack) {

    Command cmd = new ChangeCommand(result) {

      @Override
      protected void doExecute() {
        CellMeasurementDataSet lightSet = result.getLightMeasurementDataSet();
        CellMeasurementDataSet darkSet = result.getDarkMeasuremenetDataSet();
        if (lightSet != null) {
          invertSet(lightSet);
        }
        if (darkSet != null) {
          invertSet(darkSet);
        }
      }
    };

    stack.execute(cmd);
  }

  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) CellResult[] results,
      CommandStack stack) {
    Arrays.stream(results).forEach(result -> execute(result, stack));
  }


  private void invertSet(CellMeasurementDataSet set) {
    set.getData().forEach(point -> point.setCurrent(point.getCurrent() * -1));
  }
}
