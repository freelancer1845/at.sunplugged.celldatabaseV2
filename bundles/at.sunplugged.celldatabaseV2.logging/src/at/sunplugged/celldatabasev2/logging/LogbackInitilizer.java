package at.sunplugged.celldatabasev2.logging;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.FileUtils;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackInitilizer {

  public void initilizeLogback() throws URISyntaxException, IOException, JoranException {
    ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
    LoggerContext loggerContext = (LoggerContext) iLoggerFactory;
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(loggerContext);

    loggerContext.reset();
    File logbackFile = null;
    logbackFile = FileUtils.locateRootFile("logback.xml");

    if (logbackFile != null && logbackFile.isFile()) {
      configurator.doConfigure(logbackFile);
    } else {
      throw new IllegalStateException("Logback file not found...");
    }
  }


}
