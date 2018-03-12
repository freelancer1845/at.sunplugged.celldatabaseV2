package at.sunplugged.celldatabaseV2.labviewimport.ui.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import at.sunplugged.celldatabaseV2.labviewimport.LabviewCalculationException;
import at.sunplugged.celldatabaseV2.labviewimport.LabviewCalculator;

public class CalculatorErrorDialog extends TitleAreaDialog {

  private final Map<LabviewCalculator, IOException> calculators;
  private TableViewer viewer;

  private final Map<LabviewCalculator, Boolean> reevaluateMap;

  public CalculatorErrorDialog(Shell parentShell, Map<LabviewCalculator, IOException> calculators) {
    super(parentShell);
    this.calculators = calculators;
    reevaluateMap = new HashMap<>();
    calculators.entrySet().forEach(entry -> reevaluateMap.put(entry.getKey(), false));
  }



  @Override
  protected boolean isResizable() {
    return true;
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);

    composite.setLayout(new GridLayout(1, false));
    viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

    createColumns();
    viewer.setContentProvider(ArrayContentProvider.getInstance());
    viewer.setInput(new ArrayList<>(calculators.keySet()));



    final Table table = viewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    Button btn_eval = new Button(composite, SWT.PUSH);
    btn_eval.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
    btn_eval.setText("Reevaluate");

    btn_eval.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        calculators.keySet().stream().filter(key -> calculators.get(key) == null).forEach(calc -> {
          try {
            calc.reEvaluate(calc.getResult(), true);
          } catch (LabviewCalculationException e1) {
            MessageDialog.openError(getShell(), "Error",
                "Another Error happend.\n" + e1.getMessage());
          }
        });
      }
    });

    return composite;
  }



  private void createColumns() {
    TableViewerColumn colName = new TableViewerColumn(viewer, SWT.NONE);
    colName.getColumn().setWidth(200);
    colName.getColumn().setText("Name");
    colName.setLabelProvider(new ColumnLabelProvider() {

      @Override
      public String getText(Object element) {
        LabviewCalculator calc = (LabviewCalculator) element;
        if (calc.getDataFile() != null) {
          return calc.getDataFile().getName();
        } else {
          return calc.getResult().getName();
        }
      }
    });

    TableViewerColumn exceptionName = new TableViewerColumn(viewer, SWT.NONE);
    exceptionName.getColumn().setWidth(200);
    exceptionName.getColumn().setText("Exception");
    exceptionName.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        LabviewCalculator cal = (LabviewCalculator) element;
        if (calculators.get(cal) != null) {
          return "I/O Exception";
        } else {
          return "Calculation Error";
        }
      }

    });

    TableViewerColumn colMessage = new TableViewerColumn(viewer, SWT.NONE);
    colMessage.getColumn().setWidth(500);
    colMessage.getColumn().setText("Error Message");
    colMessage.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        LabviewCalculator cal = (LabviewCalculator) element;

        if (calculators.get(cal) != null) {
          return calculators.get(cal).getMessage();
        } else {
          StringBuilder builder = new StringBuilder();
          cal.getCalculationErrors().stream()
              .forEach(err -> builder.append(err.getMessage() + "; "));
          return builder.toString();
        }
      }
    });

    TableViewerColumn reEvaluateButton = new TableViewerColumn(viewer, SWT.NONE);
    reEvaluateButton.getColumn().setWidth(150);
    reEvaluateButton.getColumn().setText("Reevaluate?");
    reEvaluateButton.getColumn()
        .setToolTipText("Reevaluate the data but ask for UserInput to find Ranges.");
    reEvaluateButton.setLabelProvider(new CellLabelProvider() {

      @Override
      public void update(ViewerCell cell) {
        cell.setText("Reevaluate?");
      }
    });
    reEvaluateButton.setEditingSupport(new CheckboxEvaluateSupport(viewer));

  }

  private final class CheckboxEvaluateSupport extends EditingSupport {

    public CheckboxEvaluateSupport(ColumnViewer viewer) {
      super(viewer);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
      return new CheckboxCellEditor(null, SWT.CHECK);
    }

    @Override
    protected boolean canEdit(Object element) {
      return true;
    }

    @Override
    protected Object getValue(Object element) {
      return reevaluateMap.get(element);
    }

    @Override
    protected void setValue(Object element, Object value) {
      reevaluateMap.put((LabviewCalculator) element, (Boolean) value);
      getViewer().update(element, null);
    }

  }

  public Map<LabviewCalculator, IOException> getCalculators() {
    return calculators;
  }



}
