package at.sunplugged.celldatabaseV2.plotting.internal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ShapeUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.plotting.PlotHelper;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.UIDataPoint;

public class CellResultJFreeChartPlotter implements ChartPlotter {

  private static final Logger LOG = LoggerFactory.getLogger(CellResultJFreeChartPlotter.class);

  private List<CellMeasurementDataSet> measurementDataSets;


  public CellResultJFreeChartPlotter(List<CellMeasurementDataSet> cellResults) {
    this.measurementDataSets = cellResults;
  }

  @Override
  public JFreeChart getChart(Map<String, String> options) {
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

    for (String key : options.keySet()) {
      handleOptionKey(chart, key, options.get(key));
    }

    plot.setRenderer(new XYSplineRenderer());
    XYItemRenderer r = plot.getRenderer();
    if (r instanceof XYLineAndShapeRenderer) {
      XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
      if (options.containsKey(PlotHelper.DATA_POINTS)) {
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(false);
      } else {
        renderer.setBaseShapesVisible(false);
        renderer.setBaseShapesFilled(false);
      }
      renderer.setSeriesShape(0, ShapeUtilities.createDiagonalCross(2.2f, 2.2f));
      for (int i = 0; i < plot.getSeriesCount(); i++) {
        renderer.setSeriesStroke(i, new BasicStroke(3.0f));
      }

    }

    return chart;
  }

  private void handleOptionKey(JFreeChart chart, String key, String string) {
    switch (key) {
      case PlotHelper.POWER_CURVE:
        for (CellMeasurementDataSet dataSet : measurementDataSets) {
          String name = getDataSetName(dataSet) + " Power Curve";
          XYSeries powerSeries = new XYSeries(name, false);
          List<UIDataPoint> data = dataSet.getData();
          for (UIDataPoint point : data) {
            powerSeries.add(point.getVoltage(), point.getVoltage() * point.getCurrent());
          }
          ((XYSeriesCollection) chart.getXYPlot().getDataset()).addSeries(powerSeries);
        }
        break;
      case PlotHelper.POWER_CURVE_FIT:
        for (CellMeasurementDataSet dataSet : measurementDataSets) {
          CellResult cellResult = (CellResult) dataSet.eContainer();
          if (cellResult != null) {
            String name = getDataSetName(dataSet) + "PowerCurve Fit";
            XYSeries uiseries = new XYSeries(name, false);

            if (cellResult.getMppFitCoefficients().isEmpty() == false) {
              List<Double> coef;
              coef = cellResult.getMppFitCoefficients();



              PolynomialFunction fun = new PolynomialFunction(
                  IntStream.range(0, coef.size()).mapToDouble(idx -> coef.get(idx)).toArray());
              double[] x = new double[2000];
              double start = dataSet.getData().get(0).getVoltage();
              double end = dataSet.getData().get(dataSet.getData().size() - 1).getVoltage();
              double h = (end - start) / 2000;
              for (int i = 0; i < 2000; i++) {
                x[i] = start + i * h;
              }
              for (double x_i : x) {
                uiseries.add(x_i, fun.value(x_i));
              }
              ((XYSeriesCollection) chart.getXYPlot().getDataset()).addSeries(uiseries);
            }
          }
        }
        break;
      case PlotHelper.MP_POINT:
        for (CellMeasurementDataSet dataSet : measurementDataSets) {
          CellResult cellResult = (CellResult) dataSet.eContainer();
          if (cellResult != null) {
            Marker target = new ValueMarker(-cellResult.getMaximumPowerCurrent());
            target.setPaint(Color.yellow);
            chart.getXYPlot().addRangeMarker(target);
            Marker domainTarget = new ValueMarker(cellResult.getMaximumPowerVoltage());
            domainTarget.setPaint(Color.yellow);
            chart.getXYPlot().addDomainMarker(domainTarget);

          }
        }
        break;
      case PlotHelper.RS_LINE:
        for (CellMeasurementDataSet dataSet : measurementDataSets) {
          CellResult cellResult = (CellResult) dataSet.eContainer();
          if (cellResult != null) {
            String name = getDataSetName(dataSet) + " Rs Line";
            XYSeries rsSeries = new XYSeries(name, false);

            double rs = cellResult.getSeriesResistance();
            double[] x = new double[2000];
            double start = dataSet.getData().get(0).getVoltage();
            double end = dataSet.getData().get(dataSet.getData().size() - 1).getVoltage();
            double h = (end - start) / 2000;
            for (int i = 0; i < 2000; i++) {
              x[i] = start + i * h;
            }
            for (double x_i : x) {
              rsSeries.add(x_i, 1 / rs * x_i - cellResult.getOpenCircuitVoltage() / rs);
            }
            ((XYSeriesCollection) chart.getXYPlot().getDataset()).addSeries(rsSeries);
          }
        }
        break;
      case PlotHelper.RP_LINE:
        for (CellMeasurementDataSet dataSet : measurementDataSets) {
          CellResult cellResult = (CellResult) dataSet.eContainer();
          if (cellResult != null) {
            String name = getDataSetName(dataSet) + " Rp curve";
            XYSeries rpSeries = new XYSeries(name, false);


            double rp = cellResult.getParallelResistance();

            double[] x = new double[2000];
            double start = dataSet.getData().get(0).getVoltage();
            double end = dataSet.getData().get(dataSet.getData().size() - 1).getVoltage();
            double h = (end - start) / 2000;
            for (int i = 0; i < 2000; i++) {
              x[i] = start + i * h;
            }
            for (double x_i : x) {
              rpSeries.add(x_i, 1 / rp * x_i - cellResult.getShortCircuitCurrent());
            }
            ((XYSeriesCollection) chart.getXYPlot().getDataset()).addSeries(rpSeries);
          }
        }
        break;
      case PlotHelper.VOC_RS_FIT:
        for (CellMeasurementDataSet dataSet : measurementDataSets) {
          CellResult cellResult = (CellResult) dataSet.eContainer();
          if (cellResult != null) {
            String name = getDataSetName(dataSet) + "Voc/Rs Fit";
            XYSeries uiseries = new XYSeries(name, false);

            if (cellResult.getRsVocFitCoefficients().isEmpty() == false) {
              List<Double> coef;
              coef = cellResult.getRsVocFitCoefficients();



              PolynomialFunction fun = new PolynomialFunction(
                  IntStream.range(0, coef.size()).mapToDouble(idx -> coef.get(idx)).toArray());
              double[] x = new double[2000];
              double start = dataSet.getData().get(0).getVoltage();
              double end = dataSet.getData().get(dataSet.getData().size() - 1).getVoltage();
              double h = (end - start) / 2000;
              for (int i = 0; i < 2000; i++) {
                x[i] = start + i * h;
              }
              for (double x_i : x) {
                uiseries.add(x_i, fun.value(x_i));
              }
              ((XYSeriesCollection) chart.getXYPlot().getDataset()).addSeries(uiseries);
            }
          }
        }
        break;
      case PlotHelper.ISC_RP_FIT:
        for (CellMeasurementDataSet dataSet : measurementDataSets) {
          CellResult cellResult = (CellResult) dataSet.eContainer();
          if (cellResult != null) {
            String name = getDataSetName(dataSet) + "Isc/Rp Fit";
            XYSeries uiseries = new XYSeries(name, false);

            if (cellResult.getRpIscFitCoefficients().isEmpty() == false) {
              List<Double> coef = cellResult.getRpIscFitCoefficients();



              PolynomialFunction fun = new PolynomialFunction(
                  IntStream.range(0, coef.size()).mapToDouble(idx -> coef.get(idx)).toArray());
              double[] x = new double[2000];
              double start = dataSet.getData().get(0).getVoltage();
              double end = dataSet.getData().get(dataSet.getData().size() - 1).getVoltage();
              double h = (end - start) / 2000;
              for (int i = 0; i < 2000; i++) {
                x[i] = start + i * h;
              }
              for (double x_i : x) {
                uiseries.add(x_i, fun.value(x_i));
              }
              ((XYSeriesCollection) chart.getXYPlot().getDataset()).addSeries(uiseries);
            }
          }
        }
        break;
      case PlotHelper.DARK_RP_FIT:
        addLinearSeries(chart, "Dark RP Fit", cellResult -> cellResult.getDarkRpFitCoefficients());
        break;
      case PlotHelper.DARK_RS_FIT:
        addLinearSeries(chart, "Dark RS Fit", cellResult -> cellResult.getDarkRsFitCoefficients());
        break;

      default:
        LOG.error("Unkown option: " + key);
        break;
    }
  }

