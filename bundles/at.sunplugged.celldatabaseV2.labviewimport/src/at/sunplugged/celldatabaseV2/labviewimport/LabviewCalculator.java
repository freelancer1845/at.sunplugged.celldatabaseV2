package at.sunplugged.celldatabaseV2.labviewimport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.plotting.PlotHelper;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.UIDataPoint;

public class LabviewCalculator {

  private static final Logger log = LoggerFactory.getLogger(LabviewCalculator.class);

  private static final int linesToSkip = 2;

  private static final String DELIMITER = "\t";

  private static final int VOC_FIT_DEGREE = 3;

  private static final int ISC_FIT_DEGREE = 1;

  private static final int DARK_RP_FIT_DEGREE = 1;

  private static final int DARK_RS_FIT_DEGREE = 1;

  private static final int MPP_FIT_DEGREE = 3;

  private LabviewDataFile dataFile;

  private CellResult result;

  private boolean askForUserInput = false;

  private List<LabviewCalculationException> calculationErrors = new ArrayList<>();

  public CellResult evaluate(LabviewDataFile dataFile, boolean askForUserInput)
      throws IOException, LabviewCalculationException {
    this.dataFile = dataFile;
    this.askForUserInput = askForUserInput;

    result = createInitial();

    evaluateData();

    return result;
  }

  public CellResult reEvaluate(CellResult result, boolean askForUserInput)
      throws LabviewCalculationException {
    this.askForUserInput = askForUserInput;
    this.result = result;
    evaluateData();
    return result;
  }



  private void evaluateData() throws LabviewCalculationException {
    try {

      result.setDarkParallelResistance(-1);
      result.setDarkSeriesResistance(-1);
      result.setEfficiency(-1);
      result.setFillFactor(-1);
      result.setMaximumPowerCurrent(-1);
      result.setMaximumPowerVoltage(-1);
      result.setParallelResistance(-1);
      result.setShortCircuitCurrent(-1);

      double[] vocAndRs = findVocAndRs();
      result.setOpenCircuitVoltage(vocAndRs[0]);
      result.setSeriesResistance(vocAndRs[1]);
      result.getRsVocFitCoefficients().clear();
      IntStream.range(2, vocAndRs.length)
          .forEach(idx -> result.getRsVocFitCoefficients().add(vocAndRs[idx]));


      double[] iscAndRp = findIscAndRp();
      result.setShortCircuitCurrent(iscAndRp[0]);
      result.setParallelResistance(iscAndRp[1]);
      result.getRpIscFitCoefficients().clear();
      IntStream.range(2, iscAndRp.length)
          .forEach(idx -> result.getRpIscFitCoefficients().add(iscAndRp[idx]));

      double[] maxPow = findMaxPow();
      result.setMaximumPowerVoltage(maxPow[0]);
      result.setMaximumPowerCurrent(maxPow[1]);
      result.getMppFitCoefficients().clear();
      IntStream.range(2, maxPow.length)
          .forEach(idx -> result.getMppFitCoefficients().add(maxPow[idx]));


      double[] darkRp = findDarkRp();
      result.setDarkParallelResistance(darkRp[0]);
      result.getDarkRpFitCoefficients().clear();
      IntStream.range(1, darkRp.length)
          .forEach(idx -> result.getDarkRpFitCoefficients().add(darkRp[idx]));

      double[] darkRs = findDarkRs();
      result.setDarkSeriesResistance(darkRs[0]);
      result.getDarkRsFitCoefficients().clear();
      IntStream.range(1, darkRs.length)
          .forEach(idx -> result.getDarkRsFitCoefficients().add(darkRs[idx]));

      result.setFillFactor(result.getMaximumPower() / result.getOpenCircuitVoltage()
          / result.getShortCircuitCurrent() * 100);
      result.setEfficiency(
          result.getMaximumPower() / result.getLightMeasurementDataSet().getPowerInput()
              / result.getLightMeasurementDataSet().getArea() * 100);
    } catch (MathIllegalArgumentException a) {
      throwCalculationException("Math Calculations failed. " + a.getMessage(), a);
    }
  }



