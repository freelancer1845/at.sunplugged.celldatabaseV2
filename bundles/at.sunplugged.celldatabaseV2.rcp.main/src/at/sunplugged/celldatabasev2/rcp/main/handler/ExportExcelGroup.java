
package at.sunplugged.celldatabasev2.rcp.main.handler;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Named;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobFunction;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.export.api.ExcelExports;
import datamodel.CellGroup;
import datamodel.CellResult;

public class ExportExcelGroup {
  private static final Logger LOG = LoggerFactory.getLogger(ExportExcelGroup.class);

  @Execute
  public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection, Shell parent) {
    LOG.debug("Exporting Excel CellResults...");
    List<CellResult> cellResults = new ArrayList<>();
    if (selection instanceof Object[]) {
      Arrays.stream((Object[]) selection)
          .sequential()
          .forEach(object -> {
            if (object instanceof CellGroup) {
              CellGroup group = (CellGroup) object;
              group.getCellResults()
                  .stream()
                  .sequential()
                  .forEach(result -> {
                    if (cellResults.contains(result) == false) {
                      cellResults.add(result);
                    }
                  });
            } else if (object instanceof CellResult) {
              CellResult result = (CellResult) object;
              if (cellResults.contains(result) == false) {
                cellResults.add(result);
              }
            }

          });
    } else if (selection instanceof CellGroup) {
      cellResults.addAll(((CellGroup) selection).getCellResults());
    } else if (selection instanceof CellResult) {
      cellResults.add((CellResult) selection);
    }

    if (cellResults.isEmpty()) {
      LOG.warn("Tried to export 0 CellResults...");
      return;
    }

    FileDialog dialog = new FileDialog(parent, SWT.SAVE);
    dialog.setFilterExtensions(new String[] {"*.xlsx"});
    dialog.setFilterNames(new String[] {"Microsoft Open XML Format"});
    if (dialog.open() != null) {

      Job exportJob = Job.create("Cell Results export Job", new IJobFunction() {

        @Override
        public IStatus run(IProgressMonitor monitor) {
          try {
            monitor.beginTask("Exporting...", 1);
            ExcelExports.exportCellResults(Paths.get(dialog.getFilterPath(), dialog.getFileName())
                .toString(), cellResults);
            LOG.debug("Done exporting Excel CellResults");

          } catch (Exception e) {
            LOG.error("Failed to export...", e);
            return new Status(Status.ERROR, FrameworkUtil.getBundle(this.getClass())
                .getSymbolicName(), "Failed to export..", e);
          } finally {
            monitor.done();
          }
          return Status.OK_STATUS;
        }
      });
      exportJob.setPriority(Job.LONG);
      exportJob.schedule();
    }

  }


  @CanExecute
  public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
    if (selection == null) {
      return false;
    }
    if (selection instanceof Object[]) {
      Object[] selections = (Object[]) selection;
      if (selections.length == 0) {
        return false;
      } else if (Arrays.stream(selections)
          .anyMatch(object -> !(object instanceof CellResult) && !(object instanceof CellGroup))) {
        return false;
      }
    } else if (selection instanceof CellGroup) {
      return true;
    } else if (selection instanceof CellResult) {
      return true;
    }

    return true;
  }

}
