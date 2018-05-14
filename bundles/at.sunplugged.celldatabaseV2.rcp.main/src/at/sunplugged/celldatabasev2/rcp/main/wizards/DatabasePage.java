package at.sunplugged.celldatabasev2.rcp.main.wizards;

import java.util.ArrayDeque;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import at.sunplugged.celldatabaseV2.common.LocationsGeneral;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.common.settings.SettingsAccessor;

public class DatabasePage extends WizardPage {

  private static final String TITLE = "Database Page";
  private static final String DESCRIPTION = "Choose database file.";
  private BooleanFieldEditor useDefaultFieldEditor;
  private FileFieldEditor fileFieldEditor;


  protected DatabasePage() {
    super(TITLE);
    setTitle(TITLE);
    setDescription(DESCRIPTION);
    setPageComplete(true);
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
        .setToolTipText("If the fiel dose not exist, a new one is created.");
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


    createRecentDatabasesGroup(container);

    setControl(container);


  }


  public Group createRecentDatabasesGroup(Composite parent) {
    Group group = new Group(parent, SWT.NONE);
    group.setText("Recent Databases");
    group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

    Set<String> recentDatabases = SettingsAccessor.getInstance().getSettings().getRecentDatabases();

    if (recentDatabases.size() == 0) {
      group.setVisible(false);
    }

    group.setLayout(new GridLayout(2, false));

    recentDatabases.stream().collect(Collectors.toCollection(ArrayDeque::new)).descendingIterator()
        .forEachRemaining(database -> addDatabaseEntry(group, database));


    return group;
  }

  public void addDatabaseEntry(Group group, String database) {
    Link link = new Link(group, SWT.UNDERLINE_SINGLE);
    link.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    link.setText("<a>" + database + "</a>");
    link.setData(database);

    link.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        fileFieldEditor.setStringValue((String) link.getData());
      }
    });

    Link dlink = new Link(group, SWT.UNDERLINE_SINGLE);
    dlink.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    dlink.setText("<a>Remove</a>");
    dlink.setToolTipText("Remove this entry from recent databases");
    dlink.setData(database);
    dlink.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        SettingsAccessor.getInstance().getSettings().getRecentDatabases().remove(dlink.getData());
        link.setVisible(false);
        dlink.setVisible(false);
        SettingsAccessor.getInstance().flushSettingsIgnore();
      }
    });

  }


  public String getDatabasePath() {
    return fileFieldEditor.getStringValue();
  }
}
