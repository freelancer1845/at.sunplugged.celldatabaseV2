
package at.sunplugged.celldatabasev2.rcp.main.partdescriptors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
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
import at.sunplugged.celldatabaseV2.common.ui.LabelHelper;
import at.sunplugged.celldatabaseV2.plotting.CellResultSummaryPlot;
import at.sunplugged.celldatabaseV2.plotting.PlotHelper;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;

public class ChartDescriptor {
  private static final Logger LOG = LoggerFactory.getLogger(ChartDescriptor.class);


  @Inject
  private MPart part;

  private Map<String, String> options = new HashMap<>();

  private Composite composite;

  private ChartComposite chartComposite;

  @PostConstruct
  public void postConstruct(Composite parent) {



    Object data = part.getTransientData().get("data");


    if (data instanceof List<?>) {
      List<?> listData = (List<?>) data;
      if (listData.size() == 0) {
        LabelHelper.createErrorLabel(parent, "No Plot Data provided.");
      }
      if (listData.get(0) instanceof CellMeasurementDataSet) {
        if (listData.size() == 1) {

          ScrolledComposite container = new ScrolledComposite(parent, SWT.V_SCROLL);
          container.setExpandHorizontal(true);
          container.setExpandVertical(true);
          container.setMinSize(250, 1000);
          try {
            Composite plot = new CellResultSummaryPlot(container, SWT.NONE,
                (CellResult) ((List<CellMeasurementDataSet>) data).get(0).eContainer());
            container.setContent(plot);
          } catch (Exception e) {
            LOG.error("Failed to plot...", e);
            CLabel error =
                LabelHelper.createErrorLabel(container, "Plotting failed: " + e.getMessage());
            container.setContent(error);
          }


        } else {
          addMultiPlot(parent);
        }

      }
    }



  }



  private void addMultiPlot(Composite parent) {
    composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(1, false));



    composite.addControlListener(new ControlAdapter() {

      @Override
      public void controlResized(ControlEvent e) {
        drawPlot();
        super.controlResized(e);
      }
    });


    chartComposite = new ChartComposite(composite, SWT.NONE);
    chartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    drawPlot();

    Group controlGroup = new Group(composite, SWT.BORDER);
    controlGroup.setText("Options");

    controlGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    controlGroup.setLayout(new GridLayout(4, false));



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

    Label lblPowerCurveFitSeries = new Label(controlGroup, SWT.NONE);
    lblPowerCurveFitSeries.setText("Draw PowerCurve Fit Series");
    lblPowerCurveFitSeries.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

    Button cbPowerCurveFitSeries = new Button(controlGroup, SWT.CHECK);
    cbPowerCurveFitSeries.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    cbPowerCurveFitSeries.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (cbPowerCurveFitSeries.getSelection() == false) {
          options.remove(PlotHelper.POWER_CURVE_FIT);
        } else {
          options.put(PlotHelper.POWER_CURVE_FIT, "true");
        }
        drawPlot();
      }
    });

    Label lblRsSeries = new Label(controlGroup, SWT.NONE);
    lblRsSeries.setText("Draw RS Series");
    lblRsSeries.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

    Button cbRsSeries = new Button(controlGroup, SWT.CHECK);
    cbRsSeries.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    cbRsSeries.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (cbRsSeries.getSelection() == false) {
          options.remove(PlotHelper.RS_LINE);
        } else {
          options.put(PlotHelper.RS_LINE, "true");
        }
        drawPlot();
      }
    });

    Label lblRpSeries = new Label(controlGroup, SWT.NONE);
    lblRpSeries.setText("Draw Rp Series");
    lblRpSeries.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

    Button cbRpSeries = new Button(controlGroup, SWT.CHECK);
    cbRpSeries.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    cbRpSeries.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (cbRpSeries.getSelection() == false) {
          options.remove(PlotHelper.RP_LINE);
        } else {
          options.put(PlotHelper.RP_LINE, "true");
        }
        drawPlot();
      }
    });

    Label lbllightUiFitSeries = new Label(controlGroup, SWT.NONE);
    lbllightUiFitSeries.setText("Draw Voc/Rs Fit Series");
    lbllightUiFitSeries.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

    Button cblightUiFitSeries = new Button(controlGroup, SWT.CHECK);
    cblightUiFitSeries.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    cblightUiFitSeries.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (cblightUiFitSeries.getSelection() == false) {
          options.remove(PlotHelper.VOC_RS_FIT);
        } else {
          options.put(PlotHelper.VOC_RS_FIT, "true");
        }
        drawPlot();
      }
    });
    Label lbldarkUiFitSeries = new Label(controlGroup, SWT.NONE);
    lbldarkUiFitSeries.setText("Draw Isc/Rp Fit Series");
    lbldarkUiFitSeries.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

    Button cbdarkUiFitSeries = new Button(controlGroup, SWT.CHECK);
    cbdarkUiFitSeries.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    cbdarkUiFitSeries.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (cbdarkUiFitSeries.getSelection() == false) {
          options.remove(PlotHelper.ISC_RP_FIT);
        } else {
          options.put(PlotHelper.ISC_RP_FIT, "true");
        }
        drawPlot();
      }
    });

    addOption(controlGroup, "Draw Dark Rp Fit", PlotHelper.DARK_RP_FIT);
    addOption(controlGroup, "Draw Dark Rs Fit", PlotHelper.DARK_RS_FIT);
  }



  private void addOption(Group controlGroup, String name, String option) {
    Label lblPowerCurve = new Label(controlGroup, SWT.NONE);
    lblPowerCurve.setText(name);

    lblPowerCurve.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

    Button cbPowerCurve = new Button(controlGroup, SWT.CHECK);
    cbPowerCurve.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    cbPowerCurve.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (cbPowerCurve.getSelection() == false) {
          options.remove(option);
        } else {
          options.put(option, "true");
        }
        drawPlot();
      }
    });
  }



  private void drawPlot() {
    Object data = part.getTransientData().get("data");
    if (data instanceof List<?>) {
      if (((List<?>) data).get(0) instanceof CellMeasurementDataSet) {

        @SuppressWarnings("unchecked")
        JFreeChart chart =
            PlotHelper.createJFreeChart((List<CellMeasurementDataSet>) data, options);

        chartComposite.dispose();
        chartComposite = new ChartComposite(composite, SWT.NONE, chart, true);
        chartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        chartComposite.moveAbove(null);
        composite.layout();



      } else {
        LOG.error("Expected List of CellMeasurementDataSets...");
      }

    } else {
      LOG.error("Exepceted List of CellMeasurementDataSets...");
    }
  }

}
