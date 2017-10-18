package at.sunplugged.celldatabasev2.rcp.main;

import java.io.IOException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import datamodel.Database;

/**
 * This is a stub implementation containing e4 LifeCycle annotated methods.<br />
 * There is a corresponding entry in <em>plugin.xml</em> (under the
 * <em>org.eclipse.core.runtime.products' extension point</em>) that references this class.
 **/
@SuppressWarnings("restriction")
public class E4LifeCycle {


  @PostContextCreate
  void postContextCreate(IEclipseContext workbenchContext) {
    at.sunplugged.celldatabaseV2.common.Utils.setDefaultSettings(false);


    DatabaseService databaseService = workbenchContext.get(DatabaseService.class);
    Database database = databaseService.getDatabase();
    EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(database);

    workbenchContext.set(ContextKeys.EDITING_DOMAIN, domain);
    workbenchContext.set(ContextKeys.COMMAND_STACK, domain.getCommandStack());
    workbenchContext.set(EditingDomain.class, domain);
    workbenchContext.set(CommandStack.class, domain.getCommandStack());
    workbenchContext.set(Database.class, database);
  }

  @PreSave
  void preSave(IEclipseContext workbenchContext) {}

  @ProcessAdditions
  void processAdditions(IEclipseContext workbenchContext) throws IOException {

    ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
    LoggerContext loggerContext = (LoggerContext) iLoggerFactory;
    loggerContext.reset();
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(loggerContext);
    try {
      Bundle bundle = Platform.getBundle(FrameworkUtil.getBundle(getClass()).getSymbolicName());
      configurator
          .doConfigure(FileLocator.openStream(bundle, new Path("resources/logback.xml"), false));
    } catch (JoranException e) {
      throw new IOException(e.getMessage(), e);
    }

    LoggerFactory.getLogger(getClass()).debug("Logging Configuration loaded...");
  }

  @ProcessRemovals
  void processRemovals(IEclipseContext workbenchContext) {}
}
