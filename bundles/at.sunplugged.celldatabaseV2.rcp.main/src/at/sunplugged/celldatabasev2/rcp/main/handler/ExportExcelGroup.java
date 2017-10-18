
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.export.api.ExcelOutputHelper;
import datamodel.CellResult;

public class ExportExcelGroup {
  private static final Logger LOG = LoggerFactory.getLogger(ExportExcelGroup.class);

  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selection, Shell parent) {
    LOG.debug("Exporting Excel CellResults...");

    List<CellResult> cellResults = Arrays.stream(selection)
        .map(cellResult -> (CellResult) cellResult).collect(Collectors.toList());

    if (cellResults.isEmpty()) {
      LOG.warn("Tried to export 0 CellResults...");
      return;
    }

    FileDialog dialog = new FileDialog(parent, SWT.SAVE);
    dialog.setFilterExtensions(new String[] {"*.xlsx"});
    dialog.setFilterNames(new String[] {"Ms Excel Open XML Format Spreadsheet"});
    if (dialog.open() != null) {
      ExcelOutputHelper helper = new ExcelOutputHelper(cellResults,
          Paths.get(dialog.getFilterPath(), dialog.getFileName()).toString());
      helper.execute();
    }
    LOG.debug("Done exporting Excel CellResults");
  }


  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selection) {
    if (selection.length == 0) {
      return false;
    } else if (Arrays.stream(selection).anyMatch(object -> !(object instanceof CellResult))) {
      return false;
    }
    return true;
  }

}
