/**
 */
package datamodel.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.RegexPatterns;
import datamodel.CellGroup;
import datamodel.CellResult;
import datamodel.DatamodelPackage;
import datamodel.util.DatamodelValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Cell Group</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link datamodel.impl.CellGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link datamodel.impl.CellGroupImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link datamodel.impl.CellGroupImpl#getCellResults <em>Cell Results</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CellGroupImpl extends MinimalEObjectImpl.Container implements CellGroup {
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = "Unkown Group";

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * The cached value of the '{@link #getCellResults() <em>Cell Results</em>}' containment reference list.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getCellResults()
   * @generated
   * @ordered
   */
  protected EList<CellResult> cellResults;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected CellGroupImpl() {
    super();
  }



  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return DatamodelPackage.Literals.CELL_GROUP;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated NOT
   */
  public String getName() {
    calculateNewName();
    return name;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.CELL_GROUP__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.CELL_GROUP__DESCRIPTION, oldDescription, description));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated NOT
   */
  public EList<CellResult> getCellResults() {
    if (cellResults == null) {
      cellResults = new EObjectContainmentEList<CellResult>(CellResult.class, this,
          DatamodelPackage.CELL_GROUP__CELL_RESULTS) {
        /**
         * 
         */
        private static final long serialVersionUID = -4800878187497603123L;

        @Override
        protected void didAdd(int index, CellResult newObject) {
          super.didAdd(index, newObject);
          newObject.eAdapters()
              .add(new AdapterImpl() {
                @Override
                public void notifyChanged(Notification msg) {
                  if (msg.getFeature() != null) {
                    if (msg.getFeature()
                        .equals(DatamodelPackage.Literals.CELL_RESULT__NAME)) {
                      calculateNewName();
                    }
                  }
                  super.notifyChanged(msg);
                }
              });
        }
      };
    }
    return cellResults;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated NOT
   */
  public boolean cellResultsNamesCorrect(DiagnosticChain chain, Map<?, ?> context) {
    if (cellResultsNamedProperly() == false) {
      if (chain != null) {
        chain.add(new BasicDiagnostic(Diagnostic.WARNING, DatamodelValidator.DIAGNOSTIC_SOURCE,
            DatamodelValidator.CELL_GROUP__CELL_RESULTS_NAMES_CORRECT,
            "CellResults are not correctly named.",
            new Object[] {this, DatamodelPackage.eINSTANCE.getCellResult_Name()}));
      }
      return false;
    }
    return true;
  }



  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
      NotificationChain msgs) {
    switch (featureID) {
      case DatamodelPackage.CELL_GROUP__CELL_RESULTS:
        return ((InternalEList<?>)getCellResults()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case DatamodelPackage.CELL_GROUP__NAME:
        return getName();
      case DatamodelPackage.CELL_GROUP__DESCRIPTION:
        return getDescription();
      case DatamodelPackage.CELL_GROUP__CELL_RESULTS:
        return getCellResults();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case DatamodelPackage.CELL_GROUP__NAME:
        setName((String)newValue);
        return;
      case DatamodelPackage.CELL_GROUP__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case DatamodelPackage.CELL_GROUP__CELL_RESULTS:
        getCellResults().clear();
        getCellResults().addAll((Collection<? extends CellResult>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case DatamodelPackage.CELL_GROUP__NAME:
        setName(NAME_EDEFAULT);
        return;
      case DatamodelPackage.CELL_GROUP__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case DatamodelPackage.CELL_GROUP__CELL_RESULTS:
        getCellResults().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case DatamodelPackage.CELL_GROUP__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case DatamodelPackage.CELL_GROUP__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
      case DatamodelPackage.CELL_GROUP__CELL_RESULTS:
        return cellResults != null && !cellResults.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
      case DatamodelPackage.CELL_GROUP___CELL_RESULTS_NAMES_CORRECT__DIAGNOSTICCHAIN_MAP:
        return cellResultsNamesCorrect((DiagnosticChain)arguments.get(0), (Map<?, ?>)arguments.get(1));
    }
    return super.eInvoke(operationID, arguments);
  }



  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", description: ");
    result.append(description);
    result.append(')');
    return result.toString();
  }

  /**
   * 
   * 
   * @generated NOT
   */
  private void calculateNewName() {
    EList<CellResult> cellResults = getCellResults();
    if (cellResults == null || cellResults.isEmpty()) {
      String name = "Cannot be deduced...";
      setName(name);
      return;
    }
    String regex = ConfigurationScope.INSTANCE.getNode(PrefNodes.REGEX_PATTERNS)
        .get(RegexPatterns.LABVIEW_GROUP_COMPLEMENT, "");
    Map<String, List<CellResult>> grouped = cellResults.stream()
        .collect(Collectors.groupingBy(cellResult -> cellResult.getName()
            .replaceAll(regex, "")));

    String name = grouped.entrySet()
        .stream()
        .max((a, b) -> Integer.max(a.getValue()
            .size(),
            b.getValue()
                .size()))
        .orElse(null)
        .getKey();

    if (grouped.keySet()
        .size() != 1) {
      setName(name + " (Not named properly)");
      return;
    }
    setName(name);
  }

  /**
   * @generated NOT
   */
  private boolean cellResultsNamedProperly() {
    EList<CellResult> cellResults = getCellResults();
    if (cellResults == null || cellResults.isEmpty()) {
      return false;
    }
    String regex = ConfigurationScope.INSTANCE.getNode(PrefNodes.REGEX_PATTERNS)
        .get(RegexPatterns.LABVIEW_GROUP_COMPLEMENT, "");
    Map<String, List<CellResult>> grouped = cellResults.stream()
        .collect(Collectors.groupingBy(cellResult -> cellResult.getName()
            .replaceAll(regex, "")));
    if (grouped.keySet()
        .size() != 1) {
      return false;
    }
    return true;
  }

} // CellGroupImpl
