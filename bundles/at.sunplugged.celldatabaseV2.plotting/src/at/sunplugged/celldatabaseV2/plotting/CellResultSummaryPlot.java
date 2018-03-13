package at.sunplugged.celldatabaseV2.plotting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleInsets;
import datamodel.CellResult;
import datamodel.UIDataPoint;

public class CellResultSummaryPlot extends Composite {

  private final CellResult cellResult;


  public CellResultSummaryPlot(Composite parent, int style, CellResult cellResult) {
    super(parent, style);
    this.cellResult = cellResult;

    createControls();
  }


  private void createControls() {
    Layout layout = createLayout();
    setLayout(layout);

    ChartComposite mainChart = createMainChart();
    mainChart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));

    Composite rpIscChart = createRpIscChart();
    rpIscChart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    ChartComposite rsVocChart = createRsVocChart();
    rsVocChart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    Composite darkRpChart = createDarkRpChart();
    darkRpChart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    ChartComposite darkRsChart = createDarkRsChart();
    darkRsChart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
  }


  private ChartComposite createDarkRsChart() {
    Map<String, String> options = createOptions(PlotHelper.DARK_RS_FIT);
    JFreeChart chart =
        PlotHelper.createJFreeChart(cellResult.getDarkMeasuremenetDataSet(), options);

    NumberAxis range = (NumberAxis) chart.getXYPlot().getRangeAxis();
    NumberAxis domain = (NumberAxis) chart.getXYPlot().getDomainAxis();

    DoubleSummaryStatistics currentStats = cellResult.getDarkMeasuremenetDataSet().getData()
        .stream().mapToDouble(UIDataPoint::getCurrent).summaryStatistics();
    DoubleSummaryStatistics voltageStats = cellResult.getDarkMeasuremenetDataSet().getData()
        .stream().mapToDouble(UIDataPoint::getVoltage).summaryStatistics();


    domain.setRange(0, voltageStats.getMax() * 1.1);
    range.setRange(currentStats.getMin() - (currentStats.getMax() - currentStats.getMin()) / 10.0,
        currentStats.getMax() + (currentStats.getMax() - currentStats.getMin()) / 10.0);
    System.out.println(range.getRange());
    chart.setTitle("Dark Rs");
    return new ChartComposite(this, SWT.NONE, chart, true);
  }


  private Composite createDarkRpChart() {
    Map<String, String> options = createOptions(PlotHelper.DARK_RP_FIT);
    JFreeChart chart =
        PlotHelper.createJFreeChart(cellResult.getDarkMeasuremenetDataSet(), options);

    XYPlot plot = chart.getXYPlot();


    NumberAxis domain = (NumberAxis) plot.getDomainAxis();
    NumberAxis range = (NumberAxis) plot.getRangeAxis();

    double lowerDomain = cellResult.getDarkMeasuremenetDataSet().getData().stream()
        .mapToDouble(UIDataPoint::getVoltage).min().getAsDouble();
    domain.setRange(lowerDomain - Math.abs(lowerDomain) * 0.1, Math.abs(lowerDomain) * 0.1);
    range.setAutoRangeIncludesZero(false);

    chart.setTitle("Dark Rp");
    return new ChartComposite(this, SWT.NONE, chart, true);
  }


  private ChartComposite createRsVocChart() {
    Map<String, String> options = createOptions(PlotHelper.RS_LINE, PlotHelper.VOC_RS_FIT);
    JFreeChart chart =
        PlotHelper.createJFreeChart(cellResult.getLightMeasurementDataSet(), options);

    XYPlot plot = chart.getXYPlot();

    Marker vocMarker =
        new ValueMarker(cellResult.getOpenCircuitVoltage(), Color.MAGENTA, new BasicStroke(1.5f));
    vocMarker.setLabel(String.format("Voc: %.2f", cellResult.getOpenCircuitVoltage()));
    vocMarker.setLabelOffset(new RectangleInsets(30.0f, 20.0f, 0, 0));
    plot.addDomainMarker(vocMarker);

    NumberAxis domain = (NumberAxis) plot.getDomainAxis();
    NumberAxis range = (NumberAxis) plot.getRangeAxis();

    double lowerDomain = cellResult.getOpenCircuitVoltage() * 0.8;
    double upperDomain = cellResult.getOpenCircuitVoltage() * 1.2;
    domain.setRange(lowerDomain, upperDomain);
    range.setAutoRangeIncludesZero(false);
    chart.setTitle("Rs and Voc");
    return new ChartComposite(this, SWT.NONE, chart, true);

  }


  private Composite createRpIscChart() {
    Map<String, String> options = createOptions(PlotHelper.RP_LINE, PlotHelper.ISC_RP_FIT);
    JFreeChart chart =
        PlotHelper.createJFreeChart(cellResult.getLightMeasurementDataSet(), options);

    XYPlot plot = chart.getXYPlot();

    Marker iscMarker = new ValueMarker(cellResult.getShortCircuitCurrent() * -1, Color.MAGENTA,
        new BasicStroke(1.5f));
    iscMarker.setLabel(String.format("Isc: %.4f", cellResult.getShortCircuitCurrent() * -1));
    iscMarker.setLabelOffset(new RectangleInsets(10.0f, 50.0f, 0, 0));
    plot.addRangeMarker(iscMarker);

    NumberAxis domain = (NumberAxis) plot.getDomainAxis();
    NumberAxis range = (NumberAxis) plot.getRangeAxis();

    double lowerDomain = cellResult.getLightMeasurementDataSet().getData().stream()
        .mapToDouble(UIDataPoint::getVoltage).min().getAsDouble();
    double upperDomain = -lowerDomain;
    domain.setRange(lowerDomain, upperDomain);
    range.setAutoRangeIncludesZero(false);


    chart.setTitle("Isc and Rp");
    return new ChartComposite(this, SWT.NONE, chart, true);
  }


  private ChartComposite createMainChart() {
    Map<String, String> options = createOptions(PlotHelper.POWER_CURVE, PlotHelper.POWER_CURVE_FIT);


    JFreeChart chart =
        PlotHelper.createJFreeChart(cellResult.getLightMeasurementDataSet(), options);

    Marker mppVertical =
        new ValueMarker(cellResult.getMaximumPowerVoltage(), Color.YELLOW, new BasicStroke(2.0f));
    Marker mppHorizontal =
        new ValueMarker(cellResult.getMaximumPower(), Color.YELLOW, new BasicStroke(3.0f));

    chart.getXYPlot().addDomainMarker(1, mppVertical, Layer.FOREGROUND);
    chart.getXYPlot().addRangeMarker(1, mppHorizontal, Layer.BACKGROUND);

    chart.setTitle("U-I and Power Curve");
    return new ChartComposite(this, SWT.NONE, chart, true);

  }


  private Layout createLayout() {
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.makeColumnsEqualWidth = true;

    return layout;

  }

  private Map<String, String> createOptions(String... strings) {
    Map<String, String> options = new HashMap<>();
    Arrays.stream(strings).forEach(str -> options.put(str, "true"));
    return options;
  }



}
