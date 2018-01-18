package datamodel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.ToDoubleFunction;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.eclipse.emf.ecore.EAttribute;
import datamodel.CellResult;

public class CellResultsSummary extends ArrayList<CellResult> {

  /**
   * 
   */
  private static final long serialVersionUID = 212763474503702638L;

  public CellResultsSummary() {
    super();
  }

  public CellResultsSummary(Collection<? extends CellResult> arg0) {
    super(arg0.size());
    this.addAll(arg0);
  }

  public CellResultsSummary(int arg0) {
    super(arg0);
  }


  public double getMean(EAttribute attribute) {
    checkIfDoubleAttribute(attribute);
    return getMean(result -> (double) result.eGet(attribute));
  }

  public double getStd(EAttribute attribute) {
    checkIfDoubleAttribute(attribute);
    return getStd(result -> (double) result.eGet(attribute));
  }

  public double getMax(EAttribute attribute) {
    checkIfDoubleAttribute(attribute);
    return getMax(result -> (double) result.eGet(attribute));
  }



  private void checkIfDoubleAttribute(EAttribute attribute) {
    if (attribute.getEType().isInstance(Double.class) == false) {
      throw new IllegalArgumentException("Only double attributes allowed.");
    }
  }

  private double getMean(ToDoubleFunction<CellResult> getter) {
    DescriptiveStatistics stats = new DescriptiveStatistics(getValues(getter));
    return stats.getMean();
  }

  private double getStd(ToDoubleFunction<CellResult> getter) {
    DescriptiveStatistics stats = new DescriptiveStatistics(getValues(getter));
    return stats.getStandardDeviation();
  }

  private double getMax(ToDoubleFunction<CellResult> getter) {
    DescriptiveStatistics stats = new DescriptiveStatistics(getValues(getter));
    return stats.getMax();
  }

  private double[] getValues(ToDoubleFunction<CellResult> getter) {
    return this.stream().mapToDouble(getter).toArray();
  }


}
