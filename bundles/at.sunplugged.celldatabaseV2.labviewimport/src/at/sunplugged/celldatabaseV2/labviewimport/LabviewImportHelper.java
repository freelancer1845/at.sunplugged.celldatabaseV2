package at.sunplugged.celldatabaseV2.labviewimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.Preferences;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.PythonSettings;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.UIDataPoint;

public class LabviewImportHelper {

  private static final Logger LOG = LoggerFactory.getLogger(LabviewImportHelper.class);

  public static CellResult readAndCalculateFile(String name, File darkDataFile, File lightDataFile,
      double area, double powerInput) {

    LOG.debug(String.format("Calculating File: Name: %s LightData: %s Area: %d PowerInput: %d",
        name, lightDataFile.getAbsolutePath(), area, powerInput));

    Preferences preferences = ConfigurationScope.INSTANCE.getNode(PrefNodes.PYTHON);

    String pythonLocation =
        preferences.get(PythonSettings.PYTHON_PATH, "D:\\Anaconda3\\python.exe");
    String pythonScript = preferences.get(PythonSettings.LABVIEW_IMPORT_SCRIPT_PATH,
        "C:\\Users\\jasch\\SunpluggedJob\\at.sunplugged.celldatabase.master\\at.sunplugged.celldatabase\\bundles\\at.sunplugged.celldatabase.datareader\\pythonsrc\\main.py");
    String pluginLocation = Activator.getContext().getBundle().getDataFile("").getAbsolutePath();

    if (pythonScript.isEmpty()) {
      LOG.error("Python Script not specified...");
      throw new IllegalArgumentException();
    } else if (new File(pythonScript).exists() == false) {
      LOG.error("Python Script file does not exist...");
      throw new IllegalArgumentException();
    }

    int randomLoc = new Random().nextInt() * -1;

    CommandLine cmdLine = new CommandLine(pythonLocation);

    cmdLine.addArgument(pythonScript);

    HashMap<String, Object> map = new HashMap<>();

    try {
      Files.createDirectory(Paths.get(pluginLocation, String.valueOf(randomLoc)));
    } catch (IOException e2) {
      LOG.error("Failed to create directory...");
      return null;
    }

    map.put("darkDataFile", darkDataFile);
    map.put("lightDataFile", lightDataFile);
    map.put("area", area);
    map.put("powerInput", powerInput);
    cmdLine.addArgument("${darkDataFile}");
    cmdLine.addArgument("${lightDataFile}");
    cmdLine.addArgument("${area}");
    cmdLine.addArgument("${powerInput}");
    cmdLine.addArgument(Paths.get(pluginLocation, String.valueOf(randomLoc)).toString());
    cmdLine.setSubstitutionMap(map);

    ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
    Executor executor = new DefaultExecutor();
    executor.setExitValue(0);
    executor.setWatchdog(watchdog);
    try {
      executor.execute(cmdLine);
    } catch (ExecuteException e1) {
      LOG.error("Failed to execute Python Evaluation... (ExecuteException)", e1);
    } catch (IOException e1) {
      LOG.error("Failed to execute Python Evaluation... (IOException", e1);
    }

    CellResult result = DatamodelFactory.eINSTANCE.createCellResult();
    result.setName(lightDataFile.getName() + " ... Failed...");
    CellMeasurementDataSet lightDataSet = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
    CellMeasurementDataSet darkDataSet = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
    try (Reader reader = new InputStreamReader(
        new FileInputStream(
            Paths.get(pluginLocation, String.valueOf(randomLoc), "/result.json").toFile()),
        "UTF-8")) {
      Gson gson = new GsonBuilder().create();
      ResultJsonClass p = gson.fromJson(reader, ResultJsonClass.class);
      result.setName(name);
      result.setOpenCircuitVoltage(p.getVoc());
      result.setShortCircuitCurrent(p.getIsc());
      result.setFillFactor(p.getFF());
      result.setEfficiency(p.getEff() * 100);
      result.setMaximumPowerCurrent(p.getMaximumPowerI());
      result.setMaximumPowerVoltage(p.getMaximumPowerV());
      result.setDataEvaluated(new Date());
      result.setParallelResistance(p.getRp());
      result.setDarkParallelResistance(p.getRpDark());
      result.setSeriesResistance(p.getRs());
      result.setDarkSeriesResistance(p.getRsDark());
      lightDataSet.setName(name + " (Light)");
      lightDataSet.setPowerInput(p.getPowerInput());
      lightDataSet.setArea(p.getArea());
      darkDataSet.setName(name + " (Dark)");
      darkDataSet.setArea(p.getArea());
    } catch (UnsupportedEncodingException e) {
      LOG.error("Failed to read results...", e);
    } catch (FileNotFoundException e) {
      LOG.error("Failed to read results...", e);
    } catch (IOException e) {
      LOG.error("Failed to read results...", e);
    }

    try (Reader reader = new InputStreamReader(
        new FileInputStream(
            Paths.get(pluginLocation, String.valueOf(randomLoc), "/data.json").toFile()),
        "UTF-8")) {
      Gson gson = new GsonBuilder().create();
      DataJsonClass dataObject = gson.fromJson(reader, DataJsonClass.class);
      List<List<Double>> lightData = dataObject.getLightData();
      for (int i = 0; i < lightData.size(); i++) {
        List<Double> pair = lightData.get(i);
        UIDataPoint dataPoint = DatamodelFactory.eINSTANCE.createUIDataPoint();
        dataPoint.setVoltage(pair.get(0));
        dataPoint.setCurrent(pair.get(1));
        lightDataSet.getData().add(dataPoint);
      }
      List<List<Double>> darkData = dataObject.getDarkData();
      darkData.stream().forEachOrdered(pair -> {
        UIDataPoint dataPoint = DatamodelFactory.eINSTANCE.createUIDataPoint();
        dataPoint.setVoltage(pair.get(0));
        dataPoint.setCurrent(pair.get(1));
        darkDataSet.getData().add(dataPoint);
      });

    } catch (UnsupportedEncodingException e) {
      LOG.error("Failed to read results...", e);
    } catch (FileNotFoundException e) {
      LOG.error("Failed to read results...", e);
    } catch (IOException e) {
      LOG.error("Failed to read results...", e);
    }
    File resultFile = Paths.get(pluginLocation, String.valueOf(randomLoc), "/result.json").toFile();
    resultFile.delete();
    File dataFile = Paths.get(pluginLocation, String.valueOf(randomLoc), "/data.json").toFile();
    dataFile.delete();
    result.setLightMeasurementDataSet(lightDataSet);
    result.setDarkMeasuremenetDataSet(darkDataSet);
    return result;
  }

