/**
 */
package datamodel.util;

import datamodel.*;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see datamodel.DatamodelPackage
 * @generated
 */
public class DatamodelValidator extends EObjectValidator {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final DatamodelValidator INSTANCE = new DatamodelValidator();

  /**
   * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.common.util.Diagnostic#getSource()
   * @see org.eclipse.emf.common.util.Diagnostic#getCode()
   * @generated
   */
  public static final String DIAGNOSTIC_SOURCE = "datamodel";

  /**
   * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Validate Name' of 'Cell Result'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final int CELL_RESULT__VALIDATE_NAME = 1;

  /**
   * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Cell Results Names Correct' of 'Cell Group'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final int CELL_GROUP__CELL_RESULTS_NAMES_CORRECT = 2;

  /**
   * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Cell Group Name Unique' of 'Cell Group'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final int CELL_GROUP__CELL_GROUP_NAME_UNIQUE = 3;

  /**
   * A constant with a fixed name that can be used as the base value for additional hand written constants.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 3;

  /**
   * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DatamodelValidator() {
    super();
  }

  /**
   * Returns the package of this validator switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EPackage getEPackage() {
    return DatamodelPackage.eINSTANCE;
  }

  /**
   * Calls <code>validateXXX</code> for the corresponding classifier of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
    switch (classifierID) {
      case DatamodelPackage.DATABASE:
        return validateDatabase((Database)value, diagnostics, context);
      case DatamodelPackage.CELL_RESULT:
        return validateCellResult((CellResult)value, diagnostics, context);
      case DatamodelPackage.CELL_GROUP:
        return validateCellGroup((CellGroup)value, diagnostics, context);
      case DatamodelPackage.CELL_MEASUREMENT_DATA_SET:
        return validateCellMeasurementDataSet((CellMeasurementDataSet)value, diagnostics, context);
      case DatamodelPackage.UI_DATA_POINT:
        return validateUIDataPoint((UIDataPoint)value, diagnostics, context);
      default:
        return true;
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateDatabase(Database database, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(database, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateCellResult(CellResult cellResult, DiagnosticChain diagnostics, Map<Object, Object> context) {
    if (!validate_NoCircularContainment(cellResult, diagnostics, context)) return false;
    boolean result = validate_EveryMultiplicityConforms(cellResult, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryDataValueConforms(cellResult, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(cellResult, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(cellResult, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryProxyResolves(cellResult, diagnostics, context);
    if (result || diagnostics != null) result &= validate_UniqueID(cellResult, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryKeyUnique(cellResult, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(cellResult, diagnostics, context);
    if (result || diagnostics != null) result &= validateCellResult_validateName(cellResult, diagnostics, context);
    return result;
  }

  /**
   * Validates the validateName constraint of '<em>Cell Result</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateCellResult_validateName(CellResult cellResult, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return cellResult.validateName(diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateCellGroup(CellGroup cellGroup, DiagnosticChain diagnostics, Map<Object, Object> context) {
    if (!validate_NoCircularContainment(cellGroup, diagnostics, context)) return false;
    boolean result = validate_EveryMultiplicityConforms(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryDataValueConforms(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryProxyResolves(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validate_UniqueID(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryKeyUnique(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validateCellGroup_cellResultsNamesCorrect(cellGroup, diagnostics, context);
    if (result || diagnostics != null) result &= validateCellGroup_cellGroupNameUnique(cellGroup, diagnostics, context);
    return result;
  }

  /**
   * Validates the cellResultsNamesCorrect constraint of '<em>Cell Group</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateCellGroup_cellResultsNamesCorrect(CellGroup cellGroup, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return cellGroup.cellResultsNamesCorrect(diagnostics, context);
  }

  /**
   * Validates the cellGroupNameUnique constraint of '<em>Cell Group</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateCellGroup_cellGroupNameUnique(CellGroup cellGroup, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return cellGroup.cellGroupNameUnique(diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateCellMeasurementDataSet(CellMeasurementDataSet cellMeasurementDataSet, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(cellMeasurementDataSet, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateUIDataPoint(UIDataPoint uiDataPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(uiDataPoint, diagnostics, context);
  }

  /**
   * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ResourceLocator getResourceLocator() {
    // TODO
    // Specialize this to return a resource locator for messages specific to this validator.
    // Ensure that you remove @generated or mark it @generated NOT
    return super.getResourceLocator();
  }

} //DatamodelValidator
