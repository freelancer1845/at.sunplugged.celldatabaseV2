/**
 */
package datamodel.provider;


import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.DatamodelPackage;

/**
 * This is the item provider adapter for a {@link datamodel.CellResult} object.
 * <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * @generated
 */
public class CellResultItemProvider extends ItemProviderAdapter
    implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
    IItemLabelProvider, IItemPropertySource {
  /**
   * This constructs an instance from a factory and a notifier.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  public CellResultItemProvider(AdapterFactory adapterFactory) {
    super(adapterFactory);
  }

  /**
   * This returns the property descriptors for the adapted class.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
    if (itemPropertyDescriptors == null) {
      super.getPropertyDescriptors(object);

      addNamePropertyDescriptor(object);
      addDescriptionPropertyDescriptor(object);
      addDataEvaluatedPropertyDescriptor(object);
      addOpenCircuitVoltagePropertyDescriptor(object);
      addNumberOfCellsPropertyDescriptor(object);
      addShortCircuitCurrentPropertyDescriptor(object);
      addParallelResistancePropertyDescriptor(object);
      addDarkParallelResistancePropertyDescriptor(object);
      addSeriesResistancePropertyDescriptor(object);
      addDarkSeriesResistancePropertyDescriptor(object);
      addMaximumPowerVoltagePropertyDescriptor(object);
      addMaximumPowerCurrentPropertyDescriptor(object);
      addEfficiencyPropertyDescriptor(object);
      addFillFactorPropertyDescriptor(object);
      addMaximumPowerPropertyDescriptor(object);
      addRsVocFitCoefficientsPropertyDescriptor(object);
      addRpIscFitCoefficientsPropertyDescriptor(object);
      addMppFitCoefficientsPropertyDescriptor(object);
      addDarkRpFitCoefficientsPropertyDescriptor(object);
      addDarkRsFitCoefficientsPropertyDescriptor(object);
      addVocPerCellPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Name feature. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   */
  protected void addNamePropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_name_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_name_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Description feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addDescriptionPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_description_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_description_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__DESCRIPTION,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Data Evaluated feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addDataEvaluatedPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_dataEvaluated_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_dataEvaluated_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__DATA_EVALUATED,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Open Circuit Voltage feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addOpenCircuitVoltagePropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_openCircuitVoltage_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_openCircuitVoltage_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Number Of Cells feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addNumberOfCellsPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_numberOfCells_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_numberOfCells_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__NUMBER_OF_CELLS,
         true,
         false,
         false,
         ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Short Circuit Current feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addShortCircuitCurrentPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_shortCircuitCurrent_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_shortCircuitCurrent_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__SHORT_CIRCUIT_CURRENT,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Parallel Resistance feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addParallelResistancePropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_parallelResistance_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_parallelResistance_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__PARALLEL_RESISTANCE,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Dark Parallel Resistance feature.
   * <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * @generated
   */
  protected void addDarkParallelResistancePropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_darkParallelResistance_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_darkParallelResistance_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__DARK_PARALLEL_RESISTANCE,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Series Resistance feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addSeriesResistancePropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_seriesResistance_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_seriesResistance_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__SERIES_RESISTANCE,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Dark Series Resistance feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addDarkSeriesResistancePropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_darkSeriesResistance_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_darkSeriesResistance_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__DARK_SERIES_RESISTANCE,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Maximum Power Voltage feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addMaximumPowerVoltagePropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_maximumPowerVoltage_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_maximumPowerVoltage_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__MAXIMUM_POWER_VOLTAGE,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Maximum Power Current feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addMaximumPowerCurrentPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_maximumPowerCurrent_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_maximumPowerCurrent_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__MAXIMUM_POWER_CURRENT,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Efficiency feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addEfficiencyPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_efficiency_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_efficiency_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__EFFICIENCY,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Fill Factor feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addFillFactorPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_fillFactor_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_fillFactor_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__FILL_FACTOR,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Maximum Power feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addMaximumPowerPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_maximumPower_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_maximumPower_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__MAXIMUM_POWER,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Rs Voc Fit Coefficients feature.
   * <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * @generated
   */
  protected void addRsVocFitCoefficientsPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_rsVocFitCoefficients_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_rsVocFitCoefficients_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__RS_VOC_FIT_COEFFICIENTS,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Rp Isc Fit Coefficients feature.
   * <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * @generated
   */
  protected void addRpIscFitCoefficientsPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_rpIscFitCoefficients_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_rpIscFitCoefficients_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__RP_ISC_FIT_COEFFICIENTS,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Mpp Fit Coefficients feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addMppFitCoefficientsPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_mppFitCoefficients_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_mppFitCoefficients_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__MPP_FIT_COEFFICIENTS,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Dark Rp Fit Coefficients feature.
   * <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * @generated
   */
  protected void addDarkRpFitCoefficientsPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_darkRpFitCoefficients_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_darkRpFitCoefficients_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__DARK_RP_FIT_COEFFICIENTS,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Dark Rs Fit Coefficients feature.
   * <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * @generated
   */
  protected void addDarkRsFitCoefficientsPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_darkRsFitCoefficients_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_darkRsFitCoefficients_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__DARK_RS_FIT_COEFFICIENTS,
         true,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Voc Per Cell feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addVocPerCellPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellResult_vocPerCell_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellResult_vocPerCell_feature", "_UI_CellResult_type"),
         DatamodelPackage.Literals.CELL_RESULT__VOC_PER_CELL,
         false,
         false,
         false,
         ItemPropertyDescriptor.REAL_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
   * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
   * <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
    if (childrenFeatures == null) {
      super.getChildrenFeatures(object);
      childrenFeatures.add(DatamodelPackage.Literals.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET);
      childrenFeatures.add(DatamodelPackage.Literals.CELL_RESULT__DARK_MEASUREMENET_DATA_SET);
    }
    return childrenFeatures;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EStructuralFeature getChildFeature(Object object, Object child) {
    // Check the type of the specified child object and return the proper feature to use for
    // adding (see {@link AddCommand}) it as a child.

    return super.getChildFeature(object, child);
  }

  /**
   * This returns CellResult.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated NOT
   */
  @Override
  public Object getImage(Object object) {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/CellResult.png"));
  }

  /**
   * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated NOT
   */
  @Override
  public String getText(Object object) {
    String label = ((CellResult) object).getName();

    String type = "Cell";
    if (((CellResult) object).getNumberOfCells() > 1) {
      type = "Module";
    }

    return label == null || label.length() == 0 ? type : type + " " + label;
  }


  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void notifyChanged(Notification notification) {
    updateChildren(notification);

    switch (notification.getFeatureID(CellResult.class)) {
      case DatamodelPackage.CELL_RESULT__NAME:
      case DatamodelPackage.CELL_RESULT__DESCRIPTION:
      case DatamodelPackage.CELL_RESULT__DATA_EVALUATED:
      case DatamodelPackage.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE:
      case DatamodelPackage.CELL_RESULT__NUMBER_OF_CELLS:
      case DatamodelPackage.CELL_RESULT__SHORT_CIRCUIT_CURRENT:
      case DatamodelPackage.CELL_RESULT__PARALLEL_RESISTANCE:
      case DatamodelPackage.CELL_RESULT__DARK_PARALLEL_RESISTANCE:
      case DatamodelPackage.CELL_RESULT__SERIES_RESISTANCE:
      case DatamodelPackage.CELL_RESULT__DARK_SERIES_RESISTANCE:
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_VOLTAGE:
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER_CURRENT:
      case DatamodelPackage.CELL_RESULT__EFFICIENCY:
      case DatamodelPackage.CELL_RESULT__FILL_FACTOR:
      case DatamodelPackage.CELL_RESULT__MAXIMUM_POWER:
      case DatamodelPackage.CELL_RESULT__RS_VOC_FIT_COEFFICIENTS:
      case DatamodelPackage.CELL_RESULT__RP_ISC_FIT_COEFFICIENTS:
      case DatamodelPackage.CELL_RESULT__MPP_FIT_COEFFICIENTS:
      case DatamodelPackage.CELL_RESULT__DARK_RP_FIT_COEFFICIENTS:
      case DatamodelPackage.CELL_RESULT__DARK_RS_FIT_COEFFICIENTS:
      case DatamodelPackage.CELL_RESULT__VOC_PER_CELL:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case DatamodelPackage.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET:
      case DatamodelPackage.CELL_RESULT__DARK_MEASUREMENET_DATA_SET:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
        return;
    }
    super.notifyChanged(notification);
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
   * that can be created under this object.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
    super.collectNewChildDescriptors(newChildDescriptors, object);

    newChildDescriptors.add
      (createChildParameter
        (DatamodelPackage.Literals.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET,
         DatamodelFactory.eINSTANCE.createCellMeasurementDataSet()));

    newChildDescriptors.add
      (createChildParameter
        (DatamodelPackage.Literals.CELL_RESULT__DARK_MEASUREMENET_DATA_SET,
         DatamodelFactory.eINSTANCE.createCellMeasurementDataSet()));
  }

  /**
   * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public String getCreateChildText(Object owner, Object feature, Object child,
      Collection<?> selection) {
    Object childFeature = feature;
    Object childObject = child;

    boolean qualify =
      childFeature == DatamodelPackage.Literals.CELL_RESULT__LIGHT_MEASUREMENT_DATA_SET ||
      childFeature == DatamodelPackage.Literals.CELL_RESULT__DARK_MEASUREMENET_DATA_SET;

    if (qualify) {
      return getString
        ("_UI_CreateChild_text2",
         new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
    }
    return super.getCreateChildText(owner, feature, child, selection);
  }

  /**
   * Return the resource locator for this item provider's resources.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public ResourceLocator getResourceLocator() {
    return DatamodelEditPlugin.INSTANCE;
  }

}
