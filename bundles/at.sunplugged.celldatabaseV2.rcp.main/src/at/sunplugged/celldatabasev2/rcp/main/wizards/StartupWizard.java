package at.sunplugged.celldatabasev2.rcp.main.wizards;

import org.eclipse.jface.wizard.Wizard;

public class StartupWizard extends Wizard {


  private DatabasePage databasePage;

  private String databasePath;


  public StartupWizard() {
    super();


  }

  @Override
  public void addPages() {
    databasePage = new DatabasePage();
    addPage(databasePage);

  }

  @Override
  public boolean performFinish() {
    this.databasePath = databasePage.getDatabasePath();
    return true;
  }

  public String getDatabasePath() {
    return this.databasePath;
  }

}
