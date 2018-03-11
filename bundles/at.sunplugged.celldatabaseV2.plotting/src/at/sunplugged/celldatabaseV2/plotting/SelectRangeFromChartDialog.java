package at.sunplugged.celldatabaseV2.plotting;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.stream.IntStream;
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
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.experimental.chart.swt.ChartComposite;
import datamodel.CellMeasurementDataSet;

public class SelectRangeFromChartDialog extends TitleAreaDialog {

  private int currentRange = 0;

  private final String[] ranges;

  private final double[][] points;

  private final CellMeasurementDataSet dataSet;

  private ChartComposite chartComposite;

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
    chartComposite = PlotHelper.plotChartToComposite(composite, chart);
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
          double x = getXYFromMouseEvent(arg0)[0];
          if (startMarker != null) {
            chart.getXYPlot().removeDomainMarker(startMarker);
          }
          startMarker = new ValueMarker(x);
          chart.getXYPlot().addDomainMarker(startMarker);
          points[currentRange][0] = x;
          start = false;
        } else {
          double x = getXYFromMouseEvent(arg0)[0];
          if (stopMarker != null) {
            chart.getXYPlot().removeDomainMarker(stopMarker);
          }
          stopMarker = new ValueMarker(x);
          chart.getXYPlot().addDomainMarker(stopMarker);
          points[currentRange][1] = x;
          start = true;

          int startRange = IntStream.range(0, dataSet.getData().size())
              .filter(idx -> dataSet.getData().get(idx).getVoltage() > points[currentRange][0])
              .findFirst().getAsInt();
          int endRange = IntStream.range(0, dataSet.getData().size())
              .filter(idx -> dataSet.getData().get(idx).getVoltage() > points[currentRange][1])
              .findFirst().getAsInt();

          System.out.println("Range: " + points[currentRange][0] + " - " + points[currentRange][1]);
          dataSet.getData().forEach(point -> System.out
              .println("V: " + point.getVoltage() + " - I: " + point.getCurrent()));
          if (MessageDialog.openQuestion(getShell(), "Range Correct?",
              "Data Points contained in this Range: " + (endRange - startRange))) {
            currentRange++;
            if (currentRange == ranges.length) {
              close();
              return;
            }
          } else {

          }
          MessageDialog.openInformation(getShell(), "Select Point",
              "Select " + ranges[currentRange]);
        }


      }
    });



    return composite;
  }

  private double[] getXYFromMouseEvent(ChartMouseEvent event) {
    final XYPlot plot = event.getChart().getXYPlot();
    final ValueAxis domainAxis = plot.getDomainAxis();
    final ValueAxis rangeAxis = plot.getRangeAxis();
    final Rectangle2D plotRectangle =
        chartComposite.getChartRenderingInfo().getPlotInfo().getDataArea();
    final double chartX = domainAxis.java2DToValue(event.getTrigger().getPoint().getX(),
        plotRectangle, plot.getDomainAxisEdge());
    final double chartY = rangeAxis.java2DToValue(event.getTrigger().getPoint().getY(),
        plotRectangle, plot.getRangeAxisEdge());

    return new double[] {chartX, chartY};
  }

  @Override
  public void create() {
    super.create();
    MessageDialog.openInformation(getShell(), "Select Point", "Select " + ranges[currentRange]);
  }



}
