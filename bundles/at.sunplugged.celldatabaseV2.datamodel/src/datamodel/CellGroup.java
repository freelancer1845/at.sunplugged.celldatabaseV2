/**
 */
package datamodel;

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
 *   <li>{@link datamodel.CellGroup#isCustomName <em>Custom Name</em>}</li>
 * </ul>
 *
 * @see datamodel.DatamodelPackage#getCellGroup()
 * @model
 * @generated
 */
public interface CellGroup extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see datamodel.DatamodelPackage#getCellGroup_Name()
   * @model volatile="true" derived="true"
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
   * Returns the value of the '<em><b>Custom Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Custom Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Custom Name</em>' attribute.
   * @see #setCustomName(boolean)
   * @see datamodel.DatamodelPackage#getCellGroup_CustomName()
   * @model
   * @generated
   */
  boolean isCustomName();

  /**
   * Sets the value of the '{@link datamodel.CellGroup#isCustomName <em>Custom Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Custom Name</em>' attribute.
   * @see #isCustomName()
   * @generated
   */
  void setCustomName(boolean value);

} // CellGroup
