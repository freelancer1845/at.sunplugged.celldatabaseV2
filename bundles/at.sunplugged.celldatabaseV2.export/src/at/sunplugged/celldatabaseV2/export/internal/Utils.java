package at.sunplugged.celldatabaseV2.export.internal;

import java.util.Vector;
import java.util.function.ToDoubleFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import datamodel.CellResult;

public class Utils {
  private static Pattern keyPattern = Pattern.compile("#[a-zA-Z0-9]+");

  public static Vector<String> decodeKey(String key) {
    Matcher matcher = keyPattern.matcher(key);
    Vector<String> keys = new Vector<>();
    while (matcher.find()) {
      keys.add(matcher.group());
    }

    return keys;
  }

  public static ToDoubleFunction<CellResult> getChainedInterface(Vector<String> keys) {
    if (keys.size() == 1) {
      return getValueGetter(keys.get(0));
    } else if (keys.size() == 2) {
      ToDoubleFunction<CellResult> singleKey = getValueGetter(keys.get(0));
      switch (keys.get(1)) {
        case Keys.DIVIDE_BY_AREA:
          return result -> singleKey.applyAsDouble(result)
              / result.getLightMeasurementDataSet().getArea() / 10000;
        case Keys.DIVIDE_BY_CELLS:
          return result -> singleKey.applyAsDouble(result) / result.getNumberOfCells();
        default:
          throw new IllegalArgumentException("Unexpected second Key: " + keys.get(1));
      }
    } else {
      throw new IllegalArgumentException("More than two Keys supplied... Only two allowed");
    }
  }


  public static ToDoubleFunction<CellResult> getValueGetter(String key) {
    switch (key) {
      case Keys.VOC:
        return result -> result.getOpenCircuitVoltage();
      case Keys.ISC:
        return result -> result.getShortCircuitCurrent();
      case Keys.JSC:
        return result -> {
          System.out.println(String.format("ISC: %f\nAREA: %f", result.getShortCircuitCurrent(),
              result.getLightMeasurementDataSet().getArea()));
          return result.getShortCircuitCurrent() / result.getLightMeasurementDataSet().getArea()
              / 10000;
        };
      case Keys.RP:
        return result -> result.getParallelResistance();
      case Keys.RP_DARK:
        return result -> result.getDarkParallelResistance();
      case Keys.RS:
        return result -> result.getSeriesResistance();
      case Keys.RS_DARK:
        return result -> result.getDarkSeriesResistance();
      case Keys.MP:
        return result -> result.getMaximumPower();
      case Keys.EFF:
        return result -> result.getEfficiency();
      case Keys.FF:
        return result -> result.getFillFactor();
      case Keys.AREA:
        return result -> result.getLightMeasurementDataSet().getArea() * 10000;
      case Keys.POWER_INPUT:
        return result -> result.getLightMeasurementDataSet().getPowerInput();
      default:
        throw new IllegalArgumentException("Unhandled Key: " + key);
    }
  }

  private Utils() {

  }
}
