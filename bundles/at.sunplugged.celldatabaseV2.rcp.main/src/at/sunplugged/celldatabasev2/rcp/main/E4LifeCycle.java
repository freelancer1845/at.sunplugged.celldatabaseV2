package at.sunplugged.celldatabasev2.rcp.main;

import java.io.IOException;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.LocationsGeneral;
import at.sunplugged.celldatabaseV2.common.PrefNodes;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseServiceException;
import at.sunplugged.celldatabasev2.rcp.main.wizards.StartupWizard;
import datamodel.Database;

/**
 * This is a stub implementation containing e4 LifeCycle annotated methods.<br />
 * There is a corresponding entry in <em>plugin.xml</em> (under the
 * <em>org.eclipse.core.runtime.products' extension point</em>) that references this class.
 **/
@SuppressWarnings("restriction")
public class E4LifeCycle {
  private static final Logger LOG = LoggerFactory.getLogger(E4LifeCycle.class);



  @PostContextCreate
  void postContextCreate(IEclipseContext workbenchContext, Display display) {
    at.sunplugged.celldatabaseV2.common.Utils.setDefaultSettings(false);


    final Shell shell = new Shell(SWT.TOOL | SWT.NO_TRIM | SWT.APPLICATION_MODAL);

    StartupWizard startupWizard = new StartupWizard();

    WizardDialog dialog = new WizardDialog(shell, startupWizard);

    if (dialog.open() != Window.OK) {
      System.exit(-1);
    }


    DatabaseService databaseService = workbenchContext.get(DatabaseService.class);
    Database database;
    try {
      databaseService.openDatabase(ConfigurationScope.INSTANCE.getNode(PrefNodes.LOCATIONS)
          .get(LocationsGeneral.DATABASE_FILE_XMI, ""));
    } catch (DatabaseServiceException e) {
      LOG.error("Faild to load database intially...", e);
      return;
    }
    database = databaseService.getDatabase();
    EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(database);

    workbenchContext.set(ContextKeys.EDITING_DOMAIN, domain);
    workbenchContext.set(ContextKeys.COMMAND_STACK, domain.getCommandStack());
    workbenchContext.set(EditingDomain.class, domain);
    workbenchContext.set(CommandStack.class, domain.getCommandStack());
    workbenchContext.set(Database.class, database);
    LOG.debug(System.getenv()
        .toString());
  }


  @PreSave
  void preSave(IEclipseContext workbenchContext) {}

  @ProcessAdditions
  void processAdditions(IEclipseContext workbenchContext) throws IOException {}

  @ProcessRemovals
  void processRemovals(IEclipseContext workbenchContext) {}
}