  private final static class ResultJsonClass {
    private double Area;
    private double Eff;
    private double FF;
    private String ID;
    private double Isc;
    private double MaximumPowerI;
    private double MaximumPowerV;
    private double PowerInput;
    private double Rp;
    private double RpDark;
    private double Rs;
    private double RsDark;
    private double Voc;

    public double getArea() {
      return Area;
    }

    public double getEff() {
      return Eff;
    }

    public double getFF() {
      return FF;
    }

    public String getID() {
      return ID;
    }

    public double getIsc() {
      return Isc;
    }

    public double getMaximumPowerI() {
      return MaximumPowerI;
    }

    public double getMaximumPowerV() {
      return MaximumPowerV;
    }

    public double getPowerInput() {
      return PowerInput;
    }

    public double getRp() {
      return Rp;
    }

    public double getRpDark() {
      return RpDark;
    }

    public double getRs() {
      return Rs;
    }

    public double getRsDark() {
      return RsDark;
    }

    public double getVoc() {
      return Voc;
    }

  }

  private final static class DataJsonClass {
    private List<List<Double>> darkData = new ArrayList<>();
    private List<List<Double>> lightData = new ArrayList<>();

    public List<List<Double>> getDarkData() {
      return darkData;
    }

    public List<List<Double>> getLightData() {
      return lightData;
    }

  }

  private LabviewImportHelper() {}

}
