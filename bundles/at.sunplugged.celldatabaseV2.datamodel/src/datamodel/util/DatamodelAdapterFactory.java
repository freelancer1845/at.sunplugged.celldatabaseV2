/**
 */
package datamodel.util;

import datamodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see datamodel.DatamodelPackage
 * @generated
 */
public class DatamodelAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static DatamodelPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DatamodelAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = DatamodelPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DatamodelSwitch<Adapter> modelSwitch =
    new DatamodelSwitch<Adapter>() {
      @Override
      public Adapter caseDatabase(Database object) {
        return createDatabaseAdapter();
      }
      @Override
      public Adapter caseCellResult(CellResult object) {
        return createCellResultAdapter();
      }
      @Override
      public Adapter caseCellGroup(CellGroup object) {
        return createCellGroupAdapter();
      }
      @Override
      public Adapter caseCellMeasurementDataSet(CellMeasurementDataSet object) {
        return createCellMeasurementDataSetAdapter();
      }
      @Override
      public Adapter caseUIDataPoint(UIDataPoint object) {
        return createUIDataPointAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object) {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link datamodel.Database <em>Database</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see datamodel.Database
   * @generated
   */
  public Adapter createDatabaseAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link datamodel.CellResult <em>Cell Result</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see datamodel.CellResult
   * @generated
   */
  public Adapter createCellResultAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link datamodel.CellGroup <em>Cell Group</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see datamodel.CellGroup
   * @generated
   */
  public Adapter createCellGroupAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link datamodel.CellMeasurementDataSet <em>Cell Measurement Data Set</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see datamodel.CellMeasurementDataSet
   * @generated
   */
  public Adapter createCellMeasurementDataSetAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link datamodel.UIDataPoint <em>UI Data Point</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see datamodel.UIDataPoint
   * @generated
   */
  public Adapter createUIDataPointAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //DatamodelAdapterFactory
