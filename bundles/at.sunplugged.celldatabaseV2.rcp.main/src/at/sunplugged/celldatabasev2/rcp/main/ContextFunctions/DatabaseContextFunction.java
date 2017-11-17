package at.sunplugged.celldatabasev2.rcp.main.ContextFunctions;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.osgi.service.component.annotations.Component;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;

@Component(name = "DatabaseContextFunction", service = IContextFunction.class,
    property = "service.context.key=datamodel.Database")
public class DatabaseContextFunction extends ContextFunction {
  @Override
  public Object compute(IEclipseContext context, String contextKey) {
    DatabaseService databaseService = context.get(DatabaseService.class);
    return databaseService.getDatabase();
  }
}
