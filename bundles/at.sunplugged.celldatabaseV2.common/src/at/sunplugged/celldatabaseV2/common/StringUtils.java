package at.sunplugged.celldatabaseV2.common;

import java.util.Locale;

public class StringUtils {

  public static String format(String format, Object... args) {
    return String.format(Locale.US, format, args);
  }


  private StringUtils() {}

}
