
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveHandler {

  private static final Logger LOG = LoggerFactory.getLogger(SaveHandler.class);

  @Execute
  public void execute(EPartService partService) {
    MPart viewerPart = partService.getParts().stream()
        .filter(part -> part.getElementId()
            .equals("at.sunplugged.celldatabasev2.rcp.main.part.modelviewer"))
        .findAny().orElse(null);
    if (viewerPart != null) {
      partService.savePart(viewerPart, false);
    } else {
      LOG.error("Trying to save but modelviewer part is could not be found...");
    }
  }

}
