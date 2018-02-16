/**
 */
package datamodel;

import java.util.Date;

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cell Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link datamodel.CellResult#getName <em>Name</em>}</li>
 *   <li>{@link datamodel.CellResult#getDescription <em>Description</em>}</li>
 *   <li>{@link datamodel.CellResult#getDataEvaluated <em>Data Evaluated</em>}</li>
 *   <li>{@link datamodel.CellResult#getOpenCircuitVoltage <em>Open Circuit Voltage</em>}</li>
 *   <li>{@link datamodel.CellResult#getShortCircuitCurrent <em>Short Circuit Current</em>}</li>
 *   <li>{@link datamodel.CellResult#getParallelResistance <em>Parallel Resistance</em>}</li>
 *   <li>{@link datamodel.CellResult#getDarkParallelResistance <em>Dark Parallel Resistance</em>}</li>
 *   <li>{@link datamodel.CellResult#getSeriesResistance <em>Series Resistance</em>}</li>
 *   <li>{@link datamodel.CellResult#getDarkSeriesResistance <em>Dark Series Resistance</em>}</li>
 *   <li>{@link datamodel.CellResult#getMaximumPowerVoltage <em>Maximum Power Voltage</em>}</li>
 *   <li>{@link datamodel.CellResult#getMaximumPowerCurrent <em>Maximum Power Current</em>}</li>
 *   <li>{@link datamodel.CellResult#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link datamodel.CellResult#getFillFactor <em>Fill Factor</em>}</li>
 *   <li>{@link datamodel.CellResult#getLightMeasurementDataSet <em>Light Measurement Data Set</em>}</li>
 *   <li>{@link datamodel.CellResult#getDarkMeasuremenetDataSet <em>Dark Measuremenet Data Set</em>}</li>
 *   <li>{@link datamodel.CellResult#getMaximumPower <em>Maximum Power</em>}</li>
 *   <li>{@link datamodel.CellResult#getLightUICoefficients <em>Light UI Coefficients</em>}</li>
 *   <li>{@link datamodel.CellResult#getDarkUICoefficients <em>Dark UI Coefficients</em>}</li>
 * </ul>
 *
 * @see datamodel.DatamodelPackage#getCellResult()
 * @model
 * @generated
 */
public interface CellResult extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * The default value is <code>"default..."</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see datamodel.DatamodelPackage#getCellResult_Name()
   * @model default="default..."
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #setDescription(String)
   * @see datamodel.DatamodelPackage#getCellResult_Description()
   * @model
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Data Evaluated</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Data Evaluated</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Data Evaluated</em>' attribute.
   * @see #setDataEvaluated(Date)
   * @see datamodel.DatamodelPackage#getCellResult_DataEvaluated()
   * @model
   * @generated
   */
  Date getDataEvaluated();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getDataEvaluated <em>Data Evaluated</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Data Evaluated</em>' attribute.
   * @see #getDataEvaluated()
   * @generated
   */
  void setDataEvaluated(Date value);

  /**
   * Returns the value of the '<em><b>Open Circuit Voltage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Open Circuit Voltage</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Open Circuit Voltage</em>' attribute.
   * @see #setOpenCircuitVoltage(double)
   * @see datamodel.DatamodelPackage#getCellResult_OpenCircuitVoltage()
   * @model
   * @generated
   */
  double getOpenCircuitVoltage();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getOpenCircuitVoltage <em>Open Circuit Voltage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Open Circuit Voltage</em>' attribute.
   * @see #getOpenCircuitVoltage()
   * @generated
   */
  void setOpenCircuitVoltage(double value);

  /**
   * Returns the value of the '<em><b>Short Circuit Current</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Short Circuit Current</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Short Circuit Current</em>' attribute.
   * @see #setShortCircuitCurrent(double)
   * @see datamodel.DatamodelPackage#getCellResult_ShortCircuitCurrent()
   * @model
   * @generated
   */
  double getShortCircuitCurrent();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getShortCircuitCurrent <em>Short Circuit Current</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Short Circuit Current</em>' attribute.
   * @see #getShortCircuitCurrent()
   * @generated
   */
  void setShortCircuitCurrent(double value);

  /**
   * Returns the value of the '<em><b>Parallel Resistance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parallel Resistance</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parallel Resistance</em>' attribute.
   * @see #setParallelResistance(double)
   * @see datamodel.DatamodelPackage#getCellResult_ParallelResistance()
   * @model
   * @generated
   */
  double getParallelResistance();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getParallelResistance <em>Parallel Resistance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parallel Resistance</em>' attribute.
   * @see #getParallelResistance()
   * @generated
   */
  void setParallelResistance(double value);

  /**
   * Returns the value of the '<em><b>Dark Parallel Resistance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Dark Parallel Resistance</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dark Parallel Resistance</em>' attribute.
   * @see #setDarkParallelResistance(double)
   * @see datamodel.DatamodelPackage#getCellResult_DarkParallelResistance()
   * @model
   * @generated
   */
  double getDarkParallelResistance();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getDarkParallelResistance <em>Dark Parallel Resistance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Dark Parallel Resistance</em>' attribute.
   * @see #getDarkParallelResistance()
   * @generated
   */
  void setDarkParallelResistance(double value);

  /**
   * Returns the value of the '<em><b>Series Resistance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Series Resistance</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Series Resistance</em>' attribute.
   * @see #setSeriesResistance(double)
   * @see datamodel.DatamodelPackage#getCellResult_SeriesResistance()
   * @model
   * @generated
   */
  double getSeriesResistance();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getSeriesResistance <em>Series Resistance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Series Resistance</em>' attribute.
   * @see #getSeriesResistance()
   * @generated
   */
  void setSeriesResistance(double value);

  /**
   * Returns the value of the '<em><b>Dark Series Resistance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Dark Series Resistance</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dark Series Resistance</em>' attribute.
   * @see #setDarkSeriesResistance(double)
   * @see datamodel.DatamodelPackage#getCellResult_DarkSeriesResistance()
   * @model
   * @generated
   */
  double getDarkSeriesResistance();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getDarkSeriesResistance <em>Dark Series Resistance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Dark Series Resistance</em>' attribute.
   * @see #getDarkSeriesResistance()
   * @generated
   */
  void setDarkSeriesResistance(double value);

  /**
   * Returns the value of the '<em><b>Maximum Power Voltage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Maximum Power Voltage</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Maximum Power Voltage</em>' attribute.
   * @see #setMaximumPowerVoltage(double)
   * @see datamodel.DatamodelPackage#getCellResult_MaximumPowerVoltage()
   * @model
   * @generated
   */
  double getMaximumPowerVoltage();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getMaximumPowerVoltage <em>Maximum Power Voltage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Maximum Power Voltage</em>' attribute.
   * @see #getMaximumPowerVoltage()
   * @generated
   */
  void setMaximumPowerVoltage(double value);

  /**
   * Returns the value of the '<em><b>Maximum Power Current</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Maximum Power Current</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Maximum Power Current</em>' attribute.
   * @see #setMaximumPowerCurrent(double)
   * @see datamodel.DatamodelPackage#getCellResult_MaximumPowerCurrent()
   * @model
   * @generated
   */
  double getMaximumPowerCurrent();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getMaximumPowerCurrent <em>Maximum Power Current</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Maximum Power Current</em>' attribute.
   * @see #getMaximumPowerCurrent()
   * @generated
   */
  void setMaximumPowerCurrent(double value);

  /**
   * Returns the value of the '<em><b>Efficiency</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Efficiency</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Efficiency</em>' attribute.
   * @see #setEfficiency(double)
   * @see datamodel.DatamodelPackage#getCellResult_Efficiency()
   * @model
   * @generated
   */
  double getEfficiency();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getEfficiency <em>Efficiency</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Efficiency</em>' attribute.
   * @see #getEfficiency()
   * @generated
   */
  void setEfficiency(double value);

  /**
   * Returns the value of the '<em><b>Fill Factor</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fill Factor</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fill Factor</em>' attribute.
   * @see #setFillFactor(double)
   * @see datamodel.DatamodelPackage#getCellResult_FillFactor()
   * @model
   * @generated
   */
  double getFillFactor();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getFillFactor <em>Fill Factor</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fill Factor</em>' attribute.
   * @see #getFillFactor()
   * @generated
   */
  void setFillFactor(double value);

  /**
   * Returns the value of the '<em><b>Light Measurement Data Set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Light Measurement Data Set</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Light Measurement Data Set</em>' containment reference.
   * @see #setLightMeasurementDataSet(CellMeasurementDataSet)
   * @see datamodel.DatamodelPackage#getCellResult_LightMeasurementDataSet()
   * @model containment="true"
   * @generated
   */
  CellMeasurementDataSet getLightMeasurementDataSet();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getLightMeasurementDataSet <em>Light Measurement Data Set</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Light Measurement Data Set</em>' containment reference.
   * @see #getLightMeasurementDataSet()
   * @generated
   */
  void setLightMeasurementDataSet(CellMeasurementDataSet value);

  /**
   * Returns the value of the '<em><b>Dark Measuremenet Data Set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Dark Measuremenet Data Set</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dark Measuremenet Data Set</em>' containment reference.
   * @see #setDarkMeasuremenetDataSet(CellMeasurementDataSet)
   * @see datamodel.DatamodelPackage#getCellResult_DarkMeasuremenetDataSet()
   * @model containment="true"
   * @generated
   */
  CellMeasurementDataSet getDarkMeasuremenetDataSet();

  /**
   * Sets the value of the '{@link datamodel.CellResult#getDarkMeasuremenetDataSet <em>Dark Measuremenet Data Set</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Dark Measuremenet Data Set</em>' containment reference.
   * @see #getDarkMeasuremenetDataSet()
   * @generated
   */
  void setDarkMeasuremenetDataSet(CellMeasurementDataSet value);

  /**
   * Returns the value of the '<em><b>Maximum Power</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Maximum Power</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Maximum Power</em>' attribute.
   * @see datamodel.DatamodelPackage#getCellResult_MaximumPower()
   * @model transient="true" changeable="false" volatile="true" derived="true"
   * @generated
   */
  double getMaximumPower();

  /**
   * Returns the value of the '<em><b>Light UI Coefficients</b></em>' attribute list.
   * The list contents are of type {@link java.lang.Double}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Light UI Coefficients</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Light UI Coefficients</em>' attribute list.
   * @see datamodel.DatamodelPackage#getCellResult_LightUICoefficients()
   * @model
   * @generated
   */
  EList<Double> getLightUICoefficients();

  /**
   * Returns the value of the '<em><b>Dark UI Coefficients</b></em>' attribute list.
   * The list contents are of type {@link java.lang.Double}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Dark UI Coefficients</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dark UI Coefficients</em>' attribute list.
   * @see datamodel.DatamodelPackage#getCellResult_DarkUICoefficients()
   * @model
   * @generated
   */
  EList<Double> getDarkUICoefficients();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  boolean validateName(DiagnosticChain chain, Map<?, ?> context);

} // CellResult