  private double[] findDarkRs() throws LabviewCalculationException {
    List<UIDataPoint> data = result.getDarkMeasuremenetDataSet().getData();
    int startRange;
    int endRange;

    double maxCurrent = data.stream().mapToDouble(point -> point.getCurrent()).max().getAsDouble();

    if (askForUserInput == true) {
      double[] range = showRangeDialog(data, "Dark Rs Fit Range");

      startRange = IntStream.range(0, data.size())
          .filter(idx -> data.get(idx).getVoltage() > range[0]).findFirst().orElse(-1);
      endRange = IntStream.range(0, data.size())
          .filter(idx -> data.get(idx).getVoltage() > range[1]).findFirst().orElse(-1) - 1;
    } else {
      startRange = IntStream.range(0, data.size())
          .filter(idx -> data.get(idx).getCurrent() > maxCurrent * 0.9).findFirst().orElse(0) - 1;

      endRange = data.size();

      if (startRange == -1 || endRange == -1 || startRange >= endRange) {
        throwCalculationException(
            "Failed to find good start and end ranges for Dark Rs calculation.");
      }
    }


    log.debug(String.format("Using %d - %d as range for finding Dark Rs.", startRange, endRange));

    final WeightedObservedPoints points = new WeightedObservedPoints();

    IntStream.range(startRange, endRange)
        .forEach(idx -> points.add(data.get(idx).getVoltage(), data.get(idx).getCurrent()));

    PolynomialCurveFitter fitter = PolynomialCurveFitter.create(DARK_RS_FIT_DEGREE);
    double[] coeff = fitter.fit(points.toList());
    PolynomialFunction poly = new PolynomialFunction(coeff);
    double rs = 1.0 / poly.derivative().value(0);

    double[] result = new double[1 + coeff.length];
    result[0] = rs;
    IntStream.range(0, coeff.length).forEach(idx -> result[idx + 1] = coeff[idx]);

    return result;
  }

  private double[] findDarkRp() throws LabviewCalculationException {
    List<UIDataPoint> data = result.getDarkMeasuremenetDataSet().getData();
    int startRange;
    int endRange;
    if (askForUserInput == true) {
      double[] range = showRangeDialog(data, "Dark Rp Fit Range");

      startRange = IntStream.range(0, data.size())
          .filter(idx -> data.get(idx).getVoltage() > range[0]).findFirst().orElse(-1);
      endRange = IntStream.range(0, data.size())
          .filter(idx -> data.get(idx).getVoltage() > range[1]).findFirst().orElse(-1) - 1;
    } else {
      startRange = 0;

      endRange = IntStream.range(0, data.size()).filter(idx -> data.get(idx).getVoltage() > 0)
          .findFirst().orElse(-1);

      if (startRange == -1 || endRange == -1 || startRange >= endRange) {
        throwCalculationException(
            "Failed to find good start and end ranges for Dark Rp calculation.");
      }
    }


    log.debug(String.format("Using %d - %d as range for finding Dark Rp.", startRange, endRange));

    final WeightedObservedPoints points = new WeightedObservedPoints();

    IntStream.range(startRange, endRange)
        .forEach(idx -> points.add(data.get(idx).getVoltage(), data.get(idx).getCurrent()));

    PolynomialCurveFitter fitter = PolynomialCurveFitter.create(DARK_RP_FIT_DEGREE);
    double[] coeff = fitter.fit(points.toList());
    PolynomialFunction poly = new PolynomialFunction(coeff);
    double rp = 1.0 / poly.derivative().value(0);

    double[] result = new double[1 + coeff.length];
    result[0] = rp;
    IntStream.range(0, coeff.length).forEach(idx -> result[idx + 1] = coeff[idx]);

    return result;
  }


  private double[] findMaxPow() throws LabviewCalculationException {
    List<UIDataPoint> data = result.getLightMeasurementDataSet().getData();

    double[][] powerData = new double[data.size()][2];

    IntStream.range(0, data.size()).forEach(idx -> {
      powerData[idx][0] = data.get(idx).getVoltage();
      powerData[idx][1] = data.get(idx).getCurrent() * data.get(idx).getVoltage();
    });


    int startRange;
    int endRange;

    if (askForUserInput == true) {
      List<UIDataPoint> powerDataList = new ArrayList<>();
      Arrays.stream(powerData).forEach(p -> {
        UIDataPoint dataPoint = DatamodelFactory.eINSTANCE.createUIDataPoint();
        dataPoint.setVoltage(p[0]);
        dataPoint.setCurrent(p[1]);
        powerDataList.add(dataPoint);
      });

      double[] range = showRangeDialog(powerDataList, "Mpp Fit Range");

      startRange = IntStream.range(0, powerDataList.size())
          .filter(idx -> powerDataList.get(idx).getVoltage() > range[0]).findFirst().orElse(-1);
      endRange = IntStream.range(0, powerDataList.size())
          .filter(idx -> powerDataList.get(idx).getVoltage() > range[1]).findFirst().orElse(-1) - 1;
    } else {

      startRange = IntStream.range(0, powerData.length).filter(idx -> powerData[idx][1] < 0)
          .findFirst().orElse(-1);
      if (startRange == -1) {
        throwCalculationException("Failed to set StartRange while finding Mpp");
      }
      endRange = IntStream.range(0, powerData.length).filter(idx -> {
        if (idx < startRange) {
          return false;
        } else {
          return powerData[idx][1] > 0;
        }

      }).findFirst().orElse(-1);

      if (endRange == -1 || startRange == endRange) {
        throwCalculationException("Failed to set EndRange while finding Mpp");
      }

    }



    log.debug(String.format("Using %d - %d as range for finding Mpp. ( %f - %f V)", startRange,
        endRange, powerData[startRange][0], powerData[endRange][0]));

    WeightedObservedPoints points = new WeightedObservedPoints();
    IntStream.range(startRange, endRange)
        .forEach(idx -> points.add(powerData[idx][0], powerData[idx][1]));
    PolynomialCurveFitter fitter = PolynomialCurveFitter.create(MPP_FIT_DEGREE);
    double[] coeff = fitter.fit(points.toList());
    PolynomialFunction poly = new PolynomialFunction(coeff);



    LaguerreSolver solver = new LaguerreSolver();
    double mppVoltage = solver.solve(1000, poly.polynomialDerivative(), powerData[startRange][0],
        powerData[endRange][0]);
    double mppCurrent = poly.value(mppVoltage) / mppVoltage;

    double[] result = new double[2 + coeff.length];
    result[0] = mppVoltage;
    result[1] = mppCurrent;
    IntStream.range(0, coeff.length).forEach(idx -> result[idx + 2] = coeff[idx]);

    return result;
  }


