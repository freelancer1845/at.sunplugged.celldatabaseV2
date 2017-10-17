/**
 */
package datamodel;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cell Measurement Data Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link datamodel.CellMeasurementDataSet#getName <em>Name</em>}</li>
 *   <li>{@link datamodel.CellMeasurementDataSet#getDescription <em>Description</em>}</li>
 *   <li>{@link datamodel.CellMeasurementDataSet#getDateMeasured <em>Date Measured</em>}</li>
 *   <li>{@link datamodel.CellMeasurementDataSet#getArea <em>Area</em>}</li>
 *   <li>{@link datamodel.CellMeasurementDataSet#getPowerInput <em>Power Input</em>}</li>
 *   <li>{@link datamodel.CellMeasurementDataSet#getData <em>Data</em>}</li>
 * </ul>
 *
 * @see datamodel.DatamodelPackage#getCellMeasurementDataSet()
 * @model
 * @generated
 */
public interface CellMeasurementDataSet extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see datamodel.DatamodelPackage#getCellMeasurementDataSet_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link datamodel.CellMeasurementDataSet#getName <em>Name</em>}' attribute.
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
   * @see datamodel.DatamodelPackage#getCellMeasurementDataSet_Description()
   * @model
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link datamodel.CellMeasurementDataSet#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Date Measured</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Date Measured</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date Measured</em>' attribute.
   * @see #setDateMeasured(Date)
   * @see datamodel.DatamodelPackage#getCellMeasurementDataSet_DateMeasured()
   * @model
   * @generated
   */
  Date getDateMeasured();

  /**
   * Sets the value of the '{@link datamodel.CellMeasurementDataSet#getDateMeasured <em>Date Measured</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date Measured</em>' attribute.
   * @see #getDateMeasured()
   * @generated
   */
  void setDateMeasured(Date value);

  /**
   * Returns the value of the '<em><b>Area</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Area</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Area</em>' attribute.
   * @see #setArea(double)
   * @see datamodel.DatamodelPackage#getCellMeasurementDataSet_Area()
   * @model
   * @generated
   */
  double getArea();

  /**
   * Sets the value of the '{@link datamodel.CellMeasurementDataSet#getArea <em>Area</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Area</em>' attribute.
   * @see #getArea()
   * @generated
   */
  void setArea(double value);

  /**
   * Returns the value of the '<em><b>Power Input</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Power Input</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Power Input</em>' attribute.
   * @see #setPowerInput(double)
   * @see datamodel.DatamodelPackage#getCellMeasurementDataSet_PowerInput()
   * @model
   * @generated
   */
  double getPowerInput();

  /**
   * Sets the value of the '{@link datamodel.CellMeasurementDataSet#getPowerInput <em>Power Input</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Power Input</em>' attribute.
   * @see #getPowerInput()
   * @generated
   */
  void setPowerInput(double value);

  /**
   * Returns the value of the '<em><b>Data</b></em>' containment reference list.
   * The list contents are of type {@link datamodel.UIDataPoint}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Data</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Data</em>' containment reference list.
   * @see datamodel.DatamodelPackage#getCellMeasurementDataSet_Data()
   * @model containment="true"
   * @generated
   */
  EList<UIDataPoint> getData();

} // CellMeasurementDataSet
