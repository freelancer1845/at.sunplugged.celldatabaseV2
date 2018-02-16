/**
 */
package datamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>UI Data Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link datamodel.UIDataPoint#getVoltage <em>Voltage</em>}</li>
 *   <li>{@link datamodel.UIDataPoint#getCurrent <em>Current</em>}</li>
 * </ul>
 *
 * @see datamodel.DatamodelPackage#getUIDataPoint()
 * @model
 * @generated
 */
public interface UIDataPoint extends EObject {
  /**
   * Returns the value of the '<em><b>Voltage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Voltage</em>' attribute isn't clear, there really should be more of
   * a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Voltage</em>' attribute.
   * @see #setVoltage(double)
   * @see datamodel.DatamodelPackage#getUIDataPoint_Voltage()
   * @model
   * @generated
   */
  double getVoltage();

  /**
   * Sets the value of the '{@link datamodel.UIDataPoint#getVoltage <em>Voltage</em>}' attribute.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @param value the new value of the '<em>Voltage</em>' attribute.
   * @see #getVoltage()
   * @generated
   */
  void setVoltage(double value);

  /**
   * Returns the value of the '<em><b>Current</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Current</em>' attribute isn't clear, there really should be more of
   * a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Current</em>' attribute.
   * @see #setCurrent(double)
   * @see datamodel.DatamodelPackage#getUIDataPoint_Current()
   * @model
   * @generated
   */
  double getCurrent();

  /**
   * Sets the value of the '{@link datamodel.UIDataPoint#getCurrent <em>Current</em>}' attribute.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @param value the new value of the '<em>Current</em>' attribute.
   * @see #getCurrent()
   * @generated
   */
  void setCurrent(double value);

} // UIDataPoint