  private double[] findVocAndRs() throws LabviewCalculationException {

    List<UIDataPoint> dataPoints = result.getLightMeasurementDataSet().getData();
    int startRange;
    int endRange;

    if (askForUserInput == true) {
      double[] range = showRangeDialog(dataPoints, "Voc Fit Range");

      startRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > range[0]).findFirst().orElse(-1);
      endRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > range[1]).findFirst().orElse(-1) - 1;
    } else {
      int idxOfFirstPositiveCurrent = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getCurrent() > 0).findFirst().orElse(-1);

      double estVoc = dataPoints.get(idxOfFirstPositiveCurrent).getVoltage();

      if (idxOfFirstPositiveCurrent == -1) {
        throwCalculationException("Faild to find sign change in current while looking for Voc!");
      }

      startRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > estVoc * 0.7).findFirst().orElse(-1);
      while (dataPoints.get(startRange).getVoltage() > 0) {
        startRange--;
        if (startRange < 1) {
          break;
        }
      }

      endRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > estVoc * 1.2).findFirst().orElse(-1);

      if (endRange == -1) {
        endRange = dataPoints.size() - 1;
      }

      if (startRange == -1 || endRange == -1 || startRange >= endRange) {
        throwCalculationException("Failed to find good start and end ranges for Voc calculation.");
      }
    }



    log.debug(String.format("Using Range %d - %d for finding Voc.", startRange, endRange));

    final WeightedObservedPoints points = new WeightedObservedPoints();

    IntStream.range(startRange, endRange).forEach(
        idx -> points.add(dataPoints.get(idx).getVoltage(), dataPoints.get(idx).getCurrent()));

    PolynomialCurveFitter fitter = PolynomialCurveFitter.create(VOC_FIT_DEGREE);
    double[] coeff = fitter.fit(points.toList());
    PolynomialFunction poly = new PolynomialFunction(coeff);


    LaguerreSolver solver = new LaguerreSolver();
    double voc = solver.solve(10000, poly, dataPoints.get(startRange).getVoltage(),
        dataPoints.get(endRange).getVoltage());
    double rs = 1.0 / poly.derivative().value(voc);

    double[] result = new double[2 + coeff.length];

    result[0] = voc;
    result[1] = rs;
    IntStream.range(0, coeff.length).forEach(idx -> result[2 + idx] = coeff[idx]);
    return result;
  }


  private double[] findIscAndRp() throws LabviewCalculationException {

    List<UIDataPoint> dataPoints = result.getLightMeasurementDataSet().getData();

    int startRange;
    int endRange;

    if (askForUserInput == true) {
      double[] range = showRangeDialog(dataPoints, "Isc Fit Range");

      startRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() >= range[0]).findFirst().orElse(-1);
      endRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > range[1]).findFirst().orElse(-1);
    } else {
      int idxOfFirstPositiveVoltage = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > 0).findFirst().orElse(-1);
      int idxOfFirstPositiveCurrent = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getCurrent() > 0).findFirst().orElse(-1);

      if (idxOfFirstPositiveVoltage == -1) {
        throwCalculationException("Failed to find first Positive Voltage.");
      }



      double estVoc = dataPoints.get(idxOfFirstPositiveCurrent).getVoltage();

      startRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > -estVoc).findFirst().orElse(-1);



      int tenthOfVocIdx = IntStream.range(0, idxOfFirstPositiveCurrent)
          .filter(idx -> dataPoints.get(idx)
              .getVoltage() > dataPoints.get(idxOfFirstPositiveCurrent).getVoltage() * 0.1)
          .findFirst().orElse(-1);
      endRange = tenthOfVocIdx;
      if (startRange == -1 || endRange == -1 || startRange >= endRange) {
        throwCalculationException("Failed to find good start and end ranges for Isc calculation.");
      }
    }



    log.debug(String.format("Using Range %d - %d for finding Isc.", startRange, endRange));

    final WeightedObservedPoints points = new WeightedObservedPoints();

    IntStream.range(startRange, endRange).forEach(
        idx -> points.add(dataPoints.get(idx).getVoltage(), dataPoints.get(idx).getCurrent()));

    PolynomialCurveFitter fitter = PolynomialCurveFitter.create(ISC_FIT_DEGREE);
    double[] coeff = fitter.fit(points.toList());
    PolynomialFunction poly = new PolynomialFunction(coeff);
    double isc = poly.value(0);
    double rp = 1.0 / poly.derivative().value(0);

    double[] result = new double[2 + coeff.length];
    result[0] = isc * -1;
    result[1] = rp;
    IntStream.range(0, coeff.length).forEach(idx -> result[idx + 2] = coeff[idx]);

    return result;
  }



  private CellResult createInitial() throws IOException {

    CellResult cellResult = DatamodelFactory.eINSTANCE.createCellResult();
    CellMeasurementDataSet lightSet = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
    CellMeasurementDataSet darkSet = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();

    lightSet.getData().addAll(getData(dataFile.getAbsolutPathLight()));
    lightSet.setName(dataFile.getNameLight());
    lightSet.setArea(dataFile.getArea());
    lightSet.setPowerInput(dataFile.getPowerInput());
    cellResult.setLightMeasurementDataSet(lightSet);

    darkSet.getData().addAll(getData(dataFile.getAbsolutPathDark()));
    darkSet.setName(dataFile.getNameDark());
    darkSet.setArea(dataFile.getArea());
    darkSet.setPowerInput(0);
    cellResult.setDarkMeasuremenetDataSet(darkSet);

    cellResult.setName(dataFile.getName());


    return cellResult;
  }

  public static List<UIDataPoint> getData(String fileName) throws IOException {
    List<UIDataPoint> data = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      int currentLine = 0;

      String line;

      while ((line = reader.readLine()) != null) {
        currentLine++;
        if (currentLine <= linesToSkip) {
          continue;
        }
        if (line.isEmpty()) {
          continue;
        }
        String[] strValues = line.split(DELIMITER);
        UIDataPoint dataPoint = DatamodelFactory.eINSTANCE.createUIDataPoint();


        try {
          dataPoint.setVoltage(Double.valueOf(strValues[0]));
          dataPoint.setCurrent(Double.valueOf(strValues[1]));
        } catch (NumberFormatException e) {
          throw new IOException("File formatted Incorrectly.", e);
        }

        data.add(dataPoint);
      }


    } catch (FileNotFoundException e) {
      throw new IOException("File was not found.", e);
    } catch (IOException e) {
      throw e;
    }

    data.sort((p1, p2) -> Double.compare(p1.getVoltage(), p2.getVoltage()));
    IntStream.range(1, data.size()).forEach(idx -> {
      if (data.get(idx - 1).getVoltage() == data.get(idx).getVoltage()) {
        data.get(idx).setVoltage(data.get(idx).getVoltage() + 1e-6);
      }

    });

    return data;
  }

  private double[] showRangeDialog(List<UIDataPoint> data, String range) {
    CellMeasurementDataSet dataSet = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
    dataSet.setName(result.getName());
    dataSet.getData().addAll(EcoreUtil.copyAll(data));
    return PlotHelper.openSelectRangeChart(dataSet, range);
  }

  private void throwCalculationException(String message) throws LabviewCalculationException {
    LabviewCalculationException e = new LabviewCalculationException(message);
    this.calculationErrors.add(e);
    throw e;
  }

  private void throwCalculationException(String message, Throwable a)
      throws LabviewCalculationException {
    LabviewCalculationException e = new LabviewCalculationException(message, a);
    this.calculationErrors.add(e);
    throw e;
  }

  public List<LabviewCalculationException> getCalculationErrors() {
    return Collections.unmodifiableList(calculationErrors);
  }

  public CellResult getResult() {
    return result;
  }

  public LabviewDataFile getDataFile() {
    return dataFile;
  }


}
