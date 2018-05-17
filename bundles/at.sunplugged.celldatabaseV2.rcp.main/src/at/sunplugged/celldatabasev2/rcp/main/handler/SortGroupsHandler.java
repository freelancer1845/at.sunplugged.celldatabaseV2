
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import datamodel.CellGroup;
import datamodel.Database;
import datamodel.DatamodelPackage;
import datamodel.util.DatamodelUtils;

public class SortGroupsHandler {
  @Execute
  public void execute(Database database, EditingDomain domain, CommandStack stack) {
    EList<CellGroup> groups = database.getCellGroups();
    EList<CellGroup> sortedList = new BasicEList<>(groups);
    ECollections.sort(sortedList, DatamodelUtils.Comparators.compareGroupsNatural());

    Command cmd = SetCommand.create(domain, database,
        DatamodelPackage.Literals.DATABASE__CELL_GROUPS, sortedList);
    stack.execute(cmd);

  }

}
