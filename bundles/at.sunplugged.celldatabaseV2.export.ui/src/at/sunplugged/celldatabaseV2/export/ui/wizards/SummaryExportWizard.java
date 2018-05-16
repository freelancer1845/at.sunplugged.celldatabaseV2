package at.sunplugged.celldatabaseV2.export.ui.wizards;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobFunction;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.ui.dialog.FileDialogWithLastOpen;
import at.sunplugged.celldatabaseV2.common.ui.dialog.FileDialogWithLastOpen.IDs;
import at.sunplugged.celldatabaseV2.export.api.ExcelExports;
import datamodel.CellGroup;

public class SummaryExportWizard extends Wizard {

  private static final Logger LOG = LoggerFactory.getLogger(SummaryExportWizard.class);

  private static final String WINDOW_TITLE = "Summary Export Wizard";

  private final List<CellGroup> groups;

  protected PageOne pageOne;

  public SummaryExportWizard(List<CellGroup> groups) {
    super();
    this.groups = groups;
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    pageOne = new PageOne(groups);

    addPage(pageOne);
  }

  @Override
  public String getWindowTitle() {
    return WINDOW_TITLE;
  }

  @Override
  public boolean performFinish() {
    FileDialogWithLastOpen dialog =
        new FileDialogWithLastOpen(getShell(), SWT.SAVE, IDs.CELL_GROUPS_EXPORT);

    if (dialog.open() != null) {
      Path filePath = Paths.get(dialog.getFilterPath(), dialog.getFileName());
      ChangeRecorder recorder = new ChangeRecorder(groups);
      final List<CellGroup> reducedCellGroups = pageOne.getReducedDatabase();
      Job exportJob = Job.create("Excel Export Job", new IJobFunction() {

        @Override
        public IStatus run(IProgressMonitor monitor) {
          try {
            LOG.debug("Exporting Cell Groups...");
            monitor.beginTask("Export Cell Groups...", 1);
            ExcelExports.exportCellGroups(reducedCellGroups, filePath.toString());
            monitor.done();
          } catch (IOException e) {
            LOG.error("Failed to export CellGroups...", e);
            return new Status(Status.ERROR,
                FrameworkUtil.getBundle(this.getClass()).getSymbolicName(),
                "Failed to expot CellGroups...", e);
          } finally {
            LOG.debug("Done exporting Cell Groups.");
            recorder.endRecording().applyAndReverse();
          }
          return Status.OK_STATUS;
        }

      });
      exportJob.setPriority(Job.LONG);
      exportJob.schedule();

    }

    return true;
  }

}

