package at.sunplugged.celldatabaseV2.export.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.export.Activator;
import at.sunplugged.celldatabaseV2.plotting.PlotHelper;
import datamodel.CellResult;
import datamodel.UIDataPoint;

public class CellResultSheetXSSFCreator {

  private static final Logger LOG = LoggerFactory.getLogger(CellResultSheetXSSFCreator.class);


  public static final String TEMPLATE_SHEET = "templateResultSheet";

  private final CellResult cellResult;

  private String tableName;


  private CustomXSSFWorkbook workbook;


  private XSSFSheet sheet;



  public CellResultSheetXSSFCreator(CellResult cellResult) {
    this.cellResult = cellResult;
  }

  public void execute(CustomXSSFWorkbook workbook) throws IOException {
    this.workbook = workbook;
    long startTime = System.currentTimeMillis();
    sheet = workbook.cloneSheet(workbook.getSheetIndex(TEMPLATE_SHEET));
    workbook.setSheetName(workbook.getSheetIndex(sheet), cellResult.getName());

    for (Row row : sheet) {
      XSSFRow xssfRow = (XSSFRow) row;
      for (Cell cell : xssfRow) {
        XSSFCell xssfCell = (XSSFCell) cell;
        handleCellCellResult(xssfCell);
      }
    }


    System.out.println("Time needed: " + (System.currentTimeMillis() - startTime));
  }


  private void handleCellCellResult(Cell cell) throws IOException {
    String value = cell.getStringCellValue();
    if (value.isEmpty()) {
      return;
    }
    switch (value) {
      case Keys.NAME:
        workbook.writeValueToCell(cell, cellResult.getName());
        break;
      case Keys.AREA:
        workbook.writeValueToCell(cell, cellResult.getLightMeasurementDataSet()
            .getArea() * 10000.0);
        break;
      case Keys.EFF:
        workbook.writeValueToCell(cell, cellResult.getEfficiency());
        break;
      case Keys.FF:
        workbook.writeValueToCell(cell, cellResult.getFillFactor());
        break;
      case Keys.VOC:
        workbook.writeValueToCell(cell, cellResult.getOpenCircuitVoltage());
        break;
      case Keys.ISC:
        workbook.writeValueToCell(cell, cellResult.getShortCircuitCurrent());
        break;
      case Keys.JSC:
        workbook.writeValueToCell(cell,
            cellResult.getShortCircuitCurrent() / cellResult.getLightMeasurementDataSet()
                .getArea() * 10000.0);
        break;
      case Keys.MP:
        workbook.writeValueToCell(cell, cellResult.getMaximumPower());
        break;
      case Keys.POWER_INPUT:
        workbook.writeValueToCell(cell, cellResult.getLightMeasurementDataSet()
            .getPowerInput());
        break;
      case Keys.RP:
        workbook.writeValueToCell(cell, cellResult.getParallelResistance());
        break;
      case Keys.RP_DARK:
        workbook.writeValueToCell(cell, cellResult.getDarkParallelResistance());
        break;
      case Keys.RS:
        workbook.writeValueToCell(cell, cellResult.getSeriesResistance());
        break;
      case Keys.RS_DARK:
        workbook.writeValueToCell(cell, cellResult.getDarkSeriesResistance());
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
    File imageFile = Activator.getContext()
        .getDataFile(imageName);
    JFreeChart chart = PlotHelper.createJFreeChart(cellResult);
    try {
      ChartUtilities.saveChartAsJPEG(imageFile, 1.0f, chart, 600, 400);
    } catch (IOException e) {
      LOG.error("Failed to save Chart as file...", e);
      throw e;
    }

    BufferedInputStream in = new BufferedInputStream(new FileInputStream(imageFile));
    byte[] bytes = IOUtils.toByteArray(in);
    int imageIndex = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
    in.close();
    CreationHelper helper = workbook.getCreationHelper();
    Drawing drawing = sheet.createDrawingPatriarch();
    ClientAnchor anchor = helper.createClientAnchor();
    anchor.setCol1(cell.getColumnIndex());
    anchor.setCol2(cell.getColumnIndex() + 2);
    anchor.setRow1(cell.getRowIndex());
    anchor.setRow2(cell.getRowIndex() + 2);
    anchor.setAnchorType(3);
    Picture pic = drawing.createPicture(anchor, imageIndex);
    pic.resize();

  }



  private void writeVoltageData(Cell cell) {
    int startRow = cell.getRowIndex();
    int col = cell.getColumnIndex();
    int rowIndex = startRow;
    for (UIDataPoint dataPoint : cellResult.getLightMeasurementDataSet()
        .getData()) {
      XSSFCell cCell = getOrCreateCell(rowIndex, col);
      workbook.writeValueToCell(cCell, dataPoint.getVoltage());
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
  }

  private void writeCurrentData(Cell cell) {
    int startRow = cell.getRowIndex();
    int col = cell.getColumnIndex();
    int rowIndex = startRow;

    for (UIDataPoint dataPoint : cellResult.getLightMeasurementDataSet()
        .getData()) {
      XSSFCell cCell = getOrCreateCell(rowIndex, col);
      workbook.writeValueToCell(cCell, dataPoint.getCurrent());
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
  }

  private XSSFCell getOrCreateCell(int row, int col) {
    XSSFRow xssfRow = sheet.getRow(row);
    if (xssfRow == null) {
      xssfRow = sheet.createRow(row);
    }
    XSSFCell xssfCell = xssfRow.getCell(col);
    if (xssfCell == null) {
      xssfCell = xssfRow.createCell(col);
    }
    return xssfCell;

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
