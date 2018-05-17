package datamodel.util;

import java.text.Collator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.RegexPatterns;
import at.sunplugged.celldatabaseV2.common.StringUtils;
import datamodel.CellGroup;
import datamodel.CellResult;
import datamodel.impl.CellGroupImpl;

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

  public static class Comparators {
    public static Comparator<CellResult> comapreCellResultsNatural() {
      return new CellResultComparetor();
    }

    public static Comparator<String> compareCellResultNamesNatural() {
      return new CellResultNameComparator();
    }

    public static Comparator<CellGroup> compareGroupsNatural() {
      return new CellGroupComparator();
    }

    private static class CellResultNameComparator implements Comparator<String> {

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
      private CellResultNameComparator comparetor = new CellResultNameComparator();

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

    private static class CellGroupComparator implements Comparator<CellGroup> {


      private static final Logger log = LoggerFactory.getLogger(CellGroupComparator.class);

      private final CellResultNameComparator cellResultsNameComparator =
          new CellResultNameComparator();

      @Override
      public int compare(CellGroup arg0, CellGroup arg1) {
        String name0 = cleanName(arg0.getName());
        String name1 = cleanName(arg1.getName());
        if (name0.equals(CellGroupImpl.CANNOT_BE_DEDUCED)
            || name1.equals(CellGroupImpl.CANNOT_BE_DEDUCED)) {
          return 0;
        }
        try {


          Date date0 = getDate(name0);
          Date date1 = getDate(name1);

          if (date0.equals(date1)) {
            String idS0 = name0.split("[-_]")[1].replaceAll("(?=[^0-9]).+", "");
            String idS1 = name1.split("[-_]")[1].replaceAll("(?=[^0-9]).+", "");



            int id0 = Integer.valueOf(idS0);
            int id1 = Integer.valueOf(idS1);
            return Integer.compare(id0, id1);
          } else {
            return date1.compareTo(date0);
          }


        } catch (Exception e1) {
          log.debug(StringUtils.format("Couldnt successfully compare group names: %s and %s", name0,
              name1));
          return 0;
        }
      }

      private String cleanName(String name) {
        return name.replaceFirst("Mod_", "");
      }

      private Date getDate(String name) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.GERMAN);
        Date date = format.parse(name);
        return date;
      }

    }
  }



  private DatamodelUtils() {

  }
}
