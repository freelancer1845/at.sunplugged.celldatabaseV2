package at.sunplugged.celldatabaseV2.sprodaccess.ui.wizard;

import java.util.List;
import org.eclipse.jface.wizard.Wizard;
import at.sunplugged.celldatabaseV2.sprodaccess.api.SprodAccessService;
import at.sunplugged.celldatabaseV2.sprodaccess.ui.wizard.internal.PageOne;
import datamodel.CellResult;

public class SprodImportWizard extends Wizard {

  private static final String WINDOW_TITLE = "Sprod Import Wizard";

  private PageOne pageOne;

  private final SprodAccessService sprodAccessService;

  private List<CellResult> cellResults;

  public SprodImportWizard(SprodAccessService sprodAccessService) {
    super();
    this.sprodAccessService = sprodAccessService;
  }

  @Override
  public void addPages() {
    pageOne = new PageOne(sprodAccessService);

    addPage(pageOne);
  }

  @Override
  public String getWindowTitle() {
    return WINDOW_TITLE;
  }

  @Override
  public boolean performFinish() {
    cellResults = pageOne.getSprodResults();
    return true;
  }

  public List<CellResult> getCellResults() {
    return cellResults;
  }

}

