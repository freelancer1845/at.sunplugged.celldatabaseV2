package datamodel.util;

import java.util.List;
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



  private DatamodelUtils() {

  }
}
