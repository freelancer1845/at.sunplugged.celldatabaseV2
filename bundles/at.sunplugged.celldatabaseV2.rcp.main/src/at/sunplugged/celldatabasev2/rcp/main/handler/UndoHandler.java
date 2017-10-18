
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.command.CommandStack;

public class UndoHandler {
  @Execute
  public void execute(CommandStack stack) {
    stack.undo();
  }

  @CanExecute
  public boolean canExecute(CommandStack stack) {
    return stack.canUndo();
  }

}
