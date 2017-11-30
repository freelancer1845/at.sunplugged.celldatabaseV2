package at.sunplugged.celldatabaseV2.plotting.internal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ShapeUtilities;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.UIDataPoint;

public class CellResultJFreeChartPlotter implements ChartPlotter {

  private List<CellMeasurementDataSet> measurementDataSets;

  public CellResultJFreeChartPlotter(List<CellMeasurementDataSet> cellResults) {
    this.measurementDataSets = cellResults;
  }

  @Override
  public JFreeChart getChart() {

    XYSeriesCollection seriesCollection = getSeriesCollection();

    JFreeChart chart =
        ChartFactory.createXYLineChart("UI-Plot", "Voltage[V]", "Current[A]", seriesCollection);

    chart.setBackgroundPaint(Color.WHITE);
    chart.getLegend().setPosition(RectangleEdge.RIGHT);

    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.white);
    plot.setDomainGridlinePaint(Color.BLACK);
    plot.setRangeGridlinePaint(Color.BLACK);
    plot.setRangeMinorGridlinePaint(Color.LIGHT_GRAY);

    ValueMarker verticalMarker = new ValueMarker(0.0);
    verticalMarker.setPaint(Color.BLACK);
    plot.addDomainMarker(verticalMarker);
    plot.addRangeMarker(verticalMarker);
    plot.setRenderer(new XYSplineRenderer());
    XYItemRenderer r = plot.getRenderer();
    if (r instanceof XYLineAndShapeRenderer) {
      XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
      renderer.setBaseShapesVisible(false);
      renderer.setBaseShapesFilled(false);
      renderer.setSeriesShape(0, ShapeUtilities.createDiagonalCross(1f, 1f));
      for (int i = 0; i < plot.getSeriesCount(); i++) {
        renderer.setSeriesStroke(i, new BasicStroke(3.0f));
      }

    }

    return chart;
  }

  private XYSeriesCollection getSeriesCollection() {
    XYSeriesCollection seriesCollection = new XYSeriesCollection();

    for (CellMeasurementDataSet dataSet : measurementDataSets) {
      String name = dataSet.getName();
      if (name.isEmpty()) {
        if (dataSet.eContainer() != null) {
          if (dataSet.eContainer() instanceof CellResult) {
            name = ((CellResult) dataSet.eContainer()).getName();
          }
        }
      }
      XYSeries series = new XYSeries(name, false);

      List<UIDataPoint> data = dataSet.getData();

      for (UIDataPoint point : data) {
        series.add(point.getVoltage(), point.getCurrent());
      }
      seriesCollection.addSeries(series);
    }



    return seriesCollection;
  }



}
