/**
 */
package datamodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see datamodel.DatamodelPackage
 * @generated
 */
public interface DatamodelFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  DatamodelFactory eINSTANCE = datamodel.impl.DatamodelFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Database</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Database</em>'.
   * @generated
   */
  Database createDatabase();

  /**
   * Returns a new object of class '<em>Cell Result</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Cell Result</em>'.
   * @generated
   */
  CellResult createCellResult();

  /**
   * Returns a new object of class '<em>Cell Group</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Cell Group</em>'.
   * @generated
   */
  CellGroup createCellGroup();

  /**
   * Returns a new object of class '<em>Cell Measurement Data Set</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Cell Measurement Data Set</em>'.
   * @generated
   */
  CellMeasurementDataSet createCellMeasurementDataSet();

  /**
   * Returns a new object of class '<em>UI Data Point</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>UI Data Point</em>'.
   * @generated
   */
  UIDataPoint createUIDataPoint();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  DatamodelPackage getDatamodelPackage();

} //DatamodelFactory
