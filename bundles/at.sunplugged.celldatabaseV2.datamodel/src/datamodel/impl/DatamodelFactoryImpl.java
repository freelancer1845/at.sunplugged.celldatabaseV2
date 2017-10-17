/**
 */
package datamodel.impl;

import datamodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DatamodelFactoryImpl extends EFactoryImpl implements DatamodelFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static DatamodelFactory init() {
    try {
      DatamodelFactory theDatamodelFactory = (DatamodelFactory)EPackage.Registry.INSTANCE.getEFactory(DatamodelPackage.eNS_URI);
      if (theDatamodelFactory != null) {
        return theDatamodelFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new DatamodelFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DatamodelFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case DatamodelPackage.DATABASE: return createDatabase();
      case DatamodelPackage.CELL_RESULT: return createCellResult();
      case DatamodelPackage.CELL_GROUP: return createCellGroup();
      case DatamodelPackage.CELL_MEASUREMENT_DATA_SET: return createCellMeasurementDataSet();
      case DatamodelPackage.UI_DATA_POINT: return createUIDataPoint();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Database createDatabase() {
    DatabaseImpl database = new DatabaseImpl();
    return database;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CellResult createCellResult() {
    CellResultImpl cellResult = new CellResultImpl();
    return cellResult;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CellGroup createCellGroup() {
    CellGroupImpl cellGroup = new CellGroupImpl();
    return cellGroup;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CellMeasurementDataSet createCellMeasurementDataSet() {
    CellMeasurementDataSetImpl cellMeasurementDataSet = new CellMeasurementDataSetImpl();
    return cellMeasurementDataSet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UIDataPoint createUIDataPoint() {
    UIDataPointImpl uiDataPoint = new UIDataPointImpl();
    return uiDataPoint;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DatamodelPackage getDatamodelPackage() {
    return (DatamodelPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static DatamodelPackage getPackage() {
    return DatamodelPackage.eINSTANCE;
  }

} //DatamodelFactoryImpl
