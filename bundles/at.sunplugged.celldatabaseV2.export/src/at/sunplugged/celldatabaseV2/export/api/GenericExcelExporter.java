package at.sunplugged.celldatabaseV2.export.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.runtime.FileLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.export.Activator;
import at.sunplugged.celldatabaseV2.export.internal.Keys;
import datamodel.CellResult;
import datamodel.UIDataPoint;

public class GenericExcelExporter {

  /** Identifier of the CellResult Sheet. */
  private static final String CELL_RESULT_SHEET_ID = "CellResult";

  private static final Logger LOG = LoggerFactory.getLogger(GenericExcelExporter.class);

  private final String outputFileName;

  private String cellResultTemplate = "resources/cellResultTemplate.xlsx";

  private List<CellResult> cellResults;

  private XSSFWorkbook workbook;

  private CellStyle dateCellStyle;

  private CellStyle headerCellStyle;

  private CellStyle doubleCellStyle;

  {
    CellStyle cellStyle = workbook.createCellStyle();
    CreationHelper createHelper = workbook.getCreationHelper();
    cellStyle.setDataFormat(createHelper.createDataFormat()
        .getFormat("m/d/yy h:mm"));
    dateCellStyle = cellStyle;
    headerCellStyle = workbook.createCellStyle();

    headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);

    cellStyle = workbook.createCellStyle();
    cellStyle.setDataFormat(createHelper.createDataFormat()
        .getFormat("0,000E+00"));
    doubleCellStyle = cellStyle;
  }



  public GenericExcelExporter(String outputFileName, List<CellResult> cellResults) {
    this.outputFileName = outputFileName;
    this.cellResults = cellResults;
  }

  public void execute() {
    try {
      workbook = createRawTemplatedWorkbook();
    } catch (IOException e) {
      LOG.error("Failed to create RawTemplatedWork.", e);
      return;
    }

    createCellResultSummarySheets(workbook, cellResults);

    workbook.removeSheetAt(workbook.getSheetIndex(CELL_RESULT_SHEET_ID));

    try (FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
      workbook.write(outputStream);
    } catch (IOException e) {
      LOG.error("Failed to write Workbook...", e);
      return;
    }
  }

  private void createCellResultSummarySheets(XSSFWorkbook workbook, List<CellResult> cellResults) {
    for (CellResult cellResult : cellResults) {
      Sheet sheet = workbook.cloneSheet(workbook.getSheetIndex(CELL_RESULT_SHEET_ID));
      workbook.setSheetName(workbook.getSheetIndex(sheet), cellResult.getName());
      for (Row row : sheet) {
        for (Cell cell : row) {
          handleCellCellResult(cell, cellResult);
        }
      }
    }

  }

  private void handleCellCellResult(Cell cell, CellResult cellResult) {
    String value = cell.getStringCellValue();
    if (value.isEmpty()) {
      return;
    }
    switch (value) {
      case Keys.NAME:
        writeCellValue(cell, cellResult.getName());
        break;
      case Keys.AREA:
        writeCellValue(cell, cellResult.getLightMeasurementDataSet()
            .getArea() * 10000.0);
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
        writeCellValue(cell,
            cellResult.getShortCircuitCurrent() / cellResult.getLightMeasurementDataSet()
                .getArea() * 10000.0);
        break;
      case Keys.MP:
        writeCellValue(cell, cellResult.getMaximumPower());
        break;
      case Keys.POWER_INPUT:
        writeCellValue(cell, cellResult.getLightMeasurementDataSet()
            .getPowerInput());
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
        writeVoltageData(cell, cellResult);
        break;
      case Keys.CURRENT_DATA:
        writeCurrentData(cell, cellResult);
        break;
      default:
        break;
    }



  }

  private void writeVoltageData(Cell cell, CellResult cellResult) {
    Sheet sheet = cell.getSheet();
    int startRow = cell.getRowIndex();
    int col = cell.getColumnIndex();

    for (UIDataPoint dataPoint : cellResult.getLightMeasurementDataSet()
        .getData()) {
      Row row = sheet.getRow(startRow);
      if (row == null) {
        row = sheet.createRow(startRow);
      }
      Cell cCell = row.createCell(col);
      writeCellValue(cCell, dataPoint.getVoltage());
      startRow++;
    }
  }

  private void writeCurrentData(Cell cell, CellResult cellResult) {
    Sheet sheet = cell.getSheet();
    int startRow = cell.getRowIndex();
    int col = cell.getColumnIndex();

    for (UIDataPoint dataPoint : cellResult.getLightMeasurementDataSet()
        .getData()) {
      Row row = sheet.getRow(startRow);
      if (row == null) {
        row = sheet.createRow(startRow);
      }
      Cell cCell = row.createCell(col);
      writeCellValue(cCell, dataPoint.getCurrent());

      startRow++;
    }
  }

  private void writeCellValue(Cell cell, Object value) {
    if (value instanceof Double) {
      cell.setCellValue((double) value);
      cell.setCellStyle(doubleCellStyle);
    } else {
      cell.setCellValue(value.toString());
    }
  }

  private XSSFWorkbook createRawTemplatedWorkbook() throws IOException {
    File cellResultTemplateFile;
    XSSFWorkbook workbook;
    cellResultTemplateFile = new File(Paths.get(FileLocator.getBundleFile(Activator.getContext()
        .getBundle())
        .getAbsolutePath(), cellResultTemplate)
        .toString());
    File outputFile = new File(outputFileName);
    if (outputFile.exists()) {
      outputFile.delete();
    }

    outputFile.createNewFile();
    if (cellResultTemplateFile.exists() == true) {
      try (FileInputStream fSource = new FileInputStream(cellResultTemplateFile);
          FileOutputStream fTarget = new FileOutputStream(outputFile)) {
        fTarget.getChannel()
            .transferFrom(fSource.getChannel(), 0, fSource.getChannel()
                .size());
      } catch (IOException e) {
        throw e;
      }
    } else {
      throw new IOException("Failed to find template file...");
    }

    try (FileInputStream inputStream = new FileInputStream(outputFile)) {
      workbook = new XSSFWorkbook(inputStream);
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException e) {
      throw e;
    }
    return workbook;
  }


}
