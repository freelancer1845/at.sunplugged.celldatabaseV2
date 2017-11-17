package at.sunplugged.celldatabasev2.rcp.main.ContextFunctions;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.osgi.service.component.annotations.Component;

@Component(service = IContextFunction.class,
    property = "service.context.key=org.eclipse.emf.common.command.CommandStack")
public class CommandStackContextFunction extends ContextFunction {
  @Override
  public Object compute(IEclipseContext context, String contextKey) {
    return context.get(EditingDomain.class)
        .getCommandStack();
  }
}
