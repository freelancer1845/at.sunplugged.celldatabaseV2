package at.sunplugged.celldatabaseV2.plotting;

import java.util.Collections;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.experimental.chart.swt.ChartComposite;
import datamodel.CellMeasurementDataSet;

public class SelectRangeFromChartDialog extends TitleAreaDialog {

  private int currentRange = 0;

  private final String[] ranges;

  private final double[][] points;

  private final CellMeasurementDataSet dataSet;

  public SelectRangeFromChartDialog(Shell parentShell, CellMeasurementDataSet dataSet,
      String[] ranges) {
    super(parentShell);
    this.points = new double[ranges.length][2];
    this.dataSet = dataSet;
    this.ranges = ranges;
  }

  public double[][] getPoints() {
    return points;
  }

  @Override
  protected Point getInitialSize() {

    return new Point(800, 640);
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    composite.setLayout(new GridLayout(1, false));
    JFreeChart chart = PlotHelper.createJFreeChart(dataSet, Collections.EMPTY_MAP);
    ChartComposite chartComposite = PlotHelper.plotChartToComposite(composite, chart);
    chartComposite.addChartMouseListener(new ChartMouseListener() {

      private boolean start = true;

      private ValueMarker startMarker = null;

      private ValueMarker stopMarker = null;

      @Override
      public void chartMouseMoved(ChartMouseEvent arg0) {

      }

      @Override
      public void chartMouseClicked(ChartMouseEvent arg0) {
        JFreeChart chart = arg0.getChart();
        if (start == true) {
          double x = arg0.getTrigger().getPoint().getX();
          if (startMarker != null) {
            chart.getXYPlot().removeDomainMarker(startMarker);
          }
          startMarker = new ValueMarker(x);
          chart.getXYPlot().addDomainMarker(startMarker);
          points[currentRange][0] = x;
          start = false;
        } else {
          double x = arg0.getTrigger().getPoint().getX();
          if (stopMarker != null) {
            chart.getXYPlot().removeDomainMarker(stopMarker);
          }
          stopMarker = new ValueMarker(x);
          chart.getXYPlot().addDomainMarker(stopMarker);
          points[currentRange][1] = x;
          start = true;
          if (MessageDialog.openQuestion(getShell(), "Range Correct?", "Reselect this range?")) {
          } else {
            currentRange++;
            if (currentRange == ranges.length) {
              close();
              return;
            }
          }
          MessageDialog.openInformation(getShell(), "Select Point",
              "Select " + ranges[currentRange]);
        }


      }
    });



    return composite;
  }

  @Override
  public void create() {
    super.create();
    MessageDialog.openInformation(getShell(), "Select Point", "Select " + ranges[currentRange]);
  }



}
