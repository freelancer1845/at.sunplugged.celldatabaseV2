package at.sunplugged.celldatabaseV2.sprodaccess.ui.wizard.internal;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.ButtonBarBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerFactory;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTBuilder;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.LocationsGeneral;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.sprodaccess.api.SprodAccessException;
import at.sunplugged.celldatabaseV2.sprodaccess.api.SprodAccessService;
import datamodel.CellResult;

public class PageOne extends WizardPage {

  private static final Logger LOG = LoggerFactory.getLogger(PageOne.class);

  private static final String TITLE = "Choose Sprod File";

  private static final String DESCRIPTION = "Choose Sprod Microsoft Access Database file...";

  private final SprodAccessService sprodAccessService;

  private Text txtSprodFile;

  private Button btnSprodFile;

  private Button btnImport;

  private List<CellResult> sprodResults = new ArrayList<>();

  private TableViewer viewer;



  public PageOne(SprodAccessService sprodAccessService) {
    super(TITLE);
    setTitle(TITLE);
    setDescription(DESCRIPTION);
    this.sprodAccessService = sprodAccessService;
  }

  @Override
  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NONE);

    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.makeColumnsEqualWidth = false;
    container.setLayout(layout);


    txtSprodFile = new Text(container, SWT.BORDER | SWT.SINGLE);
    txtSprodFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    String initialLocation = ConfigurationScope.INSTANCE.getNode(PrefNodes.LOCATIONS)
        .get(LocationsGeneral.SPROD_FILE, "");
    txtSprodFile.setText(initialLocation);

    btnSprodFile = new Button(container, SWT.PUSH);
    btnSprodFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    btnSprodFile.setText("Choose file...");
    btnSprodFile.addSelectionListener(new BtnSprodFileSelectionListener());


    btnImport = new Button(container, SWT.PUSH);
    btnImport.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    btnImport.setText("Import");
    btnImport.addSelectionListener(new BtnImportSelectionListener());


    TableViewerSWTBuilder builder = TableViewerFactory.fillDefaults(container, SWT.NONE,
        Observables.staticObservableList(sprodResults));

    builder.customizeButtons(new ButtonBarBuilder() {

      @Override
      public void fillButtonComposite(Composite composite, AbstractTableViewer viewer) {}

      @Override
      public Object createNewElement(Button button) {
        return null;
      }
    });
    builder.customizeCompositeStructure(new TableViewerCompositeBuilder() {

      private Composite viewerComposite;

      @Override
      public void createCompositeLayout(Composite parent) {
        GridLayoutFactory.fillDefaults().numColumns(1).applyTo(parent);
        /* Overall Layout */
        final Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        composite.setLayout(new GridLayout(1, false));


        /* Bottom composite */
        viewerComposite = createViewerComposite(composite);
      }



      /**
       * Called to create the composite for the table viewer.
       *
       * @param composite the parent
       * @return the composite
       */
      protected Composite createViewerComposite(final Composite composite) {
        final Composite viewerComposite = new Composite(composite, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL)
            .applyTo(viewerComposite);
        GridLayoutFactory.fillDefaults().numColumns(1).applyTo(viewerComposite);
        return viewerComposite;
      }


      @Override
      public Optional<Label> getTitleLabel() {
        return Optional.empty();
      }

      @Override
      public Optional<List<Control>> getValidationControls() {
        return Optional.empty();
      }

      @Override
      public Optional<Composite> getButtonComposite() {
        return Optional.empty();
      }

      @Override
      public Composite getViewerComposite() {
        return viewerComposite;
      }

    });

    builder.addColumn("Id", "Id of the Sprod as saved in database", new CellLabelProvider() {

      @Override
      public void update(ViewerCell cell) {
        CellResult result = (CellResult) cell.getElement();
        cell.setText(result.getName());
      }
    });
    builder.addColumn("Area", "Area of the Cell in mm^2", new CellLabelProvider() {
      @Override
      public void update(ViewerCell cell) {
        CellResult result = (CellResult) cell.getElement();
        cell.setText(String.valueOf(result.getLightMeasurementDataSet().getArea()));
      }
    });
    builder.addColumn("Eff", "Efficency of the Cell %", new CellLabelProvider() {

      @Override
      public void update(ViewerCell cell) {
        CellResult result = (CellResult) cell.getElement();
        cell.setText(String.valueOf(result.getEfficiency()));
      }
    });
    AbstractTableViewerComposite abstractViewerComposite = builder.create();
    abstractViewerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    viewer = (TableViewer) abstractViewerComposite.getTableViewer();
    setControl(container);
    setPageComplete(false);
  }


  private final class BtnSprodFileSelectionListener extends SelectionAdapter {
    @Override
    public void widgetSelected(SelectionEvent e) {
      FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);

      if (dialog.open() != null) {
        txtSprodFile.setText(Paths.get(dialog.getFilterPath(), dialog.getFileName()).toString());
      }

    }
  }

  private final class BtnImportSelectionListener extends SelectionAdapter {
    @Override
    public void widgetSelected(SelectionEvent e) {
      try {
        sprodResults.clear();
        sprodAccessService.openFile(new File(txtSprodFile.getText()));
        // sprodResults.addAll(sprodAccessService.getByIds(new int[] {560, 561, 563, 564}));
        sprodResults.addAll(sprodAccessService.getAll());
        sprodAccessService.close();
        viewer.refresh();
        setPageComplete(true);
      } catch (SprodAccessException e1) {
        LOG.error(String.format("Failed to open sprod access file \"%s\"", txtSprodFile.getText()),
            e1);
      }

    }


  }

  public List<CellResult> getSprodResults() {
    return sprodResults;
  }


}

