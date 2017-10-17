
package at.sunplugged.celldatabasev2.rcp.main.parts;

import java.util.EventObject;
import javax.annotation.PostConstruct;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeViewerSWTFactory;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.Database;

public class ModelViewerPart {

  @PostConstruct
  public void postConstruct(Composite parent, DatabaseService databaseService,
      EMenuService menuService, ESelectionService selectionService) {
    Database database = databaseService.getDatabase();

    createTreeViewer(parent, database, menuService, selectionService);
  }

  private void createTreeViewer(Composite parent, Database database, EMenuService menuService,
      ESelectionService selectionService) {
    TreeViewer treeViewer = TreeViewerSWTFactory.fillDefaults(parent, database)
        .customizeLabelDecorator(new ILabelDecorator() {

          @Override
          public void removeListener(ILabelProviderListener listener) {

        }

          @Override
          public boolean isLabelProperty(Object element, String property) {
            return false;
          }

          @Override
          public void dispose() {}

          @Override
          public void addListener(ILabelProviderListener listener) {}

          @Override
          public String decorateText(String text, Object element) {
            // if (dirtyTreeElements.contains(element)) {
            // return "*" + text;
            // }
            return text;
          }

          @Override
          public Image decorateImage(Image image, Object element) {
            return null;
          }
        }).customizeContentProvider(new ITreeContentProvider() {

          @Override
          public boolean hasChildren(Object element) {
            if (element instanceof Database) {
              return ((Database) element).getCellGroups().isEmpty() == false;
            } else if (element instanceof CellGroup) {
              return ((CellGroup) element).getCellResults().isEmpty() == false;
            } else if (element instanceof CellResult) {
              if (((CellResult) element).getDarkMeasuremenetDataSet() != null) {
                return true;
              } else if (((CellResult) element).getLightMeasurementDataSet() != null) {
                return true;
              } else {
                return false;
              }
            } else if (element instanceof CellMeasurementDataSet) {
              return false;
            }
            return false;
          }

          @Override
          public Object getParent(Object element) {
            return ((EObject) element).eContainer();
          }

          @Override
          public Object[] getElements(Object inputElement) {
            Database database = (Database) inputElement;
            return database.getCellGroups().toArray();
          }

          @Override
          public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof CellGroup) {
              return ((CellGroup) parentElement).getCellResults().toArray();
            } else if (parentElement instanceof CellResult) {
              CellResult result = (CellResult) parentElement;
              if (result.getDarkMeasuremenetDataSet() != null
                  && result.getLightMeasurementDataSet() != null) {
                return new Object[] {result.getLightMeasurementDataSet(),
                    result.getDarkMeasuremenetDataSet()};
              } else if (result.getDarkMeasuremenetDataSet() == null) {
                return new Object[] {result.getLightMeasurementDataSet()};
              } else if (result.getLightMeasurementDataSet() == null) {
                return new Object[] {result.getDarkMeasuremenetDataSet()};
              }
            }
            return new Object[] {};
          }
        }).create();


    CommandStack commandStack =
        AdapterFactoryEditingDomain.getEditingDomainFor(database).getCommandStack();
    commandStack.addCommandStackListener(new CommandStackListener() {

      @Override
      public void commandStackChanged(EventObject event) {
        treeViewer.refresh();
      }
    });

    menuService.registerContextMenu(treeViewer.getControl(),
        "at.sunplugged.celldatabasev2.rcp.main.popupmenu.treemenu");

    treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        selectionService.setSelection(
            selection.size() == 1 ? selection.getFirstElement() : selection.toArray());
      }
    });
  }

  @Persist
  public void save() {

  }

}
