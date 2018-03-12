package at.sunplugged.celldatabaseV2.labviewimport.ui.wizard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeViewerSWTFactory;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.labviewimport.LabviewCalculationException;
import at.sunplugged.celldatabaseV2.labviewimport.LabviewCalculator;
import at.sunplugged.celldatabaseV2.labviewimport.LabviewDataFile;
import at.sunplugged.celldatabaseV2.plotting.PlotDialog;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.util.DatamodelUtils;

public class PageTwo extends WizardPage {

  private static final Logger LOG = LoggerFactory.getLogger(PageTwo.class);

  private static final String TITLE = "Review Result...";

  private static final String DESCRIPTION =
      "Labview Import Wizard: Check caculation results and confirm";

  private List<LabviewDataFile> dataFiles;

  private CellGroup tempGroup = DatamodelFactory.eINSTANCE.createCellGroup();

  private TreeViewer treeViewer;

  private boolean calculationDone = false;

  private CLabel cancleLabel;

  private Composite container;

  protected PageTwo(List<LabviewDataFile> dataFiles) {
    super(TITLE);
    this.dataFiles = dataFiles;
    setTitle(TITLE);
    setDescription(DESCRIPTION);
    EditingDomain editingDomain = new AdapterFactoryEditingDomain(
        new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
        new BasicCommandStack());
    editingDomain.createResource("tempResource").getContents().add(tempGroup);
  }


  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.makeColumnsEqualWidth = false;

    container.setLayout(new StackLayout());
    tempGroup.setName("LabviewImportGroup");
    treeViewer = TreeViewerSWTFactory.fillDefaults(container, tempGroup).create();

