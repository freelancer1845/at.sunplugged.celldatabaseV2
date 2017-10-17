/**
 */
package datamodel.impl;

import datamodel.CellGroup;
import datamodel.Database;
import datamodel.DatamodelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Database</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link datamodel.impl.DatabaseImpl#getCellGroups <em>Cell Groups</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DatabaseImpl extends MinimalEObjectImpl.Container implements Database {
  /**
   * The cached value of the '{@link #getCellGroups() <em>Cell Groups</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCellGroups()
   * @generated
   * @ordered
   */
  protected EList<CellGroup> cellGroups;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DatabaseImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return DatamodelPackage.Literals.DATABASE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<CellGroup> getCellGroups() {
    if (cellGroups == null) {
      cellGroups = new EObjectContainmentEList<CellGroup>(CellGroup.class, this, DatamodelPackage.DATABASE__CELL_GROUPS);
    }
    return cellGroups;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case DatamodelPackage.DATABASE__CELL_GROUPS:
        return ((InternalEList<?>)getCellGroups()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case DatamodelPackage.DATABASE__CELL_GROUPS:
        return getCellGroups();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case DatamodelPackage.DATABASE__CELL_GROUPS:
        getCellGroups().clear();
        getCellGroups().addAll((Collection<? extends CellGroup>)newValue);
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
      case DatamodelPackage.DATABASE__CELL_GROUPS:
        getCellGroups().clear();
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
      case DatamodelPackage.DATABASE__CELL_GROUPS:
        return cellGroups != null && !cellGroups.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //DatabaseImpl
