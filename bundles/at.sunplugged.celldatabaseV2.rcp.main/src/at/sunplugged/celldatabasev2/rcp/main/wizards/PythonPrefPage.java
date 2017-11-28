package at.sunplugged.celldatabasev2.rcp.main.wizards;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.FileUtils;
import at.sunplugged.celldatabaseV2.common.PythonSettings;

public class PythonPrefPage extends WizardPage {

	private static final Logger LOG = LoggerFactory.getLogger(PythonPrefPage.class);

	private static final String TITLE = "Python Settings";

	private static final String DESCRIPTION = "Configure python related settings...";

	private final IEclipsePreferences prefsPython;

	private FileFieldEditor pythonPath;

	private FileFieldEditor labviewImportPath;

	private FileFieldEditor plotScriptPath;

	protected PythonPrefPage(IEclipsePreferences prefsPython) {
		super(TITLE);
		setTitle(TITLE);
		setDescription(DESCRIPTION);

		this.prefsPython = prefsPython;
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		pythonPath = new FileFieldEditor("Python Path", "Choose python anaconda3 path.", false,
				StringButtonFieldEditor.VALIDATE_ON_FOCUS_LOST, composite);
		pythonPath.getTextControl(composite).addModifyListener(new FieldNotEmptyListener());

		labviewImportPath = new FileFieldEditor("Labview Import Path", "Labview Import Script path", false,
				StringButtonFieldEditor.VALIDATE_ON_FOCUS_LOST, composite);
		labviewImportPath.getTextControl(composite).addModifyListener(new FieldNotEmptyListener());

		plotScriptPath = new FileFieldEditor("PlotScript Path", "PlotScript path", false,
				StringButtonFieldEditor.VALIDATE_ON_FOCUS_LOST, composite);
		plotScriptPath.getTextControl(composite).addModifyListener(new FieldNotEmptyListener());

		pythonPath.setStringValue(prefsPython.get(PythonSettings.PYTHON_PATH, ""));
		pythonPath.setStringValue("");
		try {

			labviewImportPath.setStringValue(FileUtils.locateRootFile("python/main.py").getAbsolutePath());
		} catch (IOException e) {
			LOG.debug("Failed to find standard main.py", e);
			labviewImportPath.setStringValue("");
		}

		try {
			plotScriptPath.setStringValue(FileUtils.locateRootFile("python/plotScript.py").getAbsolutePath());
		} catch (IOException e) {
			LOG.debug("Failed to find standard plotScript.py", e);
			plotScriptPath.setStringValue("");
		}

		setControl(composite);
		setPageComplete(allFieldsSet());

	}

	public Map<String, String> getProvideSettings() {
		Map<String, String> values = new HashMap<>(3);
		values.put(PythonSettings.PYTHON_PATH, pythonPath.getStringValue());
		values.put(PythonSettings.LABVIEW_IMPORT_SCRIPT_PATH, labviewImportPath.getStringValue());
		values.put(PythonSettings.PLOT_SCRIPT_PATH, plotScriptPath.getStringValue());

		return values;

	}

	private final class FieldNotEmptyListener implements ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {
			if (allFieldsSet() == false) {
				setPageComplete(false);
			} else {
				setPageComplete(true);
			}

		}
	}

	private boolean allFieldsSet() {
		if (pythonPath.getStringValue().isEmpty() == true) {
			return false;
		} else if (labviewImportPath.getStringValue().isEmpty() == true) {
			return false;
		} else if (plotScriptPath.getStringValue().isEmpty() == true) {
			return false;
		}
		return true;
	}

}
