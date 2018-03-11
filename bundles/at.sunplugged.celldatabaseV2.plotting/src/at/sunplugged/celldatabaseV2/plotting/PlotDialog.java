package at.sunplugged.celldatabaseV2.plotting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import datamodel.CellMeasurementDataSet;

public class PlotDialog extends TitleAreaDialog {

  private final List<CellMeasurementDataSet> dataSets = new ArrayList<>();

  public PlotDialog(Shell parentShell, CellMeasurementDataSet dataSet) {
    super(parentShell);
    this.dataSets.add(dataSet);
    setTitle(dataSet.getName());
    setMessage("Plot of DataSet.");
  }

  public PlotDialog(Shell parentShell, List<CellMeasurementDataSet> dataSets) {
    super(parentShell);
    this.dataSets.addAll(dataSets);
  }

  @Override
  protected Point getInitialSize() {
    return new Point(800, 600);
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, "Ok", true);

  }


  @Override
  protected Control createDialogArea(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    composite.setLayout(new GridLayout(1, false));

    PlotHelper.plotChartToComposite(composite,
        PlotHelper.createJFreeChart(dataSets, Collections.EMPTY_MAP));

    return composite;

  }



}
