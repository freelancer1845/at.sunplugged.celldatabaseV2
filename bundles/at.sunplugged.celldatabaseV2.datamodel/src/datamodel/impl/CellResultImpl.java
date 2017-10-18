/**
 */
package datamodel.impl;

import java.util.Date;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.DatamodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Cell Result</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link datamodel.impl.CellResultImpl#getName <em>Name</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getDescription <em>Description</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getDataEvaluated <em>Data Evaluated</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getOpenCircuitVoltage <em>Open Circuit
 * Voltage</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getShortCircuitCurrent <em>Short Circuit
 * Current</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getParallelResistance <em>Parallel Resistance</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getDarkParallelResistance <em>Dark Parallel
 * Resistance</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getSeriesResistance <em>Series Resistance</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getDarkSeriesResistance <em>Dark Series
 * Resistance</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getMaximumPowerVoltage <em>Maximum Power
 * Voltage</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getMaximumPowerCurrent <em>Maximum Power
 * Current</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getEfficiency <em>Efficiency</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getFillFactor <em>Fill Factor</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getLightMeasurementDataSet <em>Light Measurement Data
 * Set</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getDarkMeasuremenetDataSet <em>Dark Measuremenet Data
 * Set</em>}</li>
 * <li>{@link datamodel.impl.CellResultImpl#getMaximumPower <em>Maximum Power</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CellResultImpl extends MinimalEObjectImpl.Container implements CellResult {
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = "default...";

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
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
   * The default value of the '{@link #getDataEvaluated() <em>Data Evaluated</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDataEvaluated()
   * @generated
   * @ordered
   */
  protected static final Date DATA_EVALUATED_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDataEvaluated() <em>Data Evaluated</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDataEvaluated()
   * @generated
   * @ordered
   */
  protected Date dataEvaluated = DATA_EVALUATED_EDEFAULT;

  /**
   * The default value of the '{@link #getOpenCircuitVoltage() <em>Open Circuit Voltage</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getOpenCircuitVoltage()
   * @generated
   * @ordered
   */
  protected static final double OPEN_CIRCUIT_VOLTAGE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getOpenCircuitVoltage() <em>Open Circuit Voltage</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getOpenCircuitVoltage()
   * @generated
   * @ordered
   */
  protected double openCircuitVoltage = OPEN_CIRCUIT_VOLTAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getShortCircuitCurrent() <em>Short Circuit Current</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getShortCircuitCurrent()
   * @generated
   * @ordered
   */
  protected static final double SHORT_CIRCUIT_CURRENT_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getShortCircuitCurrent() <em>Short Circuit Current</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getShortCircuitCurrent()
   * @generated
   * @ordered
   */
  protected double shortCircuitCurrent = SHORT_CIRCUIT_CURRENT_EDEFAULT;

  /**
   * The default value of the '{@link #getParallelResistance() <em>Parallel Resistance</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getParallelResistance()
   * @generated
   * @ordered
   */
  protected static final double PARALLEL_RESISTANCE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getParallelResistance() <em>Parallel Resistance</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getParallelResistance()
   * @generated
   * @ordered
   */
  protected double parallelResistance = PARALLEL_RESISTANCE_EDEFAULT;

  /**
   * The default value of the '{@link #getDarkParallelResistance() <em>Dark Parallel
   * Resistance</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDarkParallelResistance()
   * @generated
   * @ordered
   */
  protected static final double DARK_PARALLEL_RESISTANCE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getDarkParallelResistance() <em>Dark Parallel
   * Resistance</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDarkParallelResistance()
   * @generated
   * @ordered
   */
  protected double darkParallelResistance = DARK_PARALLEL_RESISTANCE_EDEFAULT;

  /**
   * The default value of the '{@link #getSeriesResistance() <em>Series Resistance</em>}' attribute.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getSeriesResistance()
   * @generated
   * @ordered
   */
  protected static final double SERIES_RESISTANCE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getSeriesResistance() <em>Series Resistance</em>}' attribute.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getSeriesResistance()
   * @generated
   * @ordered
   */
  protected double seriesResistance = SERIES_RESISTANCE_EDEFAULT;

  /**
   * The default value of the '{@link #getDarkSeriesResistance() <em>Dark Series Resistance</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDarkSeriesResistance()
   * @generated
   * @ordered
   */
  protected static final double DARK_SERIES_RESISTANCE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getDarkSeriesResistance() <em>Dark Series Resistance</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDarkSeriesResistance()
   * @generated
   * @ordered
   */
  protected double darkSeriesResistance = DARK_SERIES_RESISTANCE_EDEFAULT;

  /**
   * The default value of the '{@link #getMaximumPowerVoltage() <em>Maximum Power Voltage</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getMaximumPowerVoltage()
   * @generated
   * @ordered
   */
  protected static final double MAXIMUM_POWER_VOLTAGE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getMaximumPowerVoltage() <em>Maximum Power Voltage</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getMaximumPowerVoltage()
   * @generated
   * @ordered
   */
  protected double maximumPowerVoltage = MAXIMUM_POWER_VOLTAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getMaximumPowerCurrent() <em>Maximum Power Current</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getMaximumPowerCurrent()
   * @generated
   * @ordered
   */
  protected static final double MAXIMUM_POWER_CURRENT_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getMaximumPowerCurrent() <em>Maximum Power Current</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getMaximumPowerCurrent()
   * @generated
   * @ordered
   */
  protected double maximumPowerCurrent = MAXIMUM_POWER_CURRENT_EDEFAULT;

  /**
   * The default value of the '{@link #getEfficiency() <em>Efficiency</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getEfficiency()
   * @generated
   * @ordered
   */
  protected static final double EFFICIENCY_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getEfficiency() <em>Efficiency</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getEfficiency()
   * @generated
   * @ordered
   */
  protected double efficiency = EFFICIENCY_EDEFAULT;

  /**
   * The default value of the '{@link #getFillFactor() <em>Fill Factor</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getFillFactor()
   * @generated
   * @ordered
   */
  protected static final double FILL_FACTOR_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getFillFactor() <em>Fill Factor</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getFillFactor()
   * @generated
   * @ordered
   */
  protected double fillFactor = FILL_FACTOR_EDEFAULT;

  /**
   * The cached value of the '{@link #getLightMeasurementDataSet() <em>Light Measurement Data
   * Set</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getLightMeasurementDataSet()
   * @generated
   * @ordered
   */
  protected CellMeasurementDataSet lightMeasurementDataSet;

  /**
   * The cached value of the '{@link #getDarkMeasuremenetDataSet() <em>Dark Measuremenet Data
   * Set</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDarkMeasuremenetDataSet()
   * @generated
   * @ordered
   */
  protected CellMeasurementDataSet darkMeasuremenetDataSet;

  /**
   * The default value of the '{@link #getMaximumPower() <em>Maximum Power</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getMaximumPower()
   * @generated
   * @ordered
   */
  protected static final double MAXIMUM_POWER_EDEFAULT = 0.0;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected CellResultImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return DatamodelPackage.Literals.CELL_RESULT;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.CELL_RESULT__NAME,
          oldName, name));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__DESCRIPTION, oldDescription, description));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public Date getDataEvaluated() {
    return dataEvaluated;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setDataEvaluated(Date newDataEvaluated) {
    Date oldDataEvaluated = dataEvaluated;
    dataEvaluated = newDataEvaluated;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__DATA_EVALUATED, oldDataEvaluated, dataEvaluated));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getOpenCircuitVoltage() {
    return openCircuitVoltage;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setOpenCircuitVoltage(double newOpenCircuitVoltage) {
    double oldOpenCircuitVoltage = openCircuitVoltage;
    openCircuitVoltage = newOpenCircuitVoltage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE, oldOpenCircuitVoltage,
          openCircuitVoltage));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getShortCircuitCurrent() {
    return shortCircuitCurrent;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setShortCircuitCurrent(double newShortCircuitCurrent) {
    double oldShortCircuitCurrent = shortCircuitCurrent;
    shortCircuitCurrent = newShortCircuitCurrent;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__SHORT_CIRCUIT_CURRENT, oldShortCircuitCurrent,
          shortCircuitCurrent));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getParallelResistance() {
    return parallelResistance;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setParallelResistance(double newParallelResistance) {
    double oldParallelResistance = parallelResistance;
    parallelResistance = newParallelResistance;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__PARALLEL_RESISTANCE, oldParallelResistance,
          parallelResistance));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getDarkParallelResistance() {
    return darkParallelResistance;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setDarkParallelResistance(double newDarkParallelResistance) {
    double oldDarkParallelResistance = darkParallelResistance;
    darkParallelResistance = newDarkParallelResistance;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__DARK_PARALLEL_RESISTANCE, oldDarkParallelResistance,
          darkParallelResistance));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getSeriesResistance() {
    return seriesResistance;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setSeriesResistance(double newSeriesResistance) {
    double oldSeriesResistance = seriesResistance;
    seriesResistance = newSeriesResistance;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__SERIES_RESISTANCE, oldSeriesResistance, seriesResistance));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getDarkSeriesResistance() {
    return darkSeriesResistance;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setDarkSeriesResistance(double newDarkSeriesResistance) {
    double oldDarkSeriesResistance = darkSeriesResistance;
    darkSeriesResistance = newDarkSeriesResistance;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__DARK_SERIES_RESISTANCE, oldDarkSeriesResistance,
          darkSeriesResistance));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getMaximumPowerVoltage() {
    return maximumPowerVoltage;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setMaximumPowerVoltage(double newMaximumPowerVoltage) {
    double oldMaximumPowerVoltage = maximumPowerVoltage;
    maximumPowerVoltage = newMaximumPowerVoltage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_VOLTAGE, oldMaximumPowerVoltage,
          maximumPowerVoltage));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getMaximumPowerCurrent() {
    return maximumPowerCurrent;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setMaximumPowerCurrent(double newMaximumPowerCurrent) {
    double oldMaximumPowerCurrent = maximumPowerCurrent;
    maximumPowerCurrent = newMaximumPowerCurrent;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_CURRENT, oldMaximumPowerCurrent,
          maximumPowerCurrent));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getEfficiency() {
    return efficiency;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setEfficiency(double newEfficiency) {
    double oldEfficiency = efficiency;
    efficiency = newEfficiency;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__EFFICIENCY, oldEfficiency, efficiency));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getFillFactor() {
    return fillFactor;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setFillFactor(double newFillFactor) {
    double oldFillFactor = fillFactor;
    fillFactor = newFillFactor;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__FILL_FACTOR, oldFillFactor, fillFactor));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public CellMeasurementDataSet getLightMeasurementDataSet() {
    return lightMeasurementDataSet;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public NotificationChain basicSetLightMeasurementDataSet(
      CellMeasurementDataSet newLightMeasurementDataSet, NotificationChain msgs) {
    CellMeasurementDataSet oldLightMeasurementDataSet = lightMeasurementDataSet;
    lightMeasurementDataSet = newLightMeasurementDataSet;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET, oldLightMeasurementDataSet,
          newLightMeasurementDataSet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setLightMeasurementDataSet(CellMeasurementDataSet newLightMeasurementDataSet) {
    if (newLightMeasurementDataSet != lightMeasurementDataSet) {
      NotificationChain msgs = null;
      if (lightMeasurementDataSet != null)
        msgs = ((InternalEObject) lightMeasurementDataSet).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET, null,
            msgs);
      if (newLightMeasurementDataSet != null)
        msgs = ((InternalEObject) newLightMeasurementDataSet).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET, null,
            msgs);
      msgs = basicSetLightMeasurementDataSet(newLightMeasurementDataSet, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET, newLightMeasurementDataSet,
          newLightMeasurementDataSet));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public CellMeasurementDataSet getDarkMeasuremenetDataSet() {
    return darkMeasuremenetDataSet;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public NotificationChain basicSetDarkMeasuremenetDataSet(
      CellMeasurementDataSet newDarkMeasuremenetDataSet, NotificationChain msgs) {
    CellMeasurementDataSet oldDarkMeasuremenetDataSet = darkMeasuremenetDataSet;
    darkMeasuremenetDataSet = newDarkMeasuremenetDataSet;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET, oldDarkMeasuremenetDataSet,
          newDarkMeasuremenetDataSet);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setDarkMeasuremenetDataSet(CellMeasurementDataSet newDarkMeasuremenetDataSet) {
    if (newDarkMeasuremenetDataSet != darkMeasuremenetDataSet) {
      NotificationChain msgs = null;
      if (darkMeasuremenetDataSet != null)
        msgs = ((InternalEObject) darkMeasuremenetDataSet).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET, null,
            msgs);
      if (newDarkMeasuremenetDataSet != null)
        msgs = ((InternalEObject) newDarkMeasuremenetDataSet).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET, null,
            msgs);
      msgs = basicSetDarkMeasuremenetDataSet(newDarkMeasuremenetDataSet, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET, newDarkMeasuremenetDataSet,
          newDarkMeasuremenetDataSet));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated NOT
   */
  public double getMaximumPower() {
    return getMaximumPowerCurrent() * getMaximumPowerVoltage();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setMaximumPower(double newMaximumPower) {
    // TODO: implement this method to set the 'Maximum Power' attribute
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
      NotificationChain msgs) {
    switch (featureID) {
      case DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET:
        return basicSetLightMeasurementDataSet(null, msgs);
      case DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET:
        return basicSetDarkMeasuremenetDataSet(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case DatamodelPackage.CELL_RESULT__NAME:
        return getName();
      case DatamodelPackage.CELL_RESULT__DESCRIPTION:
        return getDescription();
      case DatamodelPackage.CELL_RESULT__DATA_EVALUATED:
        return getDataEvaluated();
      case DatamodelPackage.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE:
        return getOpenCircuitVoltage();
      case DatamodelPackage.CELL_RESULT__SHORT_CIRCUIT_CURRENT:
        return getShortCircuitCurrent();
      case DatamodelPackage.CELL_RESULT__PARALLEL_RESISTANCE:
        return getParallelResistance();
      case DatamodelPackage.CELL_RESULT__DARK_PARALLEL_RESISTANCE:
        return getDarkParallelResistance();
      case DatamodelPackage.CELL_RESULT__SERIES_RESISTANCE:
        return getSeriesResistance();
      case DatamodelPackage.CELL_RESULT__DARK_SERIES_RESISTANCE:
        return getDarkSeriesResistance();
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_VOLTAGE:
        return getMaximumPowerVoltage();
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_CURRENT:
        return getMaximumPowerCurrent();
      case DatamodelPackage.CELL_RESULT__EFFICIENCY:
        return getEfficiency();
      case DatamodelPackage.CELL_RESULT__FILL_FACTOR:
        return getFillFactor();
      case DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET:
        return getLightMeasurementDataSet();
      case DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET:
        return getDarkMeasuremenetDataSet();
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER:
        return getMaximumPower();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case DatamodelPackage.CELL_RESULT__NAME:
        setName((String) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__DESCRIPTION:
        setDescription((String) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__DATA_EVALUATED:
        setDataEvaluated((Date) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE:
        setOpenCircuitVoltage((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__SHORT_CIRCUIT_CURRENT:
        setShortCircuitCurrent((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__PARALLEL_RESISTANCE:
        setParallelResistance((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__DARK_PARALLEL_RESISTANCE:
        setDarkParallelResistance((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__SERIES_RESISTANCE:
        setSeriesResistance((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__DARK_SERIES_RESISTANCE:
        setDarkSeriesResistance((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_VOLTAGE:
        setMaximumPowerVoltage((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_CURRENT:
        setMaximumPowerCurrent((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__EFFICIENCY:
        setEfficiency((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__FILL_FACTOR:
        setFillFactor((Double) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET:
        setLightMeasurementDataSet((CellMeasurementDataSet) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET:
        setDarkMeasuremenetDataSet((CellMeasurementDataSet) newValue);
        return;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER:
        setMaximumPower((Double) newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case DatamodelPackage.CELL_RESULT__NAME:
        setName(NAME_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__DATA_EVALUATED:
        setDataEvaluated(DATA_EVALUATED_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE:
        setOpenCircuitVoltage(OPEN_CIRCUIT_VOLTAGE_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__SHORT_CIRCUIT_CURRENT:
        setShortCircuitCurrent(SHORT_CIRCUIT_CURRENT_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__PARALLEL_RESISTANCE:
        setParallelResistance(PARALLEL_RESISTANCE_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__DARK_PARALLEL_RESISTANCE:
        setDarkParallelResistance(DARK_PARALLEL_RESISTANCE_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__SERIES_RESISTANCE:
        setSeriesResistance(SERIES_RESISTANCE_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__DARK_SERIES_RESISTANCE:
        setDarkSeriesResistance(DARK_SERIES_RESISTANCE_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_VOLTAGE:
        setMaximumPowerVoltage(MAXIMUM_POWER_VOLTAGE_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_CURRENT:
        setMaximumPowerCurrent(MAXIMUM_POWER_CURRENT_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__EFFICIENCY:
        setEfficiency(EFFICIENCY_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__FILL_FACTOR:
        setFillFactor(FILL_FACTOR_EDEFAULT);
        return;
      case DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET:
        setLightMeasurementDataSet((CellMeasurementDataSet) null);
        return;
      case DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET:
        setDarkMeasuremenetDataSet((CellMeasurementDataSet) null);
        return;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER:
        setMaximumPower(MAXIMUM_POWER_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case DatamodelPackage.CELL_RESULT__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case DatamodelPackage.CELL_RESULT__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null
            : !DESCRIPTION_EDEFAULT.equals(description);
      case DatamodelPackage.CELL_RESULT__DATA_EVALUATED:
        return DATA_EVALUATED_EDEFAULT == null ? dataEvaluated != null
            : !DATA_EVALUATED_EDEFAULT.equals(dataEvaluated);
      case DatamodelPackage.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE:
        return openCircuitVoltage != OPEN_CIRCUIT_VOLTAGE_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__SHORT_CIRCUIT_CURRENT:
        return shortCircuitCurrent != SHORT_CIRCUIT_CURRENT_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__PARALLEL_RESISTANCE:
        return parallelResistance != PARALLEL_RESISTANCE_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__DARK_PARALLEL_RESISTANCE:
        return darkParallelResistance != DARK_PARALLEL_RESISTANCE_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__SERIES_RESISTANCE:
        return seriesResistance != SERIES_RESISTANCE_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__DARK_SERIES_RESISTANCE:
        return darkSeriesResistance != DARK_SERIES_RESISTANCE_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_VOLTAGE:
        return maximumPowerVoltage != MAXIMUM_POWER_VOLTAGE_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_CURRENT:
        return maximumPowerCurrent != MAXIMUM_POWER_CURRENT_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__EFFICIENCY:
        return efficiency != EFFICIENCY_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__FILL_FACTOR:
        return fillFactor != FILL_FACTOR_EDEFAULT;
      case DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET:
        return lightMeasurementDataSet != null;
      case DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET:
        return darkMeasuremenetDataSet != null;
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER:
        return getMaximumPower() != MAXIMUM_POWER_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy())
      return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", description: ");
    result.append(description);
    result.append(", dataEvaluated: ");
    result.append(dataEvaluated);
    result.append(", openCircuitVoltage: ");
    result.append(openCircuitVoltage);
    result.append(", shortCircuitCurrent: ");
    result.append(shortCircuitCurrent);
    result.append(", parallelResistance: ");
    result.append(parallelResistance);
    result.append(", darkParallelResistance: ");
    result.append(darkParallelResistance);
    result.append(", seriesResistance: ");
    result.append(seriesResistance);
    result.append(", darkSeriesResistance: ");
    result.append(darkSeriesResistance);
    result.append(", maximumPowerVoltage: ");
    result.append(maximumPowerVoltage);
    result.append(", maximumPowerCurrent: ");
    result.append(maximumPowerCurrent);
    result.append(", efficiency: ");
    result.append(efficiency);
    result.append(", fillFactor: ");
    result.append(fillFactor);
    result.append(')');
    return result.toString();
  }

} // CellResultImpl
