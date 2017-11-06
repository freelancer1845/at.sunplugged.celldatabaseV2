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


  public static ToDoubleFunction<CellResult> getValueGetter(String key) {
    switch (key) {
      case Keys.VOC:
        return result -> result.getOpenCircuitVoltage();
      case Keys.ISC:
        return result -> result.getSeriesResistance();
      case Keys.JSC:
        return result -> result.getSeriesResistance() / result.getLightMeasurementDataSet()
            .getArea() / 10000;
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
      default:
        throw new IllegalArgumentException("Unhandled Key: " + key);
    }
  }

  private Utils() {

  }
}
