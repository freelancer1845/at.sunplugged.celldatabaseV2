package at.sunplugged.celldatabasev2.rcp.main;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
  private Logger LOG = LoggerFactory.getLogger(getClass());

  @Override
  public void start(BundleContext context) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void stop(BundleContext context) throws Exception {
    LOG.debug("RCP Main bundle deactivating...");
  }

}
