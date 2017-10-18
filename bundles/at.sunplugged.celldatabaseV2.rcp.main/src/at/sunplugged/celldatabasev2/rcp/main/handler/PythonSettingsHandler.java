
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.PythonSettings;
import at.sunplugged.celldatabasev2.rcp.main.dialogs.PythonSettingsDialog;

public class PythonSettingsHandler {
  private static final Logger LOG = LoggerFactory.getLogger(PythonSettingsHandler.class);

  @Execute
  public void execute(Shell shell) {
    PythonSettingsDialog dialog = new PythonSettingsDialog(shell);
    dialog.setBlockOnOpen(true);
    if (dialog.open() == Window.OK) {
      IEclipsePreferences pref = dialog.getPreferences();

      pref.put(PythonSettings.PYTHON_PATH, dialog.getPythonPathText());
      pref.put(PythonSettings.LABVIEW_IMPORT_SCRIPT_PATH, dialog.getLabViewScriptPathText());
      pref.put(PythonSettings.PLOT_SCRIPT_PATH, dialog.getPlotScriptPathText());
      try {
        pref.flush();
      } catch (BackingStoreException e) {
        LOG.error("Failed to flush settings...", e);
      }
    }
  }

}
