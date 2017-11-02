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
import datamodel.CellGroup;
import datamodel.DatamodelFactory;
import datamodel.DatamodelPackage;

/**
 * This is the item provider adapter for a {@link datamodel.CellGroup} object.
 * <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * @generated
 */
public class CellGroupItemProvider extends ItemProviderAdapter
    implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
    IItemLabelProvider, IItemPropertySource {
  /**
   * This constructs an instance from a factory and a notifier.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  public CellGroupItemProvider(AdapterFactory adapterFactory) {
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
      addCellResultsPropertyDescriptor(object);
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
         getString("_UI_CellGroup_name_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellGroup_name_feature", "_UI_CellGroup_type"),
         DatamodelPackage.Literals.CELL_GROUP__NAME,
         false,
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
         getString("_UI_CellGroup_description_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellGroup_description_feature", "_UI_CellGroup_type"),
         DatamodelPackage.Literals.CELL_GROUP__DESCRIPTION,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Cell Results feature.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected void addCellResultsPropertyDescriptor(Object object) {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_CellGroup_cellResults_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_CellGroup_cellResults_feature", "_UI_CellGroup_type"),
         DatamodelPackage.Literals.CELL_GROUP__CELL_RESULTS,
         true,
         false,
         true,
         null,
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
      childrenFeatures.add(DatamodelPackage.Literals.CELL_GROUP__CELL_RESULTS);
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
   * This returns CellGroup.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated NOT
   */
  @Override
  public Object getImage(Object object) {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/CellGroup.png"));
  }

  /**
   * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   */
  @Override
  public String getText(Object object) {
    String label = ((CellGroup)object).getName();
    return label == null || label.length() == 0 ?
      getString("_UI_CellGroup_type") :
      getString("_UI_CellGroup_type") + " " + label;
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

    switch (notification.getFeatureID(CellGroup.class)) {
      case DatamodelPackage.CELL_GROUP__NAME:
      case DatamodelPackage.CELL_GROUP__DESCRIPTION:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case DatamodelPackage.CELL_GROUP__CELL_RESULTS:
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
        (DatamodelPackage.Literals.CELL_GROUP__CELL_RESULTS,
         DatamodelFactory.eINSTANCE.createCellResult()));
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
