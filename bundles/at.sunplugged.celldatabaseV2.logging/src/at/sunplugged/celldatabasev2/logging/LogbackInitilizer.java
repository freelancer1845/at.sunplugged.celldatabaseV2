package at.sunplugged.celldatabasev2.logging;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.FileUtils;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusListener;
import ch.qos.logback.core.util.StatusPrinter;

public class LogbackInitilizer {

  public void initilizeLogback() throws URISyntaxException, IOException, JoranException {
    ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
    LoggerContext loggerContext = (LoggerContext) iLoggerFactory;


    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(loggerContext);

    loggerContext.reset();
    File logbackFile = null;
    try {
      logbackFile = FileUtils.locateRootFile("logback.xml");
      if (logbackFile != null && logbackFile.isFile()) {
        configurator.doConfigure(logbackFile);
      } else {
        throw new IllegalStateException("Logback file not found...");
      }
    } catch (IOException e) {
      // try to find fall back logback.xml file
      configurator.doConfigure(FileLocator.openStream(Activator.getContext().getBundle(),
          new Path("resources/logback.xml"), false));
    }

    loggerContext.getStatusManager().add(new StatusListener() {

      @Override
      public void addStatusEvent(Status status) {
        System.out.println("Status Event");
        if (status.getEffectiveLevel() == Status.ERROR) {
          if (Display.getDefault() != null) {
            Display.getDefault().asyncExec(new Runnable() {
              @Override
              public void run() {
                MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
                    status.getMessage());
              }
            });
          }
        }
      }
    });

    SWTLogAppender swtAppender = new SWTLogAppender();
    swtAppender.setContext(loggerContext);
    swtAppender.setName("SWT Appender");
    swtAppender.start();

    loggerContext.getLogger("ROOT").addAppender(swtAppender);
    loggerContext.getLogger("Logger").addAppender(swtAppender);

    StatusPrinter.print(loggerContext);
  }


}
