package at.sunplugged.celldatabaseV2.export.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.odftoolkit.odfdom.type.CellRangeAddress;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.draw.Image;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.export.Activator;
import at.sunplugged.celldatabaseV2.plotting.PlotHelper;
import datamodel.CellResult;
import datamodel.UIDataPoint;

public class CellResultSheetCreator {

  private static final Logger LOG = LoggerFactory.getLogger(CellResultSheetCreator.class);


  public static final String TEMPLATE_SHEET = "templateResultSheet";

  private final CellResult cellResult;

  private String tableName;

  private SpreadsheetDocument document;

  private CellRangeAddress currentCellRangeAddress;

  private CellRangeAddress voltageCellRangeAddress;

  private Table newTable;

  public CellResultSheetCreator(CellResult cellResult) {
    this.cellResult = cellResult;
  }

  public void execute(SpreadsheetDocument document) {
    this.document = document;
    long startTime = System.currentTimeMillis();
    Table table = document.getSheetByName(TEMPLATE_SHEET);
    newTable = document.insertSheet(table, document.getSheetCount() - 1);
    tableName = cellResult.getName();
    newTable.setTableName(tableName);
    List<Row> copyRows = new ArrayList<>(newTable.getRowList());
    int colCount = copyRows.get(0).getCellCount();
    copyRows.forEach(row -> {

      for (int col = 0; col < colCount; col++) {

        Cell cell = row.getCellByIndex(col);
        try {
          handleCellCellResult(cell);
        } catch (IOException e) {
          LOG.error("failed to handle cell: " + e);
        }
      }
    });

    // List<Chart> charts = document.getChartByTitle(Keys.UI_PLOT);
    // if (charts.size() > 0) {
    // handleChart(charts.get(0));
    // }

    document.removeSheet(document.getSheetCount());
    System.out.println("Time needed: " + (System.currentTimeMillis() - startTime));
  }


  private void handleCellCellResult(Cell cell) throws IOException {
    String value = cell.getStringValue();
    if (value.isEmpty()) {
      return;
    }
    switch (value) {
      case Keys.NAME:
        writeCellValue(cell, cellResult.getName());
        break;
      case Keys.AREA:
        writeCellValue(cell, cellResult.getLightMeasurementDataSet().getArea() * 10000.0);
        break;
      case Keys.EFF:
        writeCellValue(cell, cellResult.getEfficiency());
        break;
      case Keys.FF:
        writeCellValue(cell, cellResult.getFillFactor());
        break;
      case Keys.VOC:
        writeCellValue(cell, cellResult.getOpenCircuitVoltage());
        break;
      case Keys.ISC:
        writeCellValue(cell, cellResult.getShortCircuitCurrent());
        break;
      case Keys.JSC:
        writeCellValue(cell, cellResult.getShortCircuitCurrent()
            / cellResult.getLightMeasurementDataSet().getArea() * 10000.0);
        break;
      case Keys.MP:
        writeCellValue(cell, cellResult.getMaximumPower());
        break;
      case Keys.POWER_INPUT:
        writeCellValue(cell, cellResult.getLightMeasurementDataSet().getPowerInput());
        break;
      case Keys.RP:
        writeCellValue(cell, cellResult.getParallelResistance());
        break;
      case Keys.RP_DARK:
        writeCellValue(cell, cellResult.getDarkParallelResistance());
        break;
      case Keys.RS:
        writeCellValue(cell, cellResult.getSeriesResistance());
        break;
      case Keys.RS_DARK:
        writeCellValue(cell, cellResult.getDarkSeriesResistance());
        break;
      case Keys.VOLTAGE_DATA:
        writeVoltageData(cell);
        break;
      case Keys.CURRENT_DATA:
        writeCurrentData(cell);
        break;
      case Keys.UI_PLOT:
        createUiPlot(cell);
        break;
      default:
        break;
    }
  }

  private void createUiPlot(Cell cell) throws IOException {
    String imageName = "tempImage" + new Random().nextInt() + ".jpg";
    File imageFile = Activator.getContext().getDataFile(imageName);
    JFreeChart chart = PlotHelper.createJFreeChart(cellResult);
    try {
      ChartUtilities.saveChartAsJPEG(imageFile, 1.0f, chart, 600, 400);
    } catch (IOException e) {
      LOG.error("Failed to save Chart as file...", e);
      throw e;
    }

    Image image = cell.setImage(imageFile.toURI());
  }

  private void writeCellValue(Cell cell, Object value) {
    if (value instanceof Double) {
      cell.setDoubleValue((Double) value);
    } else {
      cell.setStringValue(value.toString());
    }
  }

