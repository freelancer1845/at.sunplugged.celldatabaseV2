
package at.sunplugged.celldatabasev2.rcp.main.partdescriptors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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

  private Map<String, String> options = new HashMap<>();

  private Composite composite;

  private ChartComposite chartComposite;

  @PostConstruct
  public void postConstruct(Composite parent) {
    composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(1, false));

    chartComposite = new ChartComposite(composite, SWT.NONE);
    chartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    drawPlot();

    Group controlGroup = new Group(composite, SWT.BORDER);
    controlGroup.setText("Options");

    controlGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    controlGroup.setLayout(new GridLayout(2, false));

    Label lblPowerCurve = new Label(controlGroup, SWT.NONE);
    lblPowerCurve.setText("Draw PowerCurve");

    lblPowerCurve.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

    Button cbPowerCurve = new Button(controlGroup, SWT.CHECK);
    cbPowerCurve.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    cbPowerCurve.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (cbPowerCurve.getSelection() == false) {
          options.remove(PlotHelper.POWER_CURVE);
        } else {
          options.put(PlotHelper.POWER_CURVE, "true");
        }
        drawPlot();
      }
    });

    Label lblPowerMarker = new Label(controlGroup, SWT.NONE);
    lblPowerMarker.setText("Draw Power Marker");
    lblPowerMarker.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

    Button cbPowerMarker = new Button(controlGroup, SWT.CHECK);
    cbPowerMarker.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    cbPowerMarker.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (cbPowerMarker.getSelection() == false) {
          options.remove(PlotHelper.MP_POINT);
        } else {
          options.put(PlotHelper.MP_POINT, "true");
        }
        drawPlot();
      }
    });


  }

  private void drawPlot() {
    Object data = part.getTransientData().get("data");
    if (data instanceof List<?>) {
      if (((List<?>) data).get(0) instanceof CellMeasurementDataSet) {

        chartComposite.dispose();
        chartComposite = new ChartComposite(composite, SWT.NONE);
        chartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        chartComposite.moveAbove(null);
        composite.layout();
        @SuppressWarnings("unchecked")
        JFreeChart chart =
            PlotHelper.createJFreeChart((List<CellMeasurementDataSet>) data, options);
        chartComposite.setChart(chart);
      } else {
        LOG.error("Expected List of CellMeasurementDataSets...");
      }

    } else {
      LOG.error("Exepceted List of CellMeasurementDataSets...");
    }
  }

}
