
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import datamodel.CellGroup;
import datamodel.Database;
import datamodel.DatamodelFactory;

public class CreateCellGroupHandler {
  @Execute
  public void execute(Database database, EditingDomain editingDomain, CommandStack stack) {
    CellGroup cellGroup = DatamodelFactory.eINSTANCE.createCellGroup();
    cellGroup.setName("New Group");
    Command cmd = AddCommand.create(editingDomain, database, null, cellGroup);
    stack.execute(cmd);
  }

}
