package at.sunplugged.celldatabasev2.rcp.main.wizards;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import at.sunplugged.celldatabaseV2.common.LocationsGeneral;
import at.sunplugged.celldatabaseV2.common.PrefNodes;

public class DatabasePage extends WizardPage {

  private static final String TITLE = "Database Page";
  private static final String DESCRIPTION = "Choose database file.";
  private BooleanFieldEditor useDefaultFieldEditor;
  private FileFieldEditor fileFieldEditor;
  private final IEclipsePreferences prefsLocations;


  protected DatabasePage(IEclipsePreferences prefsLocations) {
    super(TITLE);
    setTitle(TITLE);
    setDescription(DESCRIPTION);
    setPageComplete(true);
    this.prefsLocations = prefsLocations;
  }

  @Override
  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NONE);
    container.setLayout(new GridLayout(1, false));

    useDefaultFieldEditor =
        new BooleanFieldEditor("Use Default", "Use default database", container);

    useDefaultFieldEditor.setPropertyChangeListener(new IPropertyChangeListener() {

      String fileEditorLastValue = "";

      @Override
      public void propertyChange(PropertyChangeEvent event) {
        if (useDefaultFieldEditor.getBooleanValue() == true) {
          fileFieldEditor.setEnabled(false, container);
          fileEditorLastValue = fileFieldEditor.getStringValue();
          fileFieldEditor.setStringValue("database/Database.xmi");
        } else {
          fileFieldEditor.setEnabled(true, container);
          fileFieldEditor.setStringValue(fileEditorLastValue);
        }
      }
    });



    fileFieldEditor = new FileFieldEditor("Database File", "Database to load", container);
    fileFieldEditor.getTextControl(container)
        .setToolTipText("If the filed dose not exist, a new one is created.");
    fileFieldEditor.setStringValue(ConfigurationScope.INSTANCE.getNode(PrefNodes.LOCATIONS)
        .get(LocationsGeneral.DATABASE_FILE_XMI, "database/Database.xmi"));
    fileFieldEditor.setFileExtensions(new String[] {"*.xmi"});
    fileFieldEditor.getTextControl(container).addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        String text = fileFieldEditor.getStringValue();
        if (text.isEmpty() == false) {
          if (text.endsWith(".xmi") == false) {
            fileFieldEditor.setStringValue(text + ".xmi");
          }
          setPageComplete(true);
        } else {
          setPageComplete(false);
        }
      }
    });

    fileFieldEditor.setStringValue(prefsLocations.get(LocationsGeneral.DATABASE_FILE_XMI, ""));

    setControl(container);


  }


  public String getDatabasePath() {
    return fileFieldEditor.getStringValue();
  }
}
