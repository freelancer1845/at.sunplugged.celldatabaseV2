package at.sunplugged.celldatabaseV2.plotting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.plotting.internal.CellResultJFreeChartPlotter;
import at.sunplugged.celldatabaseV2.plotting.internal.ChartPlotter;
import datamodel.CellMeasurementDataSet;

public class PlotHelper {

  private final static Logger LOG = LoggerFactory.getLogger(PlotHelper.class);

  public static final String POWER_CURVE = "powerCurve";

  public static final String POWER_CURVE_FIT = "powerCurveFit";

  public static final String MP_POINT = "mpPoint";

  public static final String RP_LINE = "rpLine";

  public static final String RS_LINE = "rsLine";

  public static final String VOC_RS_FIT = "vocRsFit";

  public static final String ISC_RP_FIT = "iscRpFit";

  public static final String DARK_RP_FIT = "darkRpFit";

  public static final String DARK_RS_FIT = "darkRsFit";

  public static final String DATA_POINTS = "drawDataPoints";


  public static JFreeChart createJFreeChart(CellMeasurementDataSet dataSet,
      Map<String, String> options) {
    ChartPlotter chartPlotter = new CellResultJFreeChartPlotter(Arrays.asList(dataSet));

    return chartPlotter.getChart(options);
  }

  public static double[] openSelectRangeChart(CellMeasurementDataSet dataSet, String range) {
    List<Double> points = new ArrayList<>();

    Display.getDefault().syncExec(() -> {
      SelectRangeFromChartDialog dialog = new SelectRangeFromChartDialog(
          Display.getDefault().getActiveShell(), dataSet, new String[] {range});
      dialog.open();
      points.add(dialog.getPoints()[0][0]);
      points.add(dialog.getPoints()[0][1]);
    });
    System.out.println(points.toString());
    return points.stream().mapToDouble(p -> p).toArray();
  }

  public static ChartComposite plotChartToComposite(Composite composite, JFreeChart chart) {
    ChartComposite chartComposite = new ChartComposite(composite, SWT.NONE, chart, true);
    chartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    chartComposite.moveAbove(null);
    composite.layout();

    return chartComposite;
  }

  public static JFreeChart createJFreeChart(List<CellMeasurementDataSet> dataSets,
      Map<String, String> options) {
    ChartPlotter chartPlotter = new CellResultJFreeChartPlotter(dataSets);

    return chartPlotter.getChart(options);
  }


  private PlotHelper() {

  }

}

