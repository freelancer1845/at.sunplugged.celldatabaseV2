package at.sunplugged.celldatabaseV2.export.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
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

  private CustomXSSFWorkbook workbook;

  private XSSFSheet sheet;

  public CellResultSheetXSSFCreator(CellResult cellResult) {
    this.cellResult = cellResult;
  }

  public void execute(CustomXSSFWorkbook workbook) throws IOException {
    this.workbook = workbook;
    sheet = workbook.cloneSheet(workbook.getSheetIndex(TEMPLATE_SHEET));
    workbook.setSheetName(workbook.getSheetIndex(sheet), cellResult.getName());

    for (Row row : sheet) {
      XSSFRow xssfRow = (XSSFRow) row;
      for (Cell cell : xssfRow) {
        XSSFCell xssfCell = (XSSFCell) cell;
        handleCellCellResult(xssfCell);
      }
    }


  }


  private void handleCellCellResult(Cell cell) throws IOException {

    Vector<String> keys = Utils.decodeKey(cell.getStringCellValue());

    if (keys.size() == 0) {
      return;
    }
    if (keys.get(0).equals(Keys.NAME)) {
      workbook.writeValueToCell(cell, cellResult.getName());
    } else if (keys.get(0).equals(Keys.VOLTAGE_DATA)) {
      writeVoltageData(cell);
    } else if (keys.get(0).equals(Keys.CURRENT_DATA)) {
      writeCurrentData(cell);
    } else if (keys.get(0).equals(Keys.UI_PLOT)) {
      createUiPlot(cell);
    } else if (keys.get(0).equals(Keys.UI_PLOT_DARK)) {
      createUiPlotDark(cell);
    } else {
      workbook.writeValueToCell(cell, Utils.getChainedInterface(keys).applyAsDouble(cellResult));
    }
  }

  private void createUiPlotDark(Cell cell) throws IOException {
    if (cellResult.getDarkMeasuremenetDataSet() != null) {
      String darkImageName = "tempImage" + new Random().nextInt() + ".jpg";
      File darkImageFile = Activator.getContext().getDataFile(darkImageName);
      JFreeChart darkChart = PlotHelper.createJFreeChart(cellResult.getDarkMeasuremenetDataSet());
      try {
        ChartUtilities.saveChartAsJPEG(darkImageFile, 1.0f, darkChart, 600, 400);
      } catch (IOException e) {
        LOG.error("Failed to save Chart as file...", e);
        throw e;
      }
      BufferedInputStream in = new BufferedInputStream(new FileInputStream(darkImageFile));
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
  }

  private void createUiPlot(Cell cell) throws IOException {
    String imageName = "tempImage" + new Random().nextInt() + ".jpg";
    File imageFile = Activator.getContext().getDataFile(imageName);
    JFreeChart lightChart = PlotHelper.createJFreeChart(cellResult.getLightMeasurementDataSet());
    try {
      ChartUtilities.saveChartAsJPEG(imageFile, 1.0f, lightChart, 600, 400);
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

    imageFile.delete();

  }



  private void writeVoltageData(Cell cell) {
    int startRow = cell.getRowIndex();
    int col = cell.getColumnIndex();
    int rowIndex = startRow;
    for (UIDataPoint dataPoint : cellResult.getLightMeasurementDataSet().getData()) {
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

    for (UIDataPoint dataPoint : cellResult.getLightMeasurementDataSet().getData()) {
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


}
