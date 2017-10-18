
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.command.CommandStack;

public class RedoHandler {
  @Execute
  public void execute(CommandStack commandStack) {
    commandStack.redo();
  }

  @CanExecute
  public boolean canExecute(CommandStack commandStack) {
    return commandStack.canRedo();
  }

}
