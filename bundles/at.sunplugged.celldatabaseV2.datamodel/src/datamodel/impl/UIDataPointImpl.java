/**
 */
package datamodel.impl;

import datamodel.DatamodelPackage;
import datamodel.UIDataPoint;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UI Data Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link datamodel.impl.UIDataPointImpl#getVoltage <em>Voltage</em>}</li>
 *   <li>{@link datamodel.impl.UIDataPointImpl#getCurrent <em>Current</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UIDataPointImpl extends MinimalEObjectImpl.Container implements UIDataPoint {
  /**
   * The default value of the '{@link #getVoltage() <em>Voltage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVoltage()
   * @generated
   * @ordered
   */
  protected static final double VOLTAGE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getVoltage() <em>Voltage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVoltage()
   * @generated
   * @ordered
   */
  protected double voltage = VOLTAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getCurrent() <em>Current</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrent()
   * @generated
   * @ordered
   */
  protected static final double CURRENT_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getCurrent() <em>Current</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrent()
   * @generated
   * @ordered
   */
  protected double current = CURRENT_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected UIDataPointImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return DatamodelPackage.Literals.UI_DATA_POINT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public double getVoltage() {
    return voltage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVoltage(double newVoltage) {
    double oldVoltage = voltage;
    voltage = newVoltage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.UI_DATA_POINT__VOLTAGE, oldVoltage, voltage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public double getCurrent() {
    return current;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCurrent(double newCurrent) {
    double oldCurrent = current;
    current = newCurrent;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.UI_DATA_POINT__CURRENT, oldCurrent, current));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case DatamodelPackage.UI_DATA_POINT__VOLTAGE:
        return getVoltage();
      case DatamodelPackage.UI_DATA_POINT__CURRENT:
        return getCurrent();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case DatamodelPackage.UI_DATA_POINT__VOLTAGE:
        setVoltage((Double)newValue);
        return;
      case DatamodelPackage.UI_DATA_POINT__CURRENT:
        setCurrent((Double)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case DatamodelPackage.UI_DATA_POINT__VOLTAGE:
        setVoltage(VOLTAGE_EDEFAULT);
        return;
      case DatamodelPackage.UI_DATA_POINT__CURRENT:
        setCurrent(CURRENT_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case DatamodelPackage.UI_DATA_POINT__VOLTAGE:
        return voltage != VOLTAGE_EDEFAULT;
      case DatamodelPackage.UI_DATA_POINT__CURRENT:
        return current != CURRENT_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (Voltage: ");
    result.append(voltage);
    result.append(", Current: ");
    result.append(current);
    result.append(')');
    return result.toString();
  }

} //UIDataPointImpl
