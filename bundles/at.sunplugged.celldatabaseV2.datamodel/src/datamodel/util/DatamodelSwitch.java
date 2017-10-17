/**
 */
package datamodel.util;

import datamodel.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see datamodel.DatamodelPackage
 * @generated
 */
public class DatamodelSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static DatamodelPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DatamodelSwitch() {
    if (modelPackage == null) {
      modelPackage = DatamodelPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
      case DatamodelPackage.DATABASE: {
        Database database = (Database)theEObject;
        T result = caseDatabase(database);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DatamodelPackage.CELL_RESULT: {
        CellResult cellResult = (CellResult)theEObject;
        T result = caseCellResult(cellResult);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DatamodelPackage.CELL_GROUP: {
        CellGroup cellGroup = (CellGroup)theEObject;
        T result = caseCellGroup(cellGroup);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DatamodelPackage.CELL_MEASUREMENT_DATA_SET: {
        CellMeasurementDataSet cellMeasurementDataSet = (CellMeasurementDataSet)theEObject;
        T result = caseCellMeasurementDataSet(cellMeasurementDataSet);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DatamodelPackage.UI_DATA_POINT: {
        UIDataPoint uiDataPoint = (UIDataPoint)theEObject;
        T result = caseUIDataPoint(uiDataPoint);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Database</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Database</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDatabase(Database object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Cell Result</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Cell Result</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCellResult(CellResult object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Cell Group</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Cell Group</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCellGroup(CellGroup object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Cell Measurement Data Set</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Cell Measurement Data Set</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCellMeasurementDataSet(CellMeasurementDataSet object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>UI Data Point</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>UI Data Point</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUIDataPoint(UIDataPoint object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} //DatamodelSwitch
