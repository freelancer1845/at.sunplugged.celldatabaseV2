package at.sunplugged.celldatabasev2.rcp.main.wizards;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.osgi.service.prefs.BackingStoreException;
import at.sunplugged.celldatabaseV2.common.GeneralSettings;
import at.sunplugged.celldatabaseV2.common.LocationsGeneral;
import at.sunplugged.celldatabaseV2.common.PrefNodes;

public class StartupWizard extends Wizard {

  private PythonPrefPage pythonPrefPage;

  private DatabasePage databasePage;

  private boolean firstApplicationStart = true;

  private IEclipsePreferences prefsGeneral;

  private IEclipsePreferences prefsPython;

  private IEclipsePreferences prefsLocations;

  public StartupWizard() {
    super();
    prefsGeneral = ConfigurationScope.INSTANCE.getNode(PrefNodes.GENERAL);
    prefsPython = ConfigurationScope.INSTANCE.getNode(PrefNodes.PYTHON);
    prefsLocations = ConfigurationScope.INSTANCE.getNode(PrefNodes.LOCATIONS);

    String firstStart = prefsGeneral.get(GeneralSettings.FIRST_START, "true");
    firstApplicationStart = Boolean.valueOf(firstStart);
    prefsGeneral.put(GeneralSettings.FIRST_START, "false");

  }

  @Override
  public void addPages() {
    if (firstApplicationStart == true) {
      pythonPrefPage = new PythonPrefPage(prefsPython);
      addPage(pythonPrefPage);
    }

    databasePage = new DatabasePage(prefsLocations);
    addPage(databasePage);

  }

  @Override
  public boolean performFinish() {
    if (pythonPrefPage != null) {
      pythonPrefPage.getProvideSettings()
          .entrySet()
          .stream()
          .forEach(entry -> prefsPython.put(entry.getKey(), entry.getValue()));
    }

    prefsLocations.put(LocationsGeneral.DATABASE_FILE_XMI, databasePage.getDatabasePath());

    try {
      prefsPython.flush();
      prefsGeneral.flush();
      prefsLocations.flush();
    } catch (BackingStoreException e) {

      MessageDialog.openError(getShell(), "Error...", "Failed to store settings... exiting!");
      return false;
    }
    return true;
  }

}
