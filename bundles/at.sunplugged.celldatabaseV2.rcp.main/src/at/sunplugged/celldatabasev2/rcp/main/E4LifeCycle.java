package at.sunplugged.celldatabasev2.rcp.main;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import datamodel.Database;

/**
 * This is a stub implementation containing e4 LifeCycle annotated methods.<br />
 * There is a corresponding entry in <em>plugin.xml</em> (under the
 * <em>org.eclipse.core.runtime.products' extension point</em>) that references this class.
 **/
@SuppressWarnings("restriction")
public class E4LifeCycle {

  @PostContextCreate
  void postContextCreate(IEclipseContext workbenchContext) {
    at.sunplugged.celldatabaseV2.common.Utils.setDefaultSettings(false);


    DatabaseService databaseService = workbenchContext.get(DatabaseService.class);
    Database database = databaseService.getDatabase();
    EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(database);

    workbenchContext.set(ContextKeys.EDITING_DOMAIN, domain);
    workbenchContext.set(ContextKeys.COMMAND_STACK, domain.getCommandStack());
    workbenchContext.set(EditingDomain.class, domain);
    workbenchContext.set(CommandStack.class, domain.getCommandStack());
    workbenchContext.set(Database.class, database);
  }

  @PreSave
  void preSave(IEclipseContext workbenchContext) {}

  @ProcessAdditions
  void processAdditions(IEclipseContext workbenchContext) {}

  @ProcessRemovals
  void processRemovals(IEclipseContext workbenchContext) {}
}
