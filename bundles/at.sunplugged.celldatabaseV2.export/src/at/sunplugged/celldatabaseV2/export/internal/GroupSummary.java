package at.sunplugged.celldatabaseV2.export.internal;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.eclipse.emf.ecore.EAttribute;
import datamodel.CellGroup;
import datamodel.CellResult;
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

  public double getMax(EAttribute attribute, PostCalculator postCalculator) {
    return calculateMax(attribute, postCalculator);
  }


  private double calculateMax(EAttribute attribute, PostCalculator postCalculator) {
    return group.getCellResults().stream()
        .mapToDouble(result -> postCalculator.calc(getValue(result, attribute), result)).max()
        .getAsDouble();
  }

  private double getValue(CellResult result, EAttribute attribute) {
    if (DatamodelPackage.Literals.CELL_RESULT.getEStructuralFeatures().contains(attribute)) {
      return (double) result.eGet(attribute);
    } else if (DatamodelPackage.Literals.CELL_MEASUREMENT_DATA_SET.getEStructuralFeatures()
        .contains(attribute)) {
      return (double) result.getLightMeasurementDataSet().eGet(attribute);
    } else {
      throw new IllegalArgumentException("EAttribute not found...");
    }
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

