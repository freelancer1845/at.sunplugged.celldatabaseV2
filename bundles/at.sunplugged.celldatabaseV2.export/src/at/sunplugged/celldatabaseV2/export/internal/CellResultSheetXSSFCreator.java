package at.sunplugged.celldatabaseV2.export.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.function.ToDoubleFunction;
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

    Row row;
    int i = 0;
    while (i < sheet.getLastRowNum() + 1) {
      row = sheet.getRow(i);
      if (row != null) {
        XSSFRow xssfRow = (XSSFRow) row;
        for (Cell cell : xssfRow) {

          XSSFCell xssfCell = (XSSFCell) cell;
          handleCellCellResult(xssfCell);
        }
      }
      i++;

    }


  }


  private void handleCellCellResult(Cell cell) throws IOException {
    if (cell.getCellType() != XSSFCell.CELL_TYPE_STRING) {
      return;
    }
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
    } else if (keys.get(0).equals(Keys.DARK_VOLTAGE_DATA)) {
      writeDarkVoltageData(cell);
    } else if (keys.get(0).equals(Keys.DARK_CURRENT_DATA)) {
      writeDarkCurrentData(cell);
    } else if (keys.get(0).equals(Keys.UI_PLOT)) {
      createUiPlot(cell);
    } else if (keys.get(0).equals(Keys.UI_PLOT_DARK)) {
      createUiPlotDark(cell);
    } else if (keys.get(0).equals(Keys.UI_PLOT_LIGHT_DARK)) {
      createUiPlotLightDark(cell);
    } else {
      workbook.writeValueToCell(cell, Utils.getChainedInterface(keys).applyAsDouble(cellResult));
    }
  }



  private void createUiPlotLightDark(Cell cell) throws IOException {
    if (cellResult.getLightMeasurementDataSet() != null) {
      String darkImageName = "tempImage" + new Random().nextInt() + ".jpg";
      File darkImageFile = Activator.getContext().getDataFile(darkImageName);
      JFreeChart lightDarkChart =
          PlotHelper.createJFreeChart(Arrays.asList(cellResult.getLightMeasurementDataSet(),
              cellResult.getDarkMeasuremenetDataSet()), Collections.EMPTY_MAP);

      try {
        ChartUtilities.saveChartAsJPEG(darkImageFile, 1.0f, lightDarkChart, 600, 400);
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

  private void createUiPlotDark(Cell cell) throws IOException {
    if (cellResult.getDarkMeasuremenetDataSet() != null) {
      String darkImageName = "tempImage" + new Random().nextInt() + ".jpg";
      File darkImageFile = Activator.getContext().getDataFile(darkImageName);
      JFreeChart darkChart = PlotHelper.createJFreeChart(cellResult.getDarkMeasuremenetDataSet(),
          Collections.EMPTY_MAP);
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
    JFreeChart lightChart =
        PlotHelper.createJFreeChart(cellResult.getLightMeasurementDataSet(), Collections.EMPTY_MAP);
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
    if (cellResult.getLightMeasurementDataSet() != null
        && cellResult.getLightMeasurementDataSet().getData().isEmpty() == false) {
      writeDataToCell(cell, cellResult.getLightMeasurementDataSet().getData(),
          UIDataPoint::getVoltage);
    } else {
      workbook.writeValueToCell(cell, "No Light Mesaurement data in cell result!");
    }

  }

  private void writeCurrentData(Cell cell) {
    if (cellResult.getLightMeasurementDataSet() != null
        && cellResult.getLightMeasurementDataSet().getData().isEmpty() == false) {
      writeDataToCell(cell, cellResult.getLightMeasurementDataSet().getData(),
          UIDataPoint::getCurrent);
    } else {
      workbook.writeValueToCell(cell, "No Light Mesaurement data in cell result!");
    }

  }

  private void writeDarkCurrentData(Cell cell) {
    if (cellResult.getDarkMeasuremenetDataSet() != null) {
      writeDataToCell(cell, cellResult.getDarkMeasuremenetDataSet().getData(),
          UIDataPoint::getCurrent);
    } else {
      workbook.writeValueToCell(cell, "No Dark Mesaurement data in cell result!");
    }

  }

  private void writeDarkVoltageData(Cell cell) {
    if (cellResult.getDarkMeasuremenetDataSet() != null
        && cellResult.getDarkMeasuremenetDataSet().getData().isEmpty() == false) {
      writeDataToCell(cell, cellResult.getDarkMeasuremenetDataSet().getData(),
          UIDataPoint::getVoltage);
    } else {
      workbook.writeValueToCell(cell, "No Dark Mesaurement data in cell result!");
    }

  }

  private void writeDataToCell(Cell cell, List<UIDataPoint> dataPoints,
      ToDoubleFunction<UIDataPoint> func) {
    int startRow = cell.getRowIndex();
    int col = cell.getColumnIndex();
    int rowIndex = startRow;

    for (UIDataPoint dataPoint : dataPoints) {
      XSSFCell cCell = getOrCreateCell(rowIndex, col);
      workbook.writeValueToCell(cCell, func.applyAsDouble(dataPoint));
      rowIndex++;
    }
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
