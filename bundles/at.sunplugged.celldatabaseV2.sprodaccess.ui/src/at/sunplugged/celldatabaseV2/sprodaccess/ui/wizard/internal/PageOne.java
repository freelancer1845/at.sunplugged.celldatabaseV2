package at.sunplugged.celldatabaseV2.sprodaccess.ui.wizard.internal;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
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

  private Text txtIds;



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

    Label label = new Label(container, SWT.NONE);
    label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
    label.setText("Set IDs");
    label.setToolTipText("For example: 3,4,75,30-46");

    txtIds = new Text(container, SWT.BORDER | SWT.SINGLE);
    txtIds.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));


    btnImport = new Button(container, SWT.PUSH);
    btnImport.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    btnImport.setText("Import");
    btnImport.addSelectionListener(new BtnImportSelectionListener());
    txtIds.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        String currentText = txtIds.getText();
        try {
          int[] ids = getIdsFromText(currentText);
          btnImport.setEnabled(true);
          if (ids.length == 0) {
            throw new NumberFormatException();
          }
        } catch (NumberFormatException e1) {
          txtIds.setToolTipText("Input not correct...");
          btnImport.setEnabled(false);
        }
      }
    });

    viewer = new TableViewer(container);


    createColumns(container, viewer);

    viewer.setContentProvider(new ArrayContentProvider());
    viewer.getTable().setHeaderVisible(true);
    viewer.getTable().setLinesVisible(true);

    viewer.setInput(sprodResults);

    viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    setControl(container);
    setPageComplete(false);
  }


  private void createColumns(Composite container, TableViewer viewer2) {
    String[] titles = {"Id"};
    String[] toolTips = {"Id as saved in Sprod database"};
    int[] bounds = {100};
    int i = 0;
    TableViewerColumn col = createTableViewerColumn(titles[i], toolTips[i], bounds[i], i);
    col.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        return ((CellResult) element).getName();
      }
    });

  }


  private TableViewerColumn createTableViewerColumn(String title, String tooltip, int bound,
      int colNumber) {
    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
    final TableColumn column = viewerColumn.getColumn();
    column.setText(title);
    column.setToolTipText(tooltip);
    column.setWidth(bound);
    column.setResizable(true);
    column.setMoveable(true);
    return viewerColumn;
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

  private int[] getIdsFromText(String input) {
    String[] elements = input.split(",");
    List<Integer> values = new LinkedList<>();
    for (String element : elements) {
      element.replace(" ", "");
      if (element.contains("-")) {
        String[] startAndEnd = element.split("-");
        if (startAndEnd.length != 2) {
          throw new NumberFormatException();
        }
        int start = Integer.valueOf(startAndEnd[0]);
        int end = Integer.valueOf(startAndEnd[1]);
        for (int i = start; i <= end; i++) {
          values.add(i);
        }
      } else {
        values.add(Integer.valueOf(element));
      }
    }

    return values.stream().mapToInt(value -> value).toArray();
  }

  private final class BtnImportSelectionListener extends SelectionAdapter {
    @Override
    public void widgetSelected(SelectionEvent e) {
      try {
        sprodResults.clear();
        sprodAccessService.openFile(new File(txtSprodFile.getText()));
        sprodResults.addAll(sprodAccessService.getByIds(getIdsFromText(txtIds.getText())));
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

