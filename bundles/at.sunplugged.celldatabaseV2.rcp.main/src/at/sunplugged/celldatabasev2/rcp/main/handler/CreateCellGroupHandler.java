
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import datamodel.CellGroup;
import datamodel.Database;
import datamodel.DatamodelFactory;

public class CreateCellGroupHandler {
  @Execute
  public void execute(DatabaseService databaseService) {
    CellGroup cellGroup = DatamodelFactory.eINSTANCE.createCellGroup();
    cellGroup.setName("New Group");
    Database database = databaseService.getDatabase();
    EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(database);
    Command cmd = AddCommand.create(editingDomain, database, null, cellGroup);
    CommandStack stack = editingDomain.getCommandStack();
    stack.execute(cmd);
  }

}
