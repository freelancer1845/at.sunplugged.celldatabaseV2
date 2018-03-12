
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import at.sunplugged.celldatabaseV2.labviewimport.LabviewCalculationException;
import at.sunplugged.celldatabaseV2.labviewimport.LabviewCalculator;
import at.sunplugged.celldatabaseV2.plotting.PlotDialog;
import datamodel.CellGroup;
import datamodel.CellResult;

public class ReevaluateHandler {

  private boolean cancleEvaluation = false;

  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection, Shell shell) {
    if (!MessageDialog.openConfirm(shell, "Reevaluate", "Are you sure?")) {
      return;
    }

    Set<CellResult> resultsToReEvaluate = new HashSet<>();

    if (selection instanceof Object) {
      if (selection instanceof CellResult) {
        resultsToReEvaluate.add((CellResult) selection);
      } else if (selection instanceof CellGroup) {
        CellGroup group = (CellGroup) selection;
        resultsToReEvaluate.addAll(group.getCellResults());
      }
    } else if (selection instanceof Object[]) {
      Arrays.stream((Object[]) selection).forEach(item -> {
        if (item instanceof CellResult) {
          resultsToReEvaluate.add((CellResult) item);
        } else if (item instanceof CellGroup) {
          CellGroup group = (CellGroup) item;
          resultsToReEvaluate.addAll(group.getCellResults());
        }
      });
    }


    resultsToReEvaluate.stream().anyMatch(result -> {
      LabviewCalculator calculator = new LabviewCalculator();
      try {
        calculator.reEvaluate(result, false);
      } catch (LabviewCalculationException e) {
        while (true) {
          int answer =
              MessageDialog.open(MessageDialog.QUESTION, shell, "Automatic Calculation failed.",
                  "Automatic calculation failed.\nChoose what to do.", SWT.NONE,
                  new String[] {"See Data", "Select Fit Ranges", "Skip", "Cancle Evaluation"});

          if (answer == 0) {
            new PlotDialog(shell, Arrays.asList(calculator.getResult().getLightMeasurementDataSet(),
                calculator.getResult().getDarkMeasuremenetDataSet())).open();
          } else if (answer == 1) {
            try {
              calculator.reEvaluate(calculator.getResult(), true);
            } catch (LabviewCalculationException e1) {
              MessageDialog.openError(shell, "Error",
                  "Calculation failed again.\n" + e1.getMessage());
            }
          } else if (answer == 2) {
            // effectivley do nothing
            break;
          } else if (answer == 3) {
            cancleEvaluation = true;
            break;
          } else {
            break;
          }
        }
      }
      return cancleEvaluation;
    });


  }


  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    if (selection instanceof Object[]) {
      Object[] selectedItems = (Object[]) selection;
      return Arrays.stream(selectedItems).allMatch(item -> {
        return item instanceof CellResult || item instanceof CellGroup;
      });
    } else {
      return selection instanceof CellResult || selection instanceof CellGroup;
    }
  }

}