    cancleLabel = new CLabel(container, SWT.NONE);
    cancleLabel.setText("Evaluation Cancled.");
    cancleLabel.setImage(Display.getDefault().getSystemImage(SWT.ICON_ERROR));
    cancleLabel.pack();
    treeViewer.addDoubleClickListener(new IDoubleClickListener() {

      @Override
      public void doubleClick(DoubleClickEvent event) {
        Object selectedElement = (EObject) treeViewer.getStructuredSelection().getFirstElement();
        if (selectedElement instanceof CellResult == false
            && selectedElement instanceof CellMeasurementDataSet == false) {
          return;
        }

        new TitleAreaDialog(container.getShell()) {

          public void create() {
            super.create();
            String title = null;
            if (selectedElement instanceof EObject) {
              EObject eObject = (EObject) selectedElement;
              EAttribute attribute = eObject.eClass().getEAttributes().stream()
                  .filter(attr -> attr.getName() == "name").findFirst().orElse(null);
              if (attribute != null) {
                title = (String) eObject.eGet(attribute);
              }

            }
            if (title == null) {
              title = "Editor";
            }

            setTitle(title);
            setMessage("Review and change CellResult", IMessageProvider.INFORMATION);
          };

          protected boolean isResizable() {
            return true;
          };

          @Override
          protected Control createDialogArea(Composite parent) {
            Composite marginComposite = new Composite(parent, SWT.BORDER);
            marginComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 10;
            fillLayout.marginWidth = 10;
            marginComposite.setLayout(fillLayout);
            ScrolledComposite composite =
                new ScrolledComposite(marginComposite, SWT.V_SCROLL | SWT.H_SCROLL);
            // Composite composite = new Composite(parent, SWT.BORDER);

            composite.setExpandHorizontal(true);
            composite.setExpandVertical(true);
            composite.setMinSize(250, 250);
            FillLayout fillLayout2 = new FillLayout();
            fillLayout2.marginHeight = 10;
            fillLayout2.marginWidth = 10;
            composite.setLayout(fillLayout2);

            composite.addListener(SWT.Resize, event -> {
              int width = composite.getClientArea().width;
              composite.setMinSize(parent.computeSize(width, SWT.DEFAULT));
            });
            try {
              ECPSWTView view =
                  ECPSWTViewRenderer.INSTANCE.render(composite, (EObject) selectedElement);
              composite.setContent(view.getSWTControl());
            } catch (ECPRendererException e) {
              LOG.error("Failed to render edit view...", e);
            }

            return composite;
          }
        }.open();
      }
    });
    treeViewer.getControl().setEnabled(true);
    treeViewer.getTree().pack();
    setControl(container);

  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    if (visible == true) {
      calculateResults();
    }
  }

  private boolean confirmHeper;

  private boolean cancleEvaluation = false;

  private void calculateResults() {
    cancleEvaluation = false;
    tempGroup.getCellResults().clear();

    Job job = Job.create("Calculating results..", (ICoreRunnable) monitor -> {
      final List<CellResult> results = new ArrayList<>();

      List<LabviewDataFile> filteredFiles = dataFiles.stream().filter(file -> {
        if (file.getArea() == null) {
          Display.getDefault().syncExec(() -> {
            MessageDialog.openError(getShell(), "Error", "No area provided for " + file.getName());
          });
          return false;
        }
        if (file.getPowerInput() == null) {
          Display.getDefault().syncExec(() -> {
            MessageDialog.openError(getShell(), "Error",
                "No powerInput provided for " + file.getName());
          });
          return false;
        }
        return true;
      }).collect(Collectors.toList());

      if (filteredFiles.size() < dataFiles.size()) {
        confirmHeper = false;
        Display.getDefault().syncExec(() -> {
          confirmHeper = MessageDialog.openQuestion(getShell(), "Continue?",
              "Some labview files where filtered. Continue?");
        });

        if (confirmHeper == false) {
          Display.getDefault().asyncExec(() -> {
            getWizard().getContainer().showPage(getPreviousPage());
          });
          return;
        }
      }

      Map<LabviewCalculator, IOException> calculators = new HashMap<>();
      filteredFiles.stream().anyMatch(file -> {
        LabviewCalculator calculator = new LabviewCalculator();
        try {
          calculators.put(calculator, null);
          results.add(calculator.evaluate(file, false));
        } catch (IOException e) {
          calculators.put(calculator, e);
          LOG.error("Failed to calculate labview files.", e);
          Display.getDefault().syncExec(() -> {
            MessageDialog.openError(getShell(), "Error",
                "Failed to load labview data for file: " + file.getName() + ".\n" + e.getMessage());
          });
        } catch (LabviewCalculationException e) {
          LOG.error("Failed to calculate labview files.", e);
          Display.getDefault().syncExec(() -> {
            MessageDialog.openError(getShell(), "Error",
                "Failed to calculate labview data.\n" + e.getMessage());


            while (true) {
              int answer = MessageDialog.open(MessageDialog.QUESTION, getShell(),
                  "Automatic Calculation failed.",
                  "Automatic calculation failed.\nChoose what to do.", SWT.NONE,
                  new String[] {"See Data", "Select Fit Ranges", "Exclude", "Cancle Evaluation"});

              if (answer == 0) {
                new PlotDialog(getShell(),
                    Arrays.asList(calculator.getResult().getLightMeasurementDataSet(),
                        calculator.getResult().getDarkMeasuremenetDataSet())).open();
              } else if (answer == 1) {
                try {
                  calculator.reEvaluate(calculator.getResult(), true);
                  results.add(calculator.getResult());
                } catch (LabviewCalculationException e1) {
                  MessageDialog.openError(getShell(), "Error",
                      "Calculation failed again.\n" + e1.getMessage());
                }
              } else if (answer == 2) {
                // effectivley do nothing
                break;
              } else if (answer == 3) {
                cancleEvaluation = true;
                break;
              } else {
                break;
              }
            }

          });
        } catch (Exception e) {
          LOG.error("Unexpected exception while processing labview files.", e);
          Display.getDefault().syncExec(() -> {
            MessageDialog.openError(getShell(), "Error",
                "Unexpected exception processing data.\n" + e.getMessage());
          });
        }
        return cancleEvaluation;
      });



      List<CellResult> sortedResults =
          results.stream().sorted(DatamodelUtils.Comparetors.comapreCellResultsNatural())
              .collect(Collectors.toList());
      tempGroup.getCellResults().addAll(sortedResults);
      calculationDone = !cancleEvaluation;
      Display.getDefault().asyncExec(() -> {
        if (cancleEvaluation == true) {
          ((StackLayout) container.getLayout()).topControl = cancleLabel;
        } else {
          ((StackLayout) container.getLayout()).topControl = treeViewer.getTree();
        }
        container.layout();
        getWizard().getContainer().updateButtons();
      });

    });

    job.setPriority(Job.LONG);
    job.schedule();

  }

  public boolean isCalculationDone() {
    return calculationDone;
  }

  public EList<CellResult> getCellResults() {
    return tempGroup.getCellResults();
  }

}

