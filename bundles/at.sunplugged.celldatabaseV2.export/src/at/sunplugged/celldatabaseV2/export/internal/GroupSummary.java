package at.sunplugged.celldatabaseV2.export.internal;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.eclipse.emf.ecore.EAttribute;
import datamodel.CellGroup;
import datamodel.DatamodelPackage;

public class GroupSummary {

  private final CellGroup group;

  public GroupSummary(CellGroup group) {
    this.group = group;
  }


  public double getAverage(EAttribute attribute, PostCalculator postCalculator) {
    return calculateAverage(attribute, postCalculator);
  }


  public double getStd(EAttribute attribute, PostCalculator postCalculator) {
    return calculateStd(attribute, postCalculator);
  }


  private double calculateAverage(EAttribute attribute, PostCalculator postCalculator) {
    if (DatamodelPackage.Literals.CELL_RESULT.getEStructuralFeatures().contains(attribute)) {
      return group.getCellResults().stream()
          .mapToDouble(result -> postCalculator.calc((double) result.eGet(attribute), result))
          .average().getAsDouble();
    } else if (DatamodelPackage.Literals.CELL_MEASUREMENT_DATA_SET.getEStructuralFeatures()
        .contains(attribute)) {
      return group.getCellResults().stream()
          .mapToDouble(result -> postCalculator
              .calc((double) result.getLightMeasurementDataSet().eGet(attribute), result))
          .average().getAsDouble();
    } else {
      throw new IllegalArgumentException(
          "This attribute is not contained in either CellResults or CellMeasurementDataSet: "
              + attribute.getName());
    }
  }

  private double calculateStd(EAttribute attribute, PostCalculator postCalculator) {
    if (DatamodelPackage.Literals.CELL_RESULT.getEStructuralFeatures().contains(attribute)) {
      StandardDeviation std = new StandardDeviation();
      double[] values = group.getCellResults().stream()
          .mapToDouble(result -> postCalculator.calc((double) result.eGet(attribute), result))
          .toArray();
      double value = std.evaluate(values, getAverage(attribute, postCalculator));
      return value;
    } else if (DatamodelPackage.Literals.CELL_MEASUREMENT_DATA_SET.getEStructuralFeatures()
        .contains(attribute)) {

      StandardDeviation std = new StandardDeviation();
      double[] values = group.getCellResults().stream()
          .mapToDouble(result -> postCalculator
              .calc((double) result.getLightMeasurementDataSet().eGet(attribute), result))
          .toArray();
      double value = std.evaluate(values, getAverage(attribute, postCalculator));
      return value;
    } else {
      throw new IllegalArgumentException(
          "This attribute is not contained in either CellResults or CellMeasurementDataSet: "
              + attribute.getName());
    }

  }
}