  private void writeVoltageData(Cell cell) {
    Table table = cell.getTable();
    int startRow = cell.getRowIndex();
    int col = cell.getColumnIndex();
    int rowIndex = startRow;
    for (UIDataPoint dataPoint : cellResult.getLightMeasurementDataSet().getData()) {
      Row row = table.getRowByIndex(rowIndex);
      Cell cCell = row.getCellByIndex(col);
      writeCellValue(cCell, dataPoint.getVoltage());
      rowIndex++;
    }
    char colChar = (char) (65 + col);
    StringBuilder builder = new StringBuilder();
    builder.append(colChar);
    builder.append(".");
    builder.append(colChar);
    builder.append(startRow - 1);
    builder.append(":");
    builder.append(colChar);
    builder.append(".");
    builder.append(colChar);
    builder.append(rowIndex);
    System.out.println(builder.toString());
    voltageCellRangeAddress = new CellRangeAddress(builder.toString());
  }

  private void writeCurrentData(Cell cell) {
    Table table = cell.getTable();
    int startRow = cell.getRowIndex();
    int col = cell.getColumnIndex();
    int rowIndex = startRow;

    for (UIDataPoint dataPoint : cellResult.getLightMeasurementDataSet().getData()) {
      Row row = table.getRowByIndex(rowIndex);
      Cell cCell = row.getCellByIndex(col);
      writeCellValue(cCell, dataPoint.getCurrent());
      rowIndex++;
    }
    char colChar = (char) (65 + col);
    StringBuilder builder = new StringBuilder();
    builder.append(colChar);
    builder.append(".");
    builder.append(colChar);
    builder.append(startRow - 1);
    builder.append(":");
    builder.append(colChar);
    builder.append(".");
    builder.append(colChar);
    builder.append(rowIndex);
    currentCellRangeAddress = new CellRangeAddress(builder.toString());
  }


  //
  // private void handleChart(Chart chart) {
  //
  //
  // // DataSet dataSet = chart.getChartData();
  // // System.out.println(dataSet);
  // List<CellRangeAddress> list = new ArrayList<>();
  // list.add(voltageCellRangeAddress);
  // list.add(currentCellRangeAddress);
  // CellRangeAddressList test = new CellRangeAddressList(list);
  // CellRangeAddressList manual =
  // CellRangeAddressList.valueOf(tableName + ".A8:" + tableName + ".B164");
  // System.out.println(manual.toString());
  //
  //
  //
  // List<UIDataPoint> dataPoints = cellResult.getLightMeasurementDataSet().getData();
  // String[] labels = new String[dataPoints.size()];
  // String[] legends = new String[] {"Current"};
  // double[][] data = new double[1][dataPoints.size()];
  // for (int i = 0; i < dataPoints.size(); i++) {
  // labels[i] = String.valueOf(dataPoints.get(i).getVoltage());
  // data[0][i] = dataPoints.get(i).getCurrent();
  // }
  //
  // // DataSet newDataSet = new DataSet(manual, document, false, true, true);
  // DataSet newDataSet = new DataSet(labels, legends, data);
  //
  // System.out.println(newDataSet.getLocalTableFirstColumn()[0]);
  // System.out.println(newDataSet.getLocalTableFirstRow()[0]);
  // System.out.println(newDataSet);
  // chart.setChartData(newDataSet);
  //
  // }
  //
  //
  // private void createUIPlot(Cell cell) {
  // List<CellRangeAddress> list = new ArrayList<>();
  // list.add(voltageCellRangeAddress);
  // list.add(currentCellRangeAddress);
  // Rectangle rect = new Rectangle();
  // rect.x = 1;
  // rect.y = 2;
  // rect.width = 10000;
  // rect.height = 5000;
  //
  // List<UIDataPoint> dataPoints = cellResult.getLightMeasurementDataSet().getData();
  // String[] labels = new String[dataPoints.size()];
  // String[] legends = new String[] {"Current"};
  // double[][] data = new double[2][dataPoints.size()];
  // for (int i = 0; i < dataPoints.size(); i++) {
  // labels[i] = String.valueOf(dataPoints.get(i).getVoltage());
  // data[0][i] = dataPoints.get(i).getVoltage();
  // data[1][i] = dataPoints.get(i).getCurrent();
  // }
  // System.out.println(voltageCellRangeAddress.toString());
  // Chart chart = document.createChart("U-I Data", document, new CellRangeAddressList(list), true,
  // false, true, rect, cell);
  // // Chart chart = document.createChart("U-I Data Plot", labels, legends, data, rect);
  // chart.setUseLegend(true);
  // chart.setChartType(ChartType.SCATTER);
  // }

}
