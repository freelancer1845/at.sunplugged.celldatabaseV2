package at.sunplugged.celldatabaseV2.export.ui.wizards;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.MenuProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import datamodel.CellGroup;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.provider.DatamodelItemProviderAdapterFactory;

public class PageOne extends WizardPage {

  private static final String PAGE_NAME = "Page One";

  private static final String PAGE_DESCRIPTION = "Page Description";

  private final List<CellGroup> groups;

  private EditingDomain editingDomain;

  private CheckboxTreeViewer treeViewer;

  protected PageOne(List<CellGroup> groups) {
    super(PAGE_NAME);
    setTitle(PAGE_NAME);
    setDescription(PAGE_DESCRIPTION);

    this.groups = groups;
    editingDomain = new AdapterFactoryEditingDomain(
        new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
        new BasicCommandStack());
    groups.removeIf(group -> group.getCellResults().isEmpty() == true);
  }

  public List<CellGroup> getReducedDatabase() {
    return groups.stream().filter(group -> {
      group.getCellResults().removeIf(result -> treeViewer.getChecked(result) == false);

      if (treeViewer.getChecked(group) == false) {
        return false;
      } else if (group.getCellResults().size() == 0) {
        return false;
      } else {
        return true;
      }
    }).collect(Collectors.toList());
  }

  @Override
  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NONE);

    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    layout.makeColumnsEqualWidth = false;

    container.setLayout(layout);


    treeViewer = new CheckboxTreeViewer(container);
    treeViewer.setAutoExpandLevel(2);
    treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));


    treeViewer.setContentProvider(new ITreeContentProvider() {
      @Override
      public boolean hasChildren(Object element) {
        if (element instanceof CellResult) {
          return false;
        } else {
          return true;
        }
      }

      @Override
      public Object getParent(Object element) {
        if (element instanceof CellResult) {
          return ((CellResult) element).eContainer();
        }
        {
          return null;
        }
      }

      @SuppressWarnings("unchecked")
      @Override
      public Object[] getElements(Object inputElement) {

        return ((List<CellGroup>) inputElement).toArray();
      }

      @Override
      public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof CellGroup) {
          return ((CellGroup) parentElement).getCellResults().toArray();
        }
        return null;
      }
    });

    treeViewer.getTree().setMenu(createMenu().getMenu(treeViewer, editingDomain));



    groups.forEach(group -> treeViewer.setSubtreeChecked(group, true));
    treeViewer.addCheckStateListener(new ICheckStateListener() {

      @Override
      public void checkStateChanged(CheckStateChangedEvent event) {
        CheckboxTreeViewer treeViewer = (CheckboxTreeViewer) event.getSource();
        Object element = event.getElement();
        if (element instanceof CellGroup) {
          treeViewer.setSubtreeChecked(element, event.getChecked());
          treeViewer.setGrayed(element, false);
        } else if (element instanceof CellResult) {
          ITreeContentProvider provider = (ITreeContentProvider) treeViewer.getContentProvider();
          Object parent = provider.getParent(element);
          if (event.getChecked() == true) {
            if (Arrays.stream(provider.getChildren(parent))
                .allMatch(child -> treeViewer.getChecked(child))) {
              treeViewer.setGrayed(parent, false);
            } else {
              treeViewer.setGrayChecked(parent, true);
            }
          } else {
            if (Arrays.stream(provider.getChildren(parent))
                .anyMatch(child -> treeViewer.getChecked(child))) {
              treeViewer.setGrayed(parent, true);
            } else {
              treeViewer.setChecked(parent, false);
            }
          }

        }
        if (treeViewer.getCheckedElements().length == 0) {
          setPageComplete(false);
        } else {
          setPageComplete(true);
        }
      }
    });

    treeViewer.setCheckStateProvider(new ICheckStateProvider() {

      @Override
      public boolean isChecked(Object element) {
        ITreeContentProvider provider = (ITreeContentProvider) treeViewer.getContentProvider();
        if (provider.hasChildren(element)) {
          return Arrays.stream(provider.getChildren(element))
              .anyMatch(child -> treeViewer.getChecked(child));
        } else {
          return treeViewer.getChecked(element);
        }
      }

      @Override
      public boolean isGrayed(Object element) {
        ITreeContentProvider provider = (ITreeContentProvider) treeViewer.getContentProvider();
        if (provider.hasChildren(element)) {
          return Arrays.stream(provider.getChildren(element))
              .anyMatch(child -> treeViewer.getChecked(child) == false);

        } else {
          return treeViewer.getGrayed(element);
        }

      }

    });
    AdapterFactory adapterFactory = new DatamodelItemProviderAdapterFactory();
    AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(adapterFactory);
    treeViewer.setLabelProvider(labelProvider);


    // editingDomain.getCommandStack().addCommandStackListener(new CommandStackListener() {
    //
    // @Override
    // public void commandStackChanged(EventObject event) {
    // treeViewer.refresh();
    // }
    // });

    treeViewer.setInput(groups);

    setControl(container);
    setPageComplete(treeViewer.getCheckedElements().length != 0);

  }

  private MenuProvider createMenu() {

    return new MenuProvider() {

      @Override
      public Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain) {
        MenuManager menuManager = new MenuManager();
        menuManager.setRemoveAllWhenShown(true);
        menuManager.addMenuListener(new IMenuListener() {

          @Override
          public void menuAboutToShow(IMenuManager manager) {
            Action addCellGroup = new Action() {
              @Override
              public void run() {
                groups.add(DatamodelFactory.eINSTANCE.createCellGroup());
                treeViewer.refresh();
              }

              @Override
              public String getText() {
                return "Add Cell Group";
              }

            };
            manager.add(addCellGroup);

            Object selectedElement = treeViewer.getStructuredSelection().getFirstElement();
            if (selectedElement instanceof CellGroup) {

              Action deleteCellGroup = new Action() {
                public void run() {

                  groups.remove(selectedElement);
                  treeViewer.refresh();
                }

                public String getText() {
                  return "Delete Cell Group";
                }
              };
              manager.add(deleteCellGroup);

            } else if (selectedElement instanceof CellResult) {
              Action cloneCellResult = new Action() {
                public void run() {
                  CellResult copy = (CellResult) EcoreUtil.copy((EObject) selectedElement);
                  ((CellGroup) copy.eContainer()).getCellResults().add(copy);
                  treeViewer.refresh();
                }

                public String getText() {
                  return "Clone CellResult";
                }
              };
              Action deleteCellResult = new Action() {
                public void run() {
                  CellResult result = (CellResult) selectedElement;
                  ((CellGroup) result.eContainer()).getCellResults().remove(result);
                  treeViewer.refresh();
                }

                public String getText() {
                  return "Delete CellResult";
                }
              };

              manager.add(cloneCellResult);
              manager.add(deleteCellResult);
            }

          }
        });
        return menuManager.createContextMenu(treeViewer.getControl());
      }

    };
  }

}

