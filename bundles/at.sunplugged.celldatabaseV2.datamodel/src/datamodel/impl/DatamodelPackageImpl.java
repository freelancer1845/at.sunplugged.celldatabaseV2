/**
 */
package datamodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.Database;
import datamodel.DatamodelFactory;
import datamodel.DatamodelPackage;
import datamodel.UIDataPoint;
import datamodel.util.DatamodelValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class DatamodelPackageImpl extends EPackageImpl implements DatamodelPackage {
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass databaseEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass cellResultEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass cellGroupEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass cellMeasurementDataSetEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass uiDataPointEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see datamodel.DatamodelPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private DatamodelPackageImpl() {
    super(eNS_URI, DatamodelFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link DatamodelPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static DatamodelPackage init() {
    if (isInited) return (DatamodelPackage)EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI);

    // Obtain or create and register package
    DatamodelPackageImpl theDatamodelPackage = (DatamodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DatamodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DatamodelPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theDatamodelPackage.createPackageContents();

    // Initialize created meta-data
    theDatamodelPackage.initializePackageContents();

    // Register package validator
    EValidator.Registry.INSTANCE.put
      (theDatamodelPackage, 
       new EValidator.Descriptor() {
         public EValidator getEValidator() {
           return DatamodelValidator.INSTANCE;
         }
       });

    // Mark meta-data to indicate it can't be changed
    theDatamodelPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(DatamodelPackage.eNS_URI, theDatamodelPackage);
    return theDatamodelPackage;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getDatabase() {
    return databaseEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getDatabase_CellGroups() {
    return (EReference)databaseEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getCellResult() {
    return cellResultEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_Name() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_Description() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_DataEvaluated() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_OpenCircuitVoltage() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_ShortCircuitCurrent() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_ParallelResistance() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_DarkParallelResistance() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_SeriesResistance() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_DarkSeriesResistance() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_MaximumPowerVoltage() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_MaximumPowerCurrent() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_Efficiency() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_FillFactor() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getCellResult_LightMeasurementDataSet() {
    return (EReference)cellResultEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getCellResult_DarkMeasuremenetDataSet() {
    return (EReference)cellResultEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellResult_MaximumPower() {
    return (EAttribute)cellResultEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getCellResult__ValidateName__DiagnosticChain_Map() {
    return cellResultEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getCellGroup() {
    return cellGroupEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellGroup_Name() {
    return (EAttribute)cellGroupEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellGroup_Description() {
    return (EAttribute)cellGroupEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getCellGroup_CellResults() {
    return (EReference)cellGroupEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EOperation getCellGroup__CellResultsNamesCorrect__DiagnosticChain_Map() {
    return cellGroupEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getCellMeasurementDataSet() {
    return cellMeasurementDataSetEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellMeasurementDataSet_Name() {
    return (EAttribute)cellMeasurementDataSetEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellMeasurementDataSet_Description() {
    return (EAttribute)cellMeasurementDataSetEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellMeasurementDataSet_DateMeasured() {
    return (EAttribute)cellMeasurementDataSetEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellMeasurementDataSet_Area() {
    return (EAttribute)cellMeasurementDataSetEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCellMeasurementDataSet_PowerInput() {
    return (EAttribute)cellMeasurementDataSetEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getCellMeasurementDataSet_Data() {
    return (EReference)cellMeasurementDataSetEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getUIDataPoint() {
    return uiDataPointEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getUIDataPoint_Voltage() {
    return (EAttribute)uiDataPointEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getUIDataPoint_Current() {
    return (EAttribute)uiDataPointEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public DatamodelFactory getDatamodelFactory() {
    return (DatamodelFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    databaseEClass = createEClass(DATABASE);
    createEReference(databaseEClass, DATABASE__CELL_GROUPS);

    cellResultEClass = createEClass(CELL_RESULT);
    createEAttribute(cellResultEClass, CELL_RESULT__NAME);
    createEAttribute(cellResultEClass, CELL_RESULT__DESCRIPTION);
    createEAttribute(cellResultEClass, CELL_RESULT__DATA_EVALUATED);
    createEAttribute(cellResultEClass, CELL_RESULT__OPEN_CIRCUIT_VOLTAGE);
    createEAttribute(cellResultEClass, CELL_RESULT__SHORT_CIRCUIT_CURRENT);
    createEAttribute(cellResultEClass, CELL_RESULT__PARALLEL_RESISTANCE);
    createEAttribute(cellResultEClass, CELL_RESULT__DARK_PARALLEL_RESISTANCE);
    createEAttribute(cellResultEClass, CELL_RESULT__SERIES_RESISTANCE);
    createEAttribute(cellResultEClass, CELL_RESULT__DARK_SERIES_RESISTANCE);
    createEAttribute(cellResultEClass, CELL_RESULT__MAXIMUM_POWER_VOLTAGE);
    createEAttribute(cellResultEClass, CELL_RESULT__MAXIMUM_POWER_CURRENT);
    createEAttribute(cellResultEClass, CELL_RESULT__EFFICIENCY);
    createEAttribute(cellResultEClass, CELL_RESULT__FILL_FACTOR);
    createEReference(cellResultEClass, CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET);
    createEReference(cellResultEClass, CELL_RESULT__DARK_MEASUREMENET_DATA_SET);
    createEAttribute(cellResultEClass, CELL_RESULT__MAXIMUM_POWER);
    createEOperation(cellResultEClass, CELL_RESULT___VALIDATE_NAME__DIAGNOSTICCHAIN_MAP);

    cellGroupEClass = createEClass(CELL_GROUP);
    createEAttribute(cellGroupEClass, CELL_GROUP__NAME);
    createEAttribute(cellGroupEClass, CELL_GROUP__DESCRIPTION);
    createEReference(cellGroupEClass, CELL_GROUP__CELL_RESULTS);
    createEOperation(cellGroupEClass, CELL_GROUP___CELL_RESULTS_NAMES_CORRECT__DIAGNOSTICCHAIN_MAP);

    cellMeasurementDataSetEClass = createEClass(CELL_MEASUREMENT_DATA_SET);
    createEAttribute(cellMeasurementDataSetEClass, CELL_MEASUREMENT_DATA_SET__NAME);
    createEAttribute(cellMeasurementDataSetEClass, CELL_MEASUREMENT_DATA_SET__DESCRIPTION);
    createEAttribute(cellMeasurementDataSetEClass, CELL_MEASUREMENT_DATA_SET__DATE_MEASURED);
    createEAttribute(cellMeasurementDataSetEClass, CELL_MEASUREMENT_DATA_SET__AREA);
    createEAttribute(cellMeasurementDataSetEClass, CELL_MEASUREMENT_DATA_SET__POWER_INPUT);
    createEReference(cellMeasurementDataSetEClass, CELL_MEASUREMENT_DATA_SET__DATA);

    uiDataPointEClass = createEClass(UI_DATA_POINT);
    createEAttribute(uiDataPointEClass, UI_DATA_POINT__VOLTAGE);
    createEAttribute(uiDataPointEClass, UI_DATA_POINT__CURRENT);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes, features, and operations; add parameters
    initEClass(databaseEClass, Database.class, "Database", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getDatabase_CellGroups(), this.getCellGroup(), null, "cellGroups", null, 0, -1, Database.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(cellResultEClass, CellResult.class, "CellResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCellResult_Name(), ecorePackage.getEString(), "name", "default...", 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_Description(), ecorePackage.getEString(), "description", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_DataEvaluated(), ecorePackage.getEDate(), "dataEvaluated", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_OpenCircuitVoltage(), ecorePackage.getEDouble(), "openCircuitVoltage", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_ShortCircuitCurrent(), ecorePackage.getEDouble(), "shortCircuitCurrent", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_ParallelResistance(), ecorePackage.getEDouble(), "parallelResistance", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_DarkParallelResistance(), ecorePackage.getEDouble(), "darkParallelResistance", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_SeriesResistance(), ecorePackage.getEDouble(), "seriesResistance", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_DarkSeriesResistance(), ecorePackage.getEDouble(), "darkSeriesResistance", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_MaximumPowerVoltage(), ecorePackage.getEDouble(), "maximumPowerVoltage", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_MaximumPowerCurrent(), ecorePackage.getEDouble(), "maximumPowerCurrent", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_Efficiency(), ecorePackage.getEDouble(), "efficiency", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_FillFactor(), ecorePackage.getEDouble(), "fillFactor", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCellResult_LightMeasurementDataSet(), this.getCellMeasurementDataSet(), null, "lightMeasurementDataSet", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCellResult_DarkMeasuremenetDataSet(), this.getCellMeasurementDataSet(), null, "darkMeasuremenetDataSet", null, 0, 1, CellResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellResult_MaximumPower(), ecorePackage.getEDouble(), "maximumPower", null, 0, 1, CellResult.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    EOperation op = initEOperation(getCellResult__ValidateName__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "validateName", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEDiagnosticChain(), "chain", 0, 1, IS_UNIQUE, IS_ORDERED);
    EGenericType g1 = createEGenericType(ecorePackage.getEMap());
    EGenericType g2 = createEGenericType();
    g1.getETypeArguments().add(g2);
    g2 = createEGenericType();
    g1.getETypeArguments().add(g2);
    addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(cellGroupEClass, CellGroup.class, "CellGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCellGroup_Name(), ecorePackage.getEString(), "name", "Unkown Group", 0, 1, CellGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellGroup_Description(), ecorePackage.getEString(), "description", null, 0, 1, CellGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCellGroup_CellResults(), this.getCellResult(), null, "cellResults", null, 0, -1, CellGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    op = initEOperation(getCellGroup__CellResultsNamesCorrect__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "cellResultsNamesCorrect", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEDiagnosticChain(), "chain", 0, 1, IS_UNIQUE, IS_ORDERED);
    g1 = createEGenericType(ecorePackage.getEMap());
    g2 = createEGenericType();
    g1.getETypeArguments().add(g2);
    g2 = createEGenericType();
    g1.getETypeArguments().add(g2);
    addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(cellMeasurementDataSetEClass, CellMeasurementDataSet.class, "CellMeasurementDataSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCellMeasurementDataSet_Name(), ecorePackage.getEString(), "name", null, 0, 1, CellMeasurementDataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellMeasurementDataSet_Description(), ecorePackage.getEString(), "description", null, 0, 1, CellMeasurementDataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellMeasurementDataSet_DateMeasured(), ecorePackage.getEDate(), "dateMeasured", null, 0, 1, CellMeasurementDataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellMeasurementDataSet_Area(), ecorePackage.getEDouble(), "area", null, 0, 1, CellMeasurementDataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCellMeasurementDataSet_PowerInput(), ecorePackage.getEDouble(), "powerInput", null, 0, 1, CellMeasurementDataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCellMeasurementDataSet_Data(), this.getUIDataPoint(), null, "data", null, 0, -1, CellMeasurementDataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(uiDataPointEClass, UIDataPoint.class, "UIDataPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUIDataPoint_Voltage(), ecorePackage.getEDouble(), "Voltage", null, 0, 1, UIDataPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getUIDataPoint_Current(), ecorePackage.getEDouble(), "Current", null, 0, 1, UIDataPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} // DatamodelPackageImpl
