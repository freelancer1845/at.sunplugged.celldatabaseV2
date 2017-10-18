package at.sunplugged.celldatabaseV2.plotting.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class CellResultExclusingStrategy implements ExclusionStrategy {

  @Override
  public boolean shouldSkipField(FieldAttributes f) {
    if (f.getName().startsWith("e")) {
      if (f.getName().equals("efficiency")) {
        return false;
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean shouldSkipClass(Class<?> clazz) {
    if (clazz.getName().startsWith("e")) {
      return true;
    }
    return false;
  }

}
