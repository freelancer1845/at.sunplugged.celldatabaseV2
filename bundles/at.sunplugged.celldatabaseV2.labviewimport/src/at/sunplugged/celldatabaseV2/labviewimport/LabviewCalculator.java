package at.sunplugged.celldatabaseV2.labviewimport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
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

  private static final int MPP_FIT_DEGREE = 3;



  public static CellResult calculateSingle(LabviewDataFile dataFile)
      throws IOException, LabviewCalculationException {

    CellResult result = createInitial(dataFile);

    evaluateData(result);

    return result;
  }



  private static void evaluateData(CellResult result) throws LabviewCalculationException {
    result.setDarkParallelResistance(-1);
    result.setDarkSeriesResistance(-1);
    result.setEfficiency(-1);
    result.setFillFactor(-1);
    result.setMaximumPowerCurrent(-1);
    result.setMaximumPowerVoltage(-1);
    result.setParallelResistance(-1);
    result.setShortCircuitCurrent(-1);

    double[] vocAndRs = findVocAndRs(result.getLightMeasurementDataSet().getData());
    result.setOpenCircuitVoltage(vocAndRs[0]);
    result.setSeriesResistance(vocAndRs[1]);
    IntStream.range(2, vocAndRs.length)
        .forEach(idx -> result.getRsVocFitCoefficients().add(vocAndRs[idx]));


    double[] iscAndRp = findIscAndRp(result.getLightMeasurementDataSet().getData());
    result.setShortCircuitCurrent(iscAndRp[0]);
    result.setParallelResistance(iscAndRp[1]);
    IntStream.range(2, iscAndRp.length)
        .forEach(idx -> result.getRpIscFitCoefficients().add(iscAndRp[idx]));

    double[] maxPow = findMaxPow(result.getLightMeasurementDataSet().getData());
    result.setMaximumPowerVoltage(maxPow[0]);
    result.setMaximumPowerCurrent(maxPow[1]);
    IntStream.range(2, maxPow.length)
        .forEach(idx -> result.getMppFitCoefficients().add(maxPow[idx]));


    result.setFillFactor(result.getMaximumPower() / result.getOpenCircuitVoltage()
        / result.getShortCircuitCurrent() * 100);
    result.setEfficiency(
        result.getMaximumPower() / result.getLightMeasurementDataSet().getPowerInput()
            / result.getLightMeasurementDataSet().getArea() * 100);

  }



  private static double[] findMaxPow(List<UIDataPoint> data) throws LabviewCalculationException {
    double[][] powerData = new double[data.size()][2];

    IntStream.range(0, data.size()).forEach(idx -> {
      powerData[idx][0] = data.get(idx).getVoltage();
      powerData[idx][1] = data.get(idx).getCurrent() * data.get(idx).getVoltage();
    });


    int startRange = IntStream.range(0, powerData.length).filter(idx -> powerData[idx][1] < 0)
        .findFirst().orElse(-1);
    if (startRange == -1) {
      throw new LabviewCalculationException("Failed to set StartRange while finding Mpp");
    }
    int endRange = IntStream.range(0, powerData.length).filter(idx -> {
      if (idx < startRange) {
        return false;
      } else {
        return powerData[idx][1] > 0;
      }

    }).findFirst().orElse(-1);

    if (endRange == -1) {
      throw new LabviewCalculationException("Failed to set EndRange while finding Mpp");
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
    double mppCurrent = poly.value(mppVoltage);

    double[] result = new double[2 + coeff.length];
    result[0] = mppVoltage;
    result[1] = mppCurrent;
    IntStream.range(0, coeff.length).forEach(idx -> result[idx + 2] = coeff[idx]);

    return result;
  }


  private static double[] findVocAndRs(List<UIDataPoint> dataPoints)
      throws LabviewCalculationException {

    DoubleSummaryStatistics stats =
        dataPoints.stream().mapToDouble(p -> p.getCurrent()).summaryStatistics();

    int idxOfFirstPositiveCurrent = IntStream.range(0, dataPoints.size())
        .filter(idx -> dataPoints.get(idx).getCurrent() > 0).findFirst().orElse(-1);

    double estVoc = dataPoints.get(idxOfFirstPositiveCurrent).getVoltage();

    if (idxOfFirstPositiveCurrent == -1) {
      throw new LabviewCalculationException(
          "Faild to find sign change in current while looking for Voc!");
    }

    int startRange = IntStream.range(0, dataPoints.size())
        .filter(idx -> dataPoints.get(idx).getVoltage() > estVoc * 0.7).findFirst().orElse(-1);
    while (dataPoints.get(startRange).getVoltage() > 0) {
      startRange--;
      if (startRange < 1) {
        break;
      }
    }

    int endRange = IntStream.range(0, dataPoints.size())
        .filter(idx -> dataPoints.get(idx).getVoltage() > estVoc * 1.2).findFirst().orElse(-1);

    if (endRange == -1) {
      endRange = dataPoints.size() - 1;
    }

    if (startRange == -1 || endRange == -1 || startRange >= endRange) {
      double[] range = showRangeDialog(dataPoints, "Voc Range");

      startRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > range[0]).findFirst().orElse(-1);
      endRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > range[1]).findFirst().orElse(-1) - 1;
      if (startRange == -1 || endRange == -1 || startRange >= endRange) {
        throw new LabviewCalculationException(
            "Failed to find good start and end ranges for Voc calculation.");
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


  private static double[] findIscAndRp(List<UIDataPoint> dataPoints)
      throws LabviewCalculationException {

    int idxOfFirstPositiveVoltage = IntStream.range(0, dataPoints.size())
        .filter(idx -> dataPoints.get(idx).getVoltage() > 0).findFirst().orElse(-1);
    int idxOfFirstPositiveCurrent = IntStream.range(0, dataPoints.size())
        .filter(idx -> dataPoints.get(idx).getCurrent() > 0).findFirst().orElse(-1);

    if (idxOfFirstPositiveVoltage == -1) {
      throw new LabviewCalculationException("Failed to find first Positive Voltage.");
    }



    double estVoc = dataPoints.get(idxOfFirstPositiveCurrent).getVoltage();

    int startRange = IntStream.range(0, dataPoints.size())
        .filter(idx -> dataPoints.get(idx).getVoltage() > -estVoc).findFirst().orElse(-1);



    int tenthOfVocIdx = IntStream.range(0, idxOfFirstPositiveCurrent)
        .filter(idx -> dataPoints.get(idx)
            .getVoltage() > dataPoints.get(idxOfFirstPositiveCurrent).getVoltage() * 0.1)
        .findFirst().orElse(-1);
    int endRange = tenthOfVocIdx;
    // if (endRange == -1) {
    // throw new LabviewCalculationException("Failed to identifiy EndRange while looking for Isc");
    // }
    //
    // if (startRange >= endRange) {
    // throw new LabviewCalculationException("StartRange >= EndRange while looking for Isc");
    // }

    if (startRange == -1 || endRange == -1 || startRange >= endRange) {
      double[] range = showRangeDialog(dataPoints, "Isc Range");

      startRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() >= range[0]).findFirst().orElse(-1);
      endRange = IntStream.range(0, dataPoints.size())
          .filter(idx -> dataPoints.get(idx).getVoltage() > range[1]).findFirst().orElse(-1);
      if (startRange == -1 || endRange == -1 || startRange >= endRange) {
        throw new LabviewCalculationException(
            "Failed to find good start and end ranges for Isc calculation.");
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



  private static CellResult createInitial(LabviewDataFile dataFile) throws IOException {

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

    return data;
  }

  private static double[] showRangeDialog(List<UIDataPoint> data, String range) {
    CellMeasurementDataSet dataSet = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
    dataSet.setName("Data");
    dataSet.getData().addAll(EcoreUtil.copyAll(data));
    return PlotHelper.openSelectRangeChart(dataSet, range);
  }

  private LabviewCalculator() {

  }

}
