/**
 */
package datamodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see datamodel.DatamodelFactory
 * @model kind="package"
 * @generated
 */
public interface DatamodelPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "datamodel";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://at/sunplugged/cdv/datamodel";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "at.sunplugged.cdv.datamodel";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  DatamodelPackage eINSTANCE = datamodel.impl.DatamodelPackageImpl.init();

  /**
   * The meta object id for the '{@link datamodel.impl.DatabaseImpl <em>Database</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see datamodel.impl.DatabaseImpl
   * @see datamodel.impl.DatamodelPackageImpl#getDatabase()
   * @generated
   */
  int DATABASE = 0;

  /**
   * The feature id for the '<em><b>Cell Groups</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DATABASE__CELL_GROUPS = 0;

  /**
   * The number of structural features of the '<em>Database</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DATABASE_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Database</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DATABASE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link datamodel.impl.CellResultImpl <em>Cell Result</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see datamodel.impl.CellResultImpl
   * @see datamodel.impl.DatamodelPackageImpl#getCellResult()
   * @generated
   */
  int CELL_RESULT = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__NAME = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Data Evaluated</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__DATA_EVALUATED = 2;

  /**
   * The feature id for the '<em><b>Open Circuit Voltage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__OPEN_CIRCUIT_VOLTAGE = 3;

  /**
   * The feature id for the '<em><b>Short Circuit Current</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__SHORT_CIRCUIT_CURRENT = 4;

  /**
   * The feature id for the '<em><b>Parallel Resistance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__PARALLEL_RESISTANCE = 5;

  /**
   * The feature id for the '<em><b>Dark Parallel Resistance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__DARK_PARALLEL_RESISTANCE = 6;

  /**
   * The feature id for the '<em><b>Series Resistance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__SERIES_RESISTANCE = 7;

  /**
   * The feature id for the '<em><b>Dark Series Resistance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__DARK_SERIES_RESISTANCE = 8;

  /**
   * The feature id for the '<em><b>Maximum Power Voltage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__MAXIMUM_POWER_VOLTAGE = 9;

  /**
   * The feature id for the '<em><b>Maximum Power Current</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__MAXIMUM_POWER_CURRENT = 10;

  /**
   * The feature id for the '<em><b>Efficiency</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__EFFICIENCY = 11;

  /**
   * The feature id for the '<em><b>Fill Factor</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__FILL_FACTOR = 12;

  /**
   * The feature id for the '<em><b>Light Measurement Data Set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET = 13;

  /**
   * The feature id for the '<em><b>Dark Measuremenet Data Set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__DARK_MEASUREMENET_DATA_SET = 14;

  /**
   * The feature id for the '<em><b>Maximum Power</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT__MAXIMUM_POWER = 15;

  /**
   * The number of structural features of the '<em>Cell Result</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT_FEATURE_COUNT = 16;

  /**
   * The number of operations of the '<em>Cell Result</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_RESULT_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link datamodel.impl.CellGroupImpl <em>Cell Group</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see datamodel.impl.CellGroupImpl
   * @see datamodel.impl.DatamodelPackageImpl#getCellGroup()
   * @generated
   */
  int CELL_GROUP = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_GROUP__NAME = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_GROUP__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Cell Results</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_GROUP__CELL_RESULTS = 2;

  /**
   * The number of structural features of the '<em>Cell Group</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_GROUP_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Cell Group</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_GROUP_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link datamodel.impl.CellMeasurementDataSetImpl <em>Cell Measurement Data Set</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see datamodel.impl.CellMeasurementDataSetImpl
   * @see datamodel.impl.DatamodelPackageImpl#getCellMeasurementDataSet()
   * @generated
   */
  int CELL_MEASUREMENT_DATA_SET = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_MEASUREMENT_DATA_SET__NAME = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_MEASUREMENT_DATA_SET__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Date Measured</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_MEASUREMENT_DATA_SET__DATE_MEASURED = 2;

  /**
   * The feature id for the '<em><b>Area</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_MEASUREMENT_DATA_SET__AREA = 3;

  /**
   * The feature id for the '<em><b>Power Input</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_MEASUREMENT_DATA_SET__POWER_INPUT = 4;

  /**
   * The feature id for the '<em><b>Data</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_MEASUREMENT_DATA_SET__DATA = 5;

  /**
   * The number of structural features of the '<em>Cell Measurement Data Set</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_MEASUREMENT_DATA_SET_FEATURE_COUNT = 6;

  /**
   * The number of operations of the '<em>Cell Measurement Data Set</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CELL_MEASUREMENT_DATA_SET_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link datamodel.impl.UIDataPointImpl <em>UI Data Point</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see datamodel.impl.UIDataPointImpl
   * @see datamodel.impl.DatamodelPackageImpl#getUIDataPoint()
   * @generated
   */
  int UI_DATA_POINT = 4;

  /**
   * The feature id for the '<em><b>Voltage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UI_DATA_POINT__VOLTAGE = 0;

  /**
   * The feature id for the '<em><b>Current</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UI_DATA_POINT__CURRENT = 1;

  /**
   * The number of structural features of the '<em>UI Data Point</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UI_DATA_POINT_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>UI Data Point</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UI_DATA_POINT_OPERATION_COUNT = 0;


  /**
   * Returns the meta object for class '{@link datamodel.Database <em>Database</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Database</em>'.
   * @see datamodel.Database
   * @generated
   */
  EClass getDatabase();

  /**
   * Returns the meta object for the containment reference list '{@link datamodel.Database#getCellGroups <em>Cell Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Cell Groups</em>'.
   * @see datamodel.Database#getCellGroups()
   * @see #getDatabase()
   * @generated
   */
  EReference getDatabase_CellGroups();

  /**
   * Returns the meta object for class '{@link datamodel.CellResult <em>Cell Result</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Cell Result</em>'.
   * @see datamodel.CellResult
   * @generated
   */
  EClass getCellResult();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see datamodel.CellResult#getName()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_Name();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see datamodel.CellResult#getDescription()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_Description();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getDataEvaluated <em>Data Evaluated</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Data Evaluated</em>'.
   * @see datamodel.CellResult#getDataEvaluated()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_DataEvaluated();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getOpenCircuitVoltage <em>Open Circuit Voltage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Open Circuit Voltage</em>'.
   * @see datamodel.CellResult#getOpenCircuitVoltage()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_OpenCircuitVoltage();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getShortCircuitCurrent <em>Short Circuit Current</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Short Circuit Current</em>'.
   * @see datamodel.CellResult#getShortCircuitCurrent()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_ShortCircuitCurrent();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getParallelResistance <em>Parallel Resistance</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Parallel Resistance</em>'.
   * @see datamodel.CellResult#getParallelResistance()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_ParallelResistance();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getDarkParallelResistance <em>Dark Parallel Resistance</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Dark Parallel Resistance</em>'.
   * @see datamodel.CellResult#getDarkParallelResistance()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_DarkParallelResistance();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getSeriesResistance <em>Series Resistance</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Series Resistance</em>'.
   * @see datamodel.CellResult#getSeriesResistance()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_SeriesResistance();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getDarkSeriesResistance <em>Dark Series Resistance</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Dark Series Resistance</em>'.
   * @see datamodel.CellResult#getDarkSeriesResistance()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_DarkSeriesResistance();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getMaximumPowerVoltage <em>Maximum Power Voltage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Maximum Power Voltage</em>'.
   * @see datamodel.CellResult#getMaximumPowerVoltage()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_MaximumPowerVoltage();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getMaximumPowerCurrent <em>Maximum Power Current</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Maximum Power Current</em>'.
   * @see datamodel.CellResult#getMaximumPowerCurrent()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_MaximumPowerCurrent();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getEfficiency <em>Efficiency</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Efficiency</em>'.
   * @see datamodel.CellResult#getEfficiency()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_Efficiency();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getFillFactor <em>Fill Factor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fill Factor</em>'.
   * @see datamodel.CellResult#getFillFactor()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_FillFactor();

  /**
   * Returns the meta object for the containment reference '{@link datamodel.CellResult#getLightMeasurementDataSet <em>Light Measurement Data Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Light Measurement Data Set</em>'.
   * @see datamodel.CellResult#getLightMeasurementDataSet()
   * @see #getCellResult()
   * @generated
   */
  EReference getCellResult_LightMeasurementDataSet();

  /**
   * Returns the meta object for the containment reference '{@link datamodel.CellResult#getDarkMeasuremenetDataSet <em>Dark Measuremenet Data Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Dark Measuremenet Data Set</em>'.
   * @see datamodel.CellResult#getDarkMeasuremenetDataSet()
   * @see #getCellResult()
   * @generated
   */
  EReference getCellResult_DarkMeasuremenetDataSet();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellResult#getMaximumPower <em>Maximum Power</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Maximum Power</em>'.
   * @see datamodel.CellResult#getMaximumPower()
   * @see #getCellResult()
   * @generated
   */
  EAttribute getCellResult_MaximumPower();

  /**
   * Returns the meta object for class '{@link datamodel.CellGroup <em>Cell Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Cell Group</em>'.
   * @see datamodel.CellGroup
   * @generated
   */
  EClass getCellGroup();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellGroup#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see datamodel.CellGroup#getName()
   * @see #getCellGroup()
   * @generated
   */
  EAttribute getCellGroup_Name();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellGroup#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see datamodel.CellGroup#getDescription()
   * @see #getCellGroup()
   * @generated
   */
  EAttribute getCellGroup_Description();

  /**
   * Returns the meta object for the containment reference list '{@link datamodel.CellGroup#getCellResults <em>Cell Results</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Cell Results</em>'.
   * @see datamodel.CellGroup#getCellResults()
   * @see #getCellGroup()
   * @generated
   */
  EReference getCellGroup_CellResults();

  /**
   * Returns the meta object for class '{@link datamodel.CellMeasurementDataSet <em>Cell Measurement Data Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Cell Measurement Data Set</em>'.
   * @see datamodel.CellMeasurementDataSet
   * @generated
   */
  EClass getCellMeasurementDataSet();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellMeasurementDataSet#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see datamodel.CellMeasurementDataSet#getName()
   * @see #getCellMeasurementDataSet()
   * @generated
   */
  EAttribute getCellMeasurementDataSet_Name();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellMeasurementDataSet#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see datamodel.CellMeasurementDataSet#getDescription()
   * @see #getCellMeasurementDataSet()
   * @generated
   */
  EAttribute getCellMeasurementDataSet_Description();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellMeasurementDataSet#getDateMeasured <em>Date Measured</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date Measured</em>'.
   * @see datamodel.CellMeasurementDataSet#getDateMeasured()
   * @see #getCellMeasurementDataSet()
   * @generated
   */
  EAttribute getCellMeasurementDataSet_DateMeasured();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellMeasurementDataSet#getArea <em>Area</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Area</em>'.
   * @see datamodel.CellMeasurementDataSet#getArea()
   * @see #getCellMeasurementDataSet()
   * @generated
   */
  EAttribute getCellMeasurementDataSet_Area();

  /**
   * Returns the meta object for the attribute '{@link datamodel.CellMeasurementDataSet#getPowerInput <em>Power Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Power Input</em>'.
   * @see datamodel.CellMeasurementDataSet#getPowerInput()
   * @see #getCellMeasurementDataSet()
   * @generated
   */
  EAttribute getCellMeasurementDataSet_PowerInput();

  /**
   * Returns the meta object for the containment reference list '{@link datamodel.CellMeasurementDataSet#getData <em>Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Data</em>'.
   * @see datamodel.CellMeasurementDataSet#getData()
   * @see #getCellMeasurementDataSet()
   * @generated
   */
  EReference getCellMeasurementDataSet_Data();

  /**
   * Returns the meta object for class '{@link datamodel.UIDataPoint <em>UI Data Point</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>UI Data Point</em>'.
   * @see datamodel.UIDataPoint
   * @generated
   */
  EClass getUIDataPoint();

  /**
   * Returns the meta object for the attribute '{@link datamodel.UIDataPoint#getVoltage <em>Voltage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Voltage</em>'.
   * @see datamodel.UIDataPoint#getVoltage()
   * @see #getUIDataPoint()
   * @generated
   */
  EAttribute getUIDataPoint_Voltage();

  /**
   * Returns the meta object for the attribute '{@link datamodel.UIDataPoint#getCurrent <em>Current</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Current</em>'.
   * @see datamodel.UIDataPoint#getCurrent()
   * @see #getUIDataPoint()
   * @generated
   */
  EAttribute getUIDataPoint_Current();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  DatamodelFactory getDatamodelFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link datamodel.impl.DatabaseImpl <em>Database</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see datamodel.impl.DatabaseImpl
     * @see datamodel.impl.DatamodelPackageImpl#getDatabase()
     * @generated
     */
    EClass DATABASE = eINSTANCE.getDatabase();

    /**
     * The meta object literal for the '<em><b>Cell Groups</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DATABASE__CELL_GROUPS = eINSTANCE.getDatabase_CellGroups();

    /**
     * The meta object literal for the '{@link datamodel.impl.CellResultImpl <em>Cell Result</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see datamodel.impl.CellResultImpl
     * @see datamodel.impl.DatamodelPackageImpl#getCellResult()
     * @generated
     */
    EClass CELL_RESULT = eINSTANCE.getCellResult();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__NAME = eINSTANCE.getCellResult_Name();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__DESCRIPTION = eINSTANCE.getCellResult_Description();

    /**
     * The meta object literal for the '<em><b>Data Evaluated</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__DATA_EVALUATED = eINSTANCE.getCellResult_DataEvaluated();

    /**
     * The meta object literal for the '<em><b>Open Circuit Voltage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__OPEN_CIRCUIT_VOLTAGE = eINSTANCE.getCellResult_OpenCircuitVoltage();

    /**
     * The meta object literal for the '<em><b>Short Circuit Current</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__SHORT_CIRCUIT_CURRENT = eINSTANCE.getCellResult_ShortCircuitCurrent();

    /**
     * The meta object literal for the '<em><b>Parallel Resistance</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__PARALLEL_RESISTANCE = eINSTANCE.getCellResult_ParallelResistance();

    /**
     * The meta object literal for the '<em><b>Dark Parallel Resistance</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__DARK_PARALLEL_RESISTANCE = eINSTANCE.getCellResult_DarkParallelResistance();

    /**
     * The meta object literal for the '<em><b>Series Resistance</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__SERIES_RESISTANCE = eINSTANCE.getCellResult_SeriesResistance();

    /**
     * The meta object literal for the '<em><b>Dark Series Resistance</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__DARK_SERIES_RESISTANCE = eINSTANCE.getCellResult_DarkSeriesResistance();

    /**
     * The meta object literal for the '<em><b>Maximum Power Voltage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__MAXIMUM_POWER_VOLTAGE = eINSTANCE.getCellResult_MaximumPowerVoltage();

    /**
     * The meta object literal for the '<em><b>Maximum Power Current</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__MAXIMUM_POWER_CURRENT = eINSTANCE.getCellResult_MaximumPowerCurrent();

    /**
     * The meta object literal for the '<em><b>Efficiency</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__EFFICIENCY = eINSTANCE.getCellResult_Efficiency();

    /**
     * The meta object literal for the '<em><b>Fill Factor</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__FILL_FACTOR = eINSTANCE.getCellResult_FillFactor();

    /**
     * The meta object literal for the '<em><b>Light Measurement Data Set</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET = eINSTANCE.getCellResult_LightMeasurementDataSet();

    /**
     * The meta object literal for the '<em><b>Dark Measuremenet Data Set</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CELL_RESULT__DARK_MEASUREMENET_DATA_SET = eINSTANCE.getCellResult_DarkMeasuremenetDataSet();

    /**
     * The meta object literal for the '<em><b>Maximum Power</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_RESULT__MAXIMUM_POWER = eINSTANCE.getCellResult_MaximumPower();

    /**
     * The meta object literal for the '{@link datamodel.impl.CellGroupImpl <em>Cell Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see datamodel.impl.CellGroupImpl
     * @see datamodel.impl.DatamodelPackageImpl#getCellGroup()
     * @generated
     */
    EClass CELL_GROUP = eINSTANCE.getCellGroup();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_GROUP__NAME = eINSTANCE.getCellGroup_Name();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_GROUP__DESCRIPTION = eINSTANCE.getCellGroup_Description();

    /**
     * The meta object literal for the '<em><b>Cell Results</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CELL_GROUP__CELL_RESULTS = eINSTANCE.getCellGroup_CellResults();

    /**
     * The meta object literal for the '{@link datamodel.impl.CellMeasurementDataSetImpl <em>Cell Measurement Data Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see datamodel.impl.CellMeasurementDataSetImpl
     * @see datamodel.impl.DatamodelPackageImpl#getCellMeasurementDataSet()
     * @generated
     */
    EClass CELL_MEASUREMENT_DATA_SET = eINSTANCE.getCellMeasurementDataSet();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_MEASUREMENT_DATA_SET__NAME = eINSTANCE.getCellMeasurementDataSet_Name();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_MEASUREMENT_DATA_SET__DESCRIPTION = eINSTANCE.getCellMeasurementDataSet_Description();

    /**
     * The meta object literal for the '<em><b>Date Measured</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_MEASUREMENT_DATA_SET__DATE_MEASURED = eINSTANCE.getCellMeasurementDataSet_DateMeasured();

    /**
     * The meta object literal for the '<em><b>Area</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_MEASUREMENT_DATA_SET__AREA = eINSTANCE.getCellMeasurementDataSet_Area();

    /**
     * The meta object literal for the '<em><b>Power Input</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CELL_MEASUREMENT_DATA_SET__POWER_INPUT = eINSTANCE.getCellMeasurementDataSet_PowerInput();

    /**
     * The meta object literal for the '<em><b>Data</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CELL_MEASUREMENT_DATA_SET__DATA = eINSTANCE.getCellMeasurementDataSet_Data();

    /**
     * The meta object literal for the '{@link datamodel.impl.UIDataPointImpl <em>UI Data Point</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see datamodel.impl.UIDataPointImpl
     * @see datamodel.impl.DatamodelPackageImpl#getUIDataPoint()
     * @generated
     */
    EClass UI_DATA_POINT = eINSTANCE.getUIDataPoint();

    /**
     * The meta object literal for the '<em><b>Voltage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UI_DATA_POINT__VOLTAGE = eINSTANCE.getUIDataPoint_Voltage();

    /**
     * The meta object literal for the '<em><b>Current</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UI_DATA_POINT__CURRENT = eINSTANCE.getUIDataPoint_Current();

  }

} //DatamodelPackage
