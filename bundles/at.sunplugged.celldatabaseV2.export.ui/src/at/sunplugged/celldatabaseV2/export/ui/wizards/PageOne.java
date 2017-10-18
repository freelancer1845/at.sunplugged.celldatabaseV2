package at.sunplugged.celldatabaseV2.export.ui.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.spi.swt.treemasterdetail.MenuProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeViewerBuilder;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeViewerSWTFactory;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import datamodel.CellGroup;
import datamodel.CellResult;
import datamodel.Database;
import datamodel.DatamodelFactory;

public class PageOne extends WizardPage {

  private static final String PAGE_NAME = "Page One";

  private static final String PAGE_DESCRIPTION = "Page Description";

  private final Database database;

  private EditingDomain editingDomain;

  private CheckboxTreeViewer treeViewer;

  protected PageOne(Database database) {
    super(PAGE_NAME);
    setTitle(PAGE_NAME);
    setDescription(PAGE_DESCRIPTION);

    this.database = database;
    editingDomain = new AdapterFactoryEditingDomain(
        new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
        new BasicCommandStack());
    database.getCellGroups().removeIf(group -> group.getCellResults().isEmpty() == true);
    editingDomain.createResource("tempResource").getContents().add(this.database);
  }

  public Database getReducedDatabase() {
    List<EObject> toRemove = new ArrayList<>();
    EcoreUtil.getAllContents(database, true).forEachRemaining(object -> {
      if (object instanceof CellGroup || object instanceof CellResult) {
        if (treeViewer.getChecked(object) == false) {
          toRemove.add((EObject) object);
        }
      }
    });

    EcoreUtil.removeAll(toRemove);

    return database;
  }

  @Override
  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NONE);

    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    layout.makeColumnsEqualWidth = false;

    container.setLayout(layout);

    treeViewer = (CheckboxTreeViewer) TreeViewerSWTFactory.fillDefaults(container, database)
        .customizeTree(new TreeViewerBuilder() {

          @Override
          public TreeViewer createTree(Composite parent) {
            TreeViewer treeViewer = new CheckboxTreeViewer(parent);
            treeViewer.setAutoExpandLevel(2);
            return treeViewer;
          }
        }).customizeContentProvider(new ITreeContentProvider() {
          @Override
          public boolean hasChildren(Object element) {
            if (element instanceof Database) {
              return ((Database) element).getCellGroups().isEmpty() == false;
            } else if (element instanceof CellGroup) {
              return ((CellGroup) element).getCellResults().isEmpty() == false;
            } else if (element instanceof CellResult) {
              return false;
            }
            return false;
          }

          @Override
          public Object getParent(Object element) {
            if (element instanceof Database) {
              return null;
            } else if (element instanceof CellGroup) {
              return database;
            } else if (element instanceof CellResult) {
              return database.getCellGroups().stream()
                  .filter(cellGroup -> cellGroup.getCellResults().contains(element)).findAny()
                  .orElse(null);
            }
            return false;
          }

          @Override
          public Object[] getElements(Object inputElement) {

            return ((Database) inputElement).getCellGroups().toArray();
          }

          @Override
          public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof Database) {
              return ((Database) parentElement).getCellGroups().toArray();
            } else if (parentElement instanceof CellGroup) {
              return ((CellGroup) parentElement).getCellResults().toArray();
            }
            return null;
          };
        }).customizeMenu(createMenu()).create();

    database.getCellGroups().forEach(group -> treeViewer.setSubtreeChecked(group, true));
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


    editingDomain.getCommandStack().addCommandStackListener(new CommandStackListener() {

      @Override
      public void commandStackChanged(EventObject event) {
        treeViewer.refresh();
      }
    });

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
                Command cmd = AddCommand.create(editingDomain, database, null,
                    DatamodelFactory.eINSTANCE.createCellGroup());
                editingDomain.getCommandStack().execute(cmd);
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
                  Command cmd = DeleteCommand.create(editingDomain, selectedElement);
                  editingDomain.getCommandStack().execute(cmd);

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
                  Command cmd = AddCommand.create(editingDomain,
                      ((ITreeContentProvider) treeViewer.getContentProvider())
                          .getParent(selectedElement),
                      null, copy);
                  editingDomain.getCommandStack().execute(cmd);

                }

                public String getText() {
                  return "Clone CellResult";
                }
              };
              Action deleteCellResult = new Action() {
                public void run() {
                  Command cmd = DeleteCommand.create(editingDomain, selectedElement);
                  editingDomain.getCommandStack().execute(cmd);
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

