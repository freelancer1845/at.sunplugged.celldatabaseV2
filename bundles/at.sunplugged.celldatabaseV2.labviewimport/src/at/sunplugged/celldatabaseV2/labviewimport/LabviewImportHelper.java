package at.sunplugged.celldatabaseV2.labviewimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.PythonSettings;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.UIDataPoint;

public class LabviewImportHelper {

  private static final Logger LOG = LoggerFactory.getLogger(LabviewImportHelper.class);

  public static List<CellResult> readAndCalculateFiles(List<LabviewDataFile> files)
      throws IOException {

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
    try {
      Files.createDirectory(Paths.get(pluginLocation, String.valueOf(randomLoc)));
    } catch (IOException e2) {
      LOG.error("Failed to create directory...");
      throw e2;
    }


    CommandLine cmdLine = new CommandLine(pythonLocation);



    File jsonFile = Paths.get(pluginLocation, String.valueOf(randomLoc), "/files.json").toFile();

    Gson gson = new Gson();
    try {
      Files.write(jsonFile.toPath(), gson.toJson(files).getBytes(), StandardOpenOption.CREATE);
    } catch (IOException e) {
      LOG.error("Failed to write json Files file.", e);
      throw e;
    }



    cmdLine.addArgument(pythonScript);
    cmdLine.addArgument(jsonFile.getPath().toString());
    cmdLine.addArgument(Paths.get(pluginLocation, String.valueOf(randomLoc)).toString());

    ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
    Executor executor = new DefaultExecutor();
    executor.setExitValue(0);
    executor.setWatchdog(watchdog);
    try {
      executor.execute(cmdLine);
    } catch (ExecuteException e1) {
      LOG.error("Failed to execute Python Evaluation... (ExecuteException)", e1);
      throw e1;
    } catch (IOException e1) {
      LOG.error("Failed to execute Python Evaluation... (IOException", e1);
      throw e1;
    }

    List<CellResult> results = new ArrayList<>();

    try (Reader reader = new InputStreamReader(
        new FileInputStream(
            Paths.get(pluginLocation, String.valueOf(randomLoc), "/result.json").toFile()),
        "UTF-8")) {
      Type type = new TypeToken<List<ResultJsonClass>>() {}.getType();
      List<ResultJsonClass> ps = gson.fromJson(reader, type);
      for (ResultJsonClass p : ps) {
        CellResult result = DatamodelFactory.eINSTANCE.createCellResult();
        CellMeasurementDataSet lightDataSet =
            DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
        CellMeasurementDataSet darkDataSet =
            DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();

        result.setName(p.getID());
        result.setOpenCircuitVoltage(p.getVoc());
        result.setShortCircuitCurrent(p.getIsc());
        result.setFillFactor(p.getFF());
        result.setEfficiency(p.getEff() * 100);
        result.setMaximumPowerCurrent(p.getMaximumPowerI());
        result.setMaximumPowerVoltage(p.getMaximumPowerV());
        result.setDataEvaluated(new Date());
        result.setParallelResistance(Math.abs(p.getRp()));
        result.setDarkParallelResistance(Math.abs(p.getRpDark()));
        result.setSeriesResistance(Math.abs(p.getRs()));
        result.setDarkSeriesResistance(Math.abs(p.getRsDark()));
        lightDataSet.setName(p.getID() + " (Light)");
        lightDataSet.setPowerInput(p.getPowerInput());
        lightDataSet.setArea(p.getArea());
        darkDataSet.setName(p.getID() + " (Dark)");
        darkDataSet.setArea(p.getArea());
        List<List<Double>> lightData = p.getLightData();
        for (int i = 0; i < lightData.size(); i++) {
          List<Double> pair = lightData.get(i);
          UIDataPoint dataPoint = DatamodelFactory.eINSTANCE.createUIDataPoint();
          dataPoint.setVoltage(pair.get(0));
          dataPoint.setCurrent(pair.get(1));
          lightDataSet.getData().add(dataPoint);
        }
        List<List<Double>> darkData = p.getDarkData();
        darkData.stream().forEachOrdered(pair -> {
          UIDataPoint dataPoint = DatamodelFactory.eINSTANCE.createUIDataPoint();
          dataPoint.setVoltage(pair.get(0));
          dataPoint.setCurrent(pair.get(1));
          darkDataSet.getData().add(dataPoint);
        });
        result.setLightMeasurementDataSet(lightDataSet);
        result.setDarkMeasuremenetDataSet(darkDataSet);

        System.out.println(Arrays.toString(p.getLightUIFitCoefficients().toArray()));
        System.out.println(Arrays.toString(p.getDarkUIFitCoefficients().toArray()));
        System.out.println(Arrays.toString(p.getPowerUIFitCoefficients().toArray()));
        results.add(result);
      }

    } catch (UnsupportedEncodingException e) {
      LOG.error("Failed to read results...", e);
      throw e;
    } catch (FileNotFoundException e) {
      LOG.error("Failed to read results...", e);
      throw e;
    } catch (IOException e) {
      LOG.error("Failed to read results...", e);
      throw e;
    }

    return results;
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
    private List<List<Double>> lightData;
    private List<List<Double>> darkData;
    private List<Double> lightUIFitCoefficients;
    private List<Double> darkUIFitCoefficients;
    private List<Double> powerUIFitCoefficients;

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

    public List<List<Double>> getDarkData() {
      return darkData;
    }

    public List<List<Double>> getLightData() {
      return lightData;
    }


    public List<Double> getDarkUIFitCoefficients() {
      return darkUIFitCoefficients;
    }

    public List<Double> getLightUIFitCoefficients() {
      return lightUIFitCoefficients;
    }

    public List<Double> getPowerUIFitCoefficients() {
      return powerUIFitCoefficients;
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
