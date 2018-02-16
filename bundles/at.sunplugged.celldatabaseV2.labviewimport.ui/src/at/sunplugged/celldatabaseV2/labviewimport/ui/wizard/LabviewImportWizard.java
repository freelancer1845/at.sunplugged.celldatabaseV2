package at.sunplugged.celldatabaseV2.labviewimport.ui.wizard;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import at.sunplugged.celldatabaseV2.labviewimport.LabviewDataFile;
import datamodel.CellResult;

public class LabviewImportWizard extends Wizard {

  protected PageOne pageOne;

  protected PageTwo pageTwo;

  private List<LabviewDataFile> dataFiles = new ArrayList<>();

  private EList<CellResult> cellResults;

  public LabviewImportWizard() {
    super();
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    pageOne = new PageOne(dataFiles);
    pageTwo = new PageTwo(dataFiles);
    addPage(pageOne);
    addPage(pageTwo);
  }

  @Override
  public IWizardPage getNextPage(IWizardPage page) {
    if (page == pageTwo) {
      pageTwo.calculateResults();
    }
    return super.getNextPage(page);
  }

  @Override
  public String getWindowTitle() {
    return "Import Labview Data";
  }

  @Override
  public boolean performFinish() {
    cellResults = pageTwo.getCellResults();
    if (cellResults.isEmpty() == true) {
      return false;
    }
    return true;
  }

  public EList<CellResult> getCellResults() {
    return cellResults;
  }

}