  private void addLinearSeries(JFreeChart chart, String name,
      Function<CellResult, List<Double>> coeffGetter) {

    for (CellMeasurementDataSet dataSet : measurementDataSets) {
      CellResult cellResult = (CellResult) dataSet.eContainer();
      if (cellResult != null) {
        String nameS = getDataSetName(dataSet) + name;
        XYSeries uiseries = new XYSeries(nameS, false);

        if (coeffGetter.apply(cellResult).isEmpty() == false) {

          List<Double> coef = coeffGetter.apply(cellResult);

          PolynomialFunction fun = new PolynomialFunction(
              IntStream.range(0, coef.size()).mapToDouble(idx -> coef.get(idx)).toArray());
          double[] x = new double[2000];
          double start = dataSet.getData().get(0).getVoltage();
          double end = dataSet.getData().get(dataSet.getData().size() - 1).getVoltage();
          double h = (end - start) / 2000;
          for (int i = 0; i < 2000; i++) {
            x[i] = start + i * h;
          }
          for (double x_i : x) {
            uiseries.add(x_i, fun.value(x_i));
          }
          ((XYSeriesCollection) chart.getXYPlot().getDataset()).addSeries(uiseries);
        }
      }
    }
  }

  private XYSeriesCollection getSeriesCollection() {
    XYSeriesCollection seriesCollection = new XYSeriesCollection();

    for (CellMeasurementDataSet dataSet : measurementDataSets) {
      String name = getDataSetName(dataSet);
      XYSeries series = new XYSeries(name, false);

      List<UIDataPoint> data = dataSet.getData();

      for (UIDataPoint point : data) {
        series.add(point.getVoltage(), point.getCurrent());
      }
      seriesCollection.addSeries(series);
    }



    return seriesCollection;
  }


  private String getDataSetName(CellMeasurementDataSet dataSet) {
    String name = dataSet.getName();
    if (name.isEmpty()) {
      if (dataSet.eContainer() != null) {
        if (dataSet.eContainer() instanceof CellResult) {
          name = ((CellResult) dataSet.eContainer()).getName();
        }
      }
    }
    return name;
  }


}
