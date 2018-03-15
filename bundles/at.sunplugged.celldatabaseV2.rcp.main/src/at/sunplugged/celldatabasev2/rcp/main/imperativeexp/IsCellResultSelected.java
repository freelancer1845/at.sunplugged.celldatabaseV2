package at.sunplugged.celldatabasev2.rcp.main.imperativeexp;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.ui.services.IServiceConstants;
import datamodel.CellResult;

public class IsCellResultSelected {

  @Evaluate
  public boolean evaluate(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    return selection instanceof CellResult;
  }

}
