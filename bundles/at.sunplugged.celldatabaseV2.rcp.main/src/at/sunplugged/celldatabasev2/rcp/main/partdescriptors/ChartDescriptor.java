
package at.sunplugged.celldatabasev2.rcp.main.partdescriptors;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.plotting.PlotHelper;
import datamodel.CellMeasurementDataSet;

public class ChartDescriptor {
  private static final Logger LOG = LoggerFactory.getLogger(ChartDescriptor.class);


  @Inject
  private MPart part;

  @PostConstruct
  public void postConstruct(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(1, false));

    ChartComposite chartComposite = new ChartComposite(composite, SWT.NONE);
    chartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));


    Object data = part.getTransientData().get("data");
    if (data instanceof List<?>) {
      if (((List<?>) data).get(0) instanceof CellMeasurementDataSet) {
        @SuppressWarnings("unchecked")
        JFreeChart chart = PlotHelper.createJFreeChart((List<CellMeasurementDataSet>) data);
        chartComposite.setChart(chart);
      } else {
        LOG.error("Expected List of CellMeasurementDataSets...");
      }

    } else {
      LOG.error("Exepceted List of CellMeasurementDataSets...");
    }

  }

}
