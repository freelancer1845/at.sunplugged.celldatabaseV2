
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import datamodel.Database;

public class DeleteHandler {
  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection,
      Database database, EditingDomain editingDomain, CommandStack stack,
      EPartService partService) {
    List<EObject> objectsToDelete;

    if (selection instanceof Object[]) {
      objectsToDelete = Arrays.stream((Object[]) selection).map(eObject -> (EObject) eObject)
          .collect(Collectors.toList());
    } else {
      objectsToDelete = Arrays.asList((EObject) selection);
    }

    objectsToDelete.stream().map(eObject -> EcoreUtil.getURI(eObject)).forEach(uri -> {
      MPart editorPart = partService.getParts().stream().filter(part -> {
        Object partUri = part.getTransientData().get("uri");
        if (partUri instanceof URI) {
          return partUri.equals(uri);
        } else {
          return false;
        }

      }).findAny().orElse(null);
      if (editorPart != null) {
        partService.hidePart(editorPart, true);
      }
    });

    Command cmd = RemoveCommand.create(editingDomain, objectsToDelete);
    stack.execute(cmd);
  }

}
