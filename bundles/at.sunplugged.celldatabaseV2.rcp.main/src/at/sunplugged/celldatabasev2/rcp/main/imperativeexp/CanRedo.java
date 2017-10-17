
package at.sunplugged.celldatabasev2.rcp.main.imperativeexp;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.emf.common.command.CommandStack;

public class CanRedo {
  @Evaluate
  public boolean evaluate(CommandStack stack) {
    return stack.canRedo();
  }
}
