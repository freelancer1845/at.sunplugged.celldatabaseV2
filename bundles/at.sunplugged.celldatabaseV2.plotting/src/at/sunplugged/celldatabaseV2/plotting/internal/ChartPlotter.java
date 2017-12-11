package at.sunplugged.celldatabaseV2.plotting.internal;

import java.util.Map;
import org.jfree.chart.JFreeChart;

public interface ChartPlotter {


  public JFreeChart getChart(Map<String, String> options);
}
