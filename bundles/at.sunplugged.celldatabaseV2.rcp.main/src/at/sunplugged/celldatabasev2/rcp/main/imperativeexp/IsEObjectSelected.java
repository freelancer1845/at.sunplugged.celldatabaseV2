
package at.sunplugged.celldatabasev2.rcp.main.imperativeexp;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecore.EObject;

public class IsEObjectSelected {
  @Evaluate
  public boolean evaluate(@Named(IServiceConstants.ACTIVE_SELECTION) Object object) {
    return object instanceof EObject;
  }
}
