
package at.sunplugged.celldatabasev2.rcp.main.parts;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.DiagnosticDecorator;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeViewerSWTFactory;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseServiceException;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.Database;

public class ModelViewerPart {

  private static final Logger LOG = LoggerFactory.getLogger(ModelViewerPart.class);

  @Inject
  private MDirtyable dirtyable;

  @Inject
  private UISynchronize sync;

  @Inject
  private IEventBroker eventBroker;

  private Map<URI, MPart> createdEditors = new HashMap<>();

  @PostConstruct
  public void postConstruct(Composite parent, Database database, EMenuService menuService,
      ESelectionService selectionService, EPartService partService, EModelService modelService,
      MApplication app, EditingDomain editingDomain) {

    createTreeViewer(parent, database, menuService, selectionService, partService, modelService,
        app, editingDomain);
  }

  private void createTreeViewer(Composite parent, Database database, EMenuService menuService,
      ESelectionService selectionService, EPartService partService, EModelService modelService,
      MApplication app, EditingDomain editingDomain) {

    ComposedAdapterFactory composedAdapterFactory =
        new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

    AdapterFactoryLabelProvider labelProvider =
        new AdapterFactoryLabelProvider(composedAdapterFactory);

    TreeViewer treeViewer = TreeViewerSWTFactory.fillDefaults(parent, database)
        .customizeContentProvider(new ITreeContentProvider() {

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


    treeViewer.setLabelProvider(
        new DecoratingLabelProvider((ILabelProvider) treeViewer.getLabelProvider(),
            new DiagnosticDecorator(editingDomain, treeViewer)));


    treeViewer.setAutoExpandLevel(0);
    treeViewer.collapseAll();


    CommandStack commandStack =
        AdapterFactoryEditingDomain.getEditingDomainFor(database).getCommandStack();
    commandStack.addCommandStackListener(new CommandStackListener() {

      @Override
      public void commandStackChanged(EventObject event) {
        sync.asyncExec(() -> treeViewer.refresh());
        dirtyable.setDirty(true);
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

    treeViewer.addDoubleClickListener(new DoubleClickListener(partService, modelService, app));

  }

  @Persist
  public void save(DatabaseService databaseService) {
    try {
      databaseService.saveDatabase();
      dirtyable.setDirty(false);

    } catch (DatabaseServiceException e) {
      LOG.error("Failed to persit modelviewer part", e);
    }
  }


  private final class DoubleClickListener implements IDoubleClickListener {

    private final EPartService partService;

    private final EModelService modelService;

    private final MApplication app;

    public DoubleClickListener(EPartService partService, EModelService modelService,
        MApplication app) {
      this.partService = partService;
      this.modelService = modelService;
      this.app = app;
    }

    @Override
    public void doubleClick(DoubleClickEvent event) {
      TreeViewer treeViewer = (TreeViewer) event.getViewer();
      Object selectedElement = (EObject) treeViewer.getStructuredSelection().getFirstElement();
      if (selectedElement instanceof EObject == false) {
        return;
      }

      String label = null;
      EObject eObject = (EObject) selectedElement;
      EAttribute attribute = eObject.eClass().getEAttributes().stream()
          .filter(attr -> attr.getName() == "name").findFirst().orElse(null);
      if (attribute != null) {
        label = (String) eObject.eGet(attribute);
      }

      if (label == null) {
        label = "Editor";
      }

      URI uri = EcoreUtil.getURI((EObject) selectedElement);

      MPart editorPart;

      editorPart = createdEditors.get(uri);
      if (editorPart != null) {
        editorPart.getTransientData().put("data", selectedElement);
        partService.showPart(editorPart, PartState.ACTIVATE);
        return;
      }
      editorPart = partService.getParts().stream()
          .filter(part -> part.getElementId()
              .equals("at.sunplugged.celldatabasev2.rcp.main.partdescriptor.modeleditor"))
          .filter(part -> {
            Object partUri = part.getTransientData().get("uri");
            if (partUri != null) {
              return partUri.equals(uri);
            } else {
              return false;
            }
          }).findAny().orElse(null);
      if (editorPart != null) {
        partService.showPart(editorPart, PartState.ACTIVATE);
        return;
      }



      editorPart = partService
          .createPart("at.sunplugged.celldatabasev2.rcp.main.partdescriptor.modeleditor");

      editorPart.setLabel(label);
      editorPart.getTransientData().put("data", selectedElement);

      editorPart.getTransientData().put("uri", uri);

      eObject.eAdapters().add(new EditorLabelAdapter(editorPart, attribute));
      createdEditors.put(uri, editorPart);
      MPartStack partStack =
          (MPartStack) modelService.find("at.sunplugged.celldatabasev2.rcp.main.partstack.1", app);

      partStack.getChildren().add(editorPart);
      partService.showPart(editorPart, PartState.ACTIVATE);

    }

  }

  private final class EditorLabelAdapter extends AdapterImpl {
    private final MPart editorPart;
    private final EAttribute attribute;

    public EditorLabelAdapter(MPart editorPart, EAttribute attribute) {
      super();
      this.editorPart = editorPart;
      this.attribute = attribute;
    }

    @Override
    public void notifyChanged(Notification msg) {
      if (msg.getFeature() != null) {
        if (msg.getFeature().equals(attribute)) {
          String label = msg.getNewStringValue();

          if (label == null) {
            label = "Editor";
          }
          editorPart.setLabel(label);
        }
      }
    }
  }
}
