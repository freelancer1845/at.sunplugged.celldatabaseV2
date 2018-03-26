package at.sunplugged.celldatabasev2.rcp.main.imperativeexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectionUtils {

  public static boolean isSelectionOfType(Object selection, Class<?> clazz) {
    if (selection instanceof Object[]) {
      return !Arrays.stream((Object[]) selection).anyMatch(item -> clazz.isInstance(item) == false);
    } else {
      return clazz.isInstance(selection);
    }
  }

  public static boolean isSelectionOnlyOfTheseTypes(Object selection, Class<?>... classes) {
    if (selection instanceof Object[]) {
      return !Arrays.stream((Object[]) selection)
          .anyMatch(item -> isSelectionOnlyOfTheseTypes(item, classes) == false);
    } else {
      return Arrays.stream(classes).anyMatch(clazz -> clazz.isInstance(selection));
    }
  }


  @SuppressWarnings("unchecked")
  public static <T> List<T> getAllFromSelectionOfType(Object selection, Class<T> clazz) {
    List<T> list = new ArrayList<>();
    if (selection instanceof Object[]) {
      Arrays.stream((Object[]) selection).filter(item -> clazz.isInstance(item))
          .forEach(item -> list.add((T) item));
    } else if (clazz.isInstance(selection)) {
      list.add((T) selection);
    }
    return list;
  }

  public static Map<Class<?>, List<?>> getAllFromSelectionOfAnyType(Object selection,
      Class<?>... classes) {
    Map<Class<?>, List<?>> map = new HashMap<>();

    Arrays.stream(classes)
        .forEach(clazz -> map.put(clazz, getAllFromSelectionOfType(selection, clazz)));


    return map;
  }

  private SelectionUtils() {}

}
