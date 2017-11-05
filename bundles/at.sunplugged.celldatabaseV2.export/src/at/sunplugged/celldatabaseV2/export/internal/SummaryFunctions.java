package at.sunplugged.celldatabaseV2.export.internal;

import java.util.function.ToDoubleFunction;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import datamodel.CellGroup;
import datamodel.CellResult;

public class SummaryFunctions {

  public static double getAverage(CellGroup group, ToDoubleFunction<CellResult> valueMapper) {
    return group.getCellResults()
        .stream()
        .mapToDouble(valueMapper)
        .average()
        .getAsDouble();
  }

  public static double getMax(CellGroup group, ToDoubleFunction<CellResult> valueMapper) {
    return group.getCellResults()
        .stream()
        .mapToDouble(valueMapper)
        .max()
        .getAsDouble();
  }

  public static double getStd(CellGroup group, ToDoubleFunction<CellResult> valueMapper) {
    StandardDeviation stdObject = new StandardDeviation();
    double[] values = group.getCellResults()
        .stream()
        .mapToDouble(valueMapper)
        .toArray();
    return stdObject.evaluate(values, getAverage(group, valueMapper));
  }


  private SummaryFunctions() {

  }
}
