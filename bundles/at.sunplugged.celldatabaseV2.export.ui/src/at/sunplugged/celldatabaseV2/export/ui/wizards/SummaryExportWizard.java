package at.sunplugged.celldatabaseV2.export.ui.wizards;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import at.sunplugged.celldatabaseV2.export.api.ExcelOutputHelper;
import datamodel.Database;

public class SummaryExportWizard extends Wizard {

  private static final String WINDOW_TITLE = "Summary Export Wizard";

  private final Database database;

  protected PageOne pageOne;

  public SummaryExportWizard(Database database) {
    super();

    this.database = EcoreUtil.copy(database);
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    pageOne = new PageOne(database);

    addPage(pageOne);
  }

  @Override
  public String getWindowTitle() {
    return WINDOW_TITLE;
  }

  @Override
  public boolean performFinish() {
    FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
    dialog.setFilterExtensions(new String[] {"*.xlsx"});
    dialog.setFilterNames(new String[] {"Ms Excel Open XML Format Spreadsheet"});

    if (dialog.open() != null) {
      Path filePath = Paths.get(dialog.getFilterPath(), dialog.getFileName());
      ExcelOutputHelper helper =
          new ExcelOutputHelper(pageOne.getReducedDatabase().getCellGroups(), filePath);
      helper.execute();

    }

    return true;
  }

}

