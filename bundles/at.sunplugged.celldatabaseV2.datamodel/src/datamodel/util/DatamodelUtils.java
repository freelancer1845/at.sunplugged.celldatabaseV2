package datamodel.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.RegexPatterns;
import datamodel.CellResult;

public class DatamodelUtils {

  public static Map<String, List<CellResult>> groupCellResultsByName(List<CellResult> results) {


    String regex = ConfigurationScope.INSTANCE.getNode(PrefNodes.REGEX_PATTERNS)
        .get(RegexPatterns.CELL_GROUP_NAME_PART_OF_CELL_RESULT, "");

    Pattern p = Pattern.compile(regex);


    Map<String, List<CellResult>> grouped =
        results.stream().collect(Collectors.groupingBy(cellResult -> {
          Matcher matcher = p.matcher(cellResult.getName());
          if (matcher.find() == true) {
            return matcher.group(0);
          } else {
            return "";
          }
        }));
    return grouped;
  }

  public static class Comparetors {
    public static Comparator<CellResult> comapreCellResultsNatural() {
      return new CellResultComparetor();
    }

    public static Comparator<String> compareCellResultNamesNatural() {
      return new CellResultNameComparetor();
    }

    private static class CellResultNameComparetor implements Comparator<String> {

      @Override
      public int compare(String o1, String o2) {
        String endingNumber0String = o1.replaceFirst(".+?(?=[0-9]+$)", "").replaceAll("_", "");
        String endingNumber1String = o2.replaceFirst(".+?(?=[0-9]+$)", "").replaceAll("_", "");
        try {
          if (endingNumber0String.isEmpty() == false && endingNumber1String.isEmpty() == false) {
            return Integer.valueOf(endingNumber0String)
                .compareTo(Integer.valueOf(endingNumber1String));
          } else {
            return Collator.getInstance(Locale.GERMANY).compare(o1, o2);
          }
        } catch (NumberFormatException e) {
          return 0;
        }

      }

    }

    private static class CellResultComparetor implements Comparator<CellResult> {
      private CellResultNameComparetor comparetor = new CellResultNameComparetor();

      @Override
      public int compare(CellResult o1, CellResult o2) {
        if (o1 != null && o2 != null) {
          String name0 = o1.getName();
          String name1 = o2.getName();
          return comparetor.compare(name0, name1);
        } else {
          return 0;
        }
      }
    }
  }



  private DatamodelUtils() {

  }
}
