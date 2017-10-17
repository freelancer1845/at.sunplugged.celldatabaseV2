
package at.sunplugged.celldatabasev2.rcp.main.handler;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import datamodel.Database;

public class Delete {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) EObject object,
      DatabaseService databaseService) {
    Database database = databaseService.getDatabase();
    EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(database);
    Command cmd = RemoveCommand.create(editingDomain, object);
    editingDomain.getCommandStack().execute(cmd);
  }

}
