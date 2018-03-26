package at.sunplugged.celldatabasev2.logging;

import org.eclipse.equinox.log.ExtendedLogReaderService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EquinoxLogListener {

  private Logger LOG = LoggerFactory.getLogger("Equinox LOG");

  @Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC,
      bind = "bindReaderService")
  protected volatile ExtendedLogReaderService readerService;


  protected void bindReaderService(ExtendedLogReaderService readerService) {
    this.readerService = readerService;

    readerService.addLogListener(new LogListener() {


      @Override
      public void logged(LogEntry entry) {
        switch (entry.getLevel()) {
          case LogService.LOG_DEBUG: {
            if (entry.getException() != null) {
              LOG.debug("Bundle: " + entry.getBundle().getSymbolicName() + " Message: "
                  + entry.getMessage(), entry.getException());
            } else {
              LOG.debug("Bundle: " + entry.getBundle().getSymbolicName() + " Message: "
                  + entry.getMessage());
            }
            break;
          }
          case LogService.LOG_ERROR: {
            if (entry.getException() != null) {
              LOG.error("Bundle: " + entry.getBundle().getSymbolicName() + " Message: "
                  + entry.getMessage(), entry.getException());
            } else {
              LOG.error("Bundle: " + entry.getBundle().getSymbolicName() + " Message: "
                  + entry.getMessage());
            }
            break;
          }
          case LogService.LOG_INFO: {
            if (entry.getException() != null) {
              LOG.info("Bundle: " + entry.getBundle().getSymbolicName() + " Message: "
                  + entry.getMessage(), entry.getException());
            } else {
              LOG.info("Bundle: " + entry.getBundle().getSymbolicName() + " Message: "
                  + entry.getMessage());
            }
            break;
          }
          case LogService.LOG_WARNING: {
            if (entry.getException() != null) {
              LOG.warn("Bundle: " + entry.getBundle().getSymbolicName() + " Message: "
                  + entry.getMessage(), entry.getException());
            } else {
              LOG.warn("Bundle: " + entry.getBundle().getSymbolicName() + " Message: "
                  + entry.getMessage());
            }
            break;
          }
          default:
            break;
        }
      }
    });

  }

}
