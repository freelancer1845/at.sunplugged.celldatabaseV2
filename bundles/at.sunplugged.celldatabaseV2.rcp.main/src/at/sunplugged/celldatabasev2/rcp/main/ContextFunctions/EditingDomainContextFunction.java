package at.sunplugged.celldatabasev2.rcp.main.ContextFunctions;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.osgi.service.component.annotations.Component;
import datamodel.Database;

@Component(service = IContextFunction.class,
    property = "service.context.key=org.eclipse.emf.edit.domain.EditingDomain")
public class EditingDomainContextFunction extends ContextFunction {
  @Override
  public Object compute(IEclipseContext context, String contextKey) {
    Database database = context.get(Database.class);
    EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(database);
    return domain;
  }
}
