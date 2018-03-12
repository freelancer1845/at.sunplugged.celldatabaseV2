package at.sunplugged.celldatabaseV2.plotting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.Executor;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.PythonSettings;
import at.sunplugged.celldatabaseV2.plotting.internal.CellResultExclusingStrategy;
import at.sunplugged.celldatabaseV2.plotting.internal.CellResultJFreeChartPlotter;
import at.sunplugged.celldatabaseV2.plotting.internal.ChartPlotter;
import at.sunplugged.celldatabaseV2.plotting.osgi.Activator;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;

public class PlotHelper {

  private final static Logger LOG = LoggerFactory.getLogger(PlotHelper.class);

  public static final String POWER_CURVE = "powerCurve";

  public static final String POWER_CURVE_FIT = "powerCurveFit";

  public static final String MP_POINT = "mpPoint";

  public static final String RP_LINE = "rpLine";

  public static final String RS_LINE = "rsLine";

  public static final String VOC_RS_FIT = "vocRsFit";

  public static final String ISC_RP_FIT = "iscRpFit";


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


  public static void plotCellResults(List<CellResult> cellResults,
      List<CellMeasurementDataSet> dataSets) {
    LOG.debug("Plotting ...");

    Preferences pref = ConfigurationScope.INSTANCE.getNode(PrefNodes.PYTHON);

    String pythonPath = pref.get(PythonSettings.PYTHON_PATH, "D:/Anaconda3/python.exe");
    if (pythonPath.isEmpty()) {
      LOG.error("Python Path may not be Empty!!!");
      return;
    }
    String scriptPath = pref.get(PythonSettings.PLOT_SCRIPT_PATH,
        "C:\\Users\\jasch\\SunpluggedJob\\at.sunplugged.celldatabase.master\\at.sunplugged.celldatabase\\features\\at.sunplugged.celldatabase.feature\\python\\plotScript.py");

    File dataFile = Activator.getContext().getDataFile("plotCellResults.json");

    Gson gson =
        new GsonBuilder().addSerializationExclusionStrategy(new CellResultExclusingStrategy())
            .setPrettyPrinting().create();

    List<EObject> completeList = new ArrayList<>();
    completeList.addAll(cellResults);
    completeList.addAll(dataSets);
    try (FileWriter writer = new FileWriter(dataFile)) {
      Type typeOfSrc = new TypeToken<List<CellResult>>() {}.getType();

      gson.toJson(completeList, typeOfSrc, writer);

    } catch (IOException e) {
      LOG.error("Failed to execute plotting. Failed to write data to json...", e);
      return;
    }

    CommandLine cmdLine = new CommandLine(pythonPath);

    cmdLine.addArgument(scriptPath);

    HashMap<String, Object> map = new HashMap<>();

    map.put("file", dataFile.getAbsolutePath());
    cmdLine.addArgument("${file}");
    cmdLine.setSubstitutionMap(map);

    // DefaultExecuteResultHandler resultHandler = new
    // DefaultExecuteResultHandler();

    Executor executor = new DefaultExecutor();
    try {
      executor.execute(cmdLine, new ExecuteResultHandler() {

        @Override
        public void onProcessFailed(ExecuteException e) {
          LOG.error("Failed to execute plot operation...", e);
        }

        @Override
        public void onProcessComplete(int exitValue) {
          if (exitValue == 0) {
            LOG.debug("Successfull plotted CellResults...");
          } else {
            LOG.error("Unexpected ExitValue: " + exitValue);
          }
        }
      });
    } catch (ExecuteException e) {
      LOG.error("Failed to execute plot operation...", e);
      return;
    } catch (IOException e) {
      LOG.error("Failed to communicate with executor...", e);
      return;
    }


  }

  private PlotHelper() {

  }

}

