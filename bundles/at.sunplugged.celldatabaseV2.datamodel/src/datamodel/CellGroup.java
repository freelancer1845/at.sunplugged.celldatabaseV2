/**
 */
package datamodel;

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cell Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link datamodel.CellGroup#getName <em>Name</em>}</li>
 *   <li>{@link datamodel.CellGroup#getDescription <em>Description</em>}</li>
 *   <li>{@link datamodel.CellGroup#getCellResults <em>Cell Results</em>}</li>
 *   <li>{@link datamodel.CellGroup#getNameSuffix <em>Name Suffix</em>}</li>
 * </ul>
 *
 * @see datamodel.DatamodelPackage#getCellGroup()
 * @model
 * @generated
 */
public interface CellGroup extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * The default value is <code>"Unkown Group"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see datamodel.DatamodelPackage#getCellGroup_Name()
   * @model default="Unkown Group"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link datamodel.CellGroup#getName <em>Name</em>}' attribute.
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
   * @see datamodel.DatamodelPackage#getCellGroup_Description()
   * @model
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link datamodel.CellGroup#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Cell Results</b></em>' containment reference list.
   * The list contents are of type {@link datamodel.CellResult}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Cell Results</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Cell Results</em>' containment reference list.
   * @see datamodel.DatamodelPackage#getCellGroup_CellResults()
   * @model containment="true"
   * @generated
   */
  EList<CellResult> getCellResults();

  /**
   * Returns the value of the '<em><b>Name Suffix</b></em>' attribute.
   * The default value is <code>""</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name Suffix</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name Suffix</em>' attribute.
   * @see #setNameSuffix(String)
   * @see datamodel.DatamodelPackage#getCellGroup_NameSuffix()
   * @model default="" unique="false"
   * @generated
   */
  String getNameSuffix();

  /**
   * Sets the value of the '{@link datamodel.CellGroup#getNameSuffix <em>Name Suffix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name Suffix</em>' attribute.
   * @see #getNameSuffix()
   * @generated
   */
  void setNameSuffix(String value);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  boolean cellResultsNamesCorrect(DiagnosticChain chain, Map<?, ?> context);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  boolean cellGroupNameUnique(DiagnosticChain chain, Map<?, ?> context);

} // CellGroup
