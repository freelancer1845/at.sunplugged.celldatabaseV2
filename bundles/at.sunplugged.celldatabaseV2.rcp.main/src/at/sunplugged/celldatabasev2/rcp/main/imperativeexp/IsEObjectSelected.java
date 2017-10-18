
package at.sunplugged.celldatabasev2.rcp.main.imperativeexp;

import java.util.Arrays;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecore.EObject;

public class IsEObjectSelected {
  @Evaluate
  public boolean evaluate(@Named(IServiceConstants.ACTIVE_SELECTION) Object object) {
    if (object instanceof Object[]) {
      return Arrays.stream((Object[]) object)
          .anyMatch(singleObject -> !(object instanceof EObject));
    } else {
      return object instanceof EObject;
    }


  }
}
