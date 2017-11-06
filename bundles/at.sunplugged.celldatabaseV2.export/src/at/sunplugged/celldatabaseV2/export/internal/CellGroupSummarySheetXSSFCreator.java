package at.sunplugged.celldatabaseV2.export.internal;

import java.util.Vector;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import datamodel.CellGroup;
import datamodel.CellResult;

public class CellGroupSummarySheetXSSFCreator {

  public static final String CELL_GROUP_TEMPLATE_TABLE_NAME = "TemplateCellGroup";

  private static final Logger LOG = LoggerFactory.getLogger(CellGroupSummarySheetXSSFCreator.class);

  private CellGroup cellGroup;

  private XSSFSheet sheet;

  private String tableName;

  private Pattern keyPattern = Pattern.compile("#[a-zA-Z0-9]+");

  private CustomXSSFWorkbook workbook;


  public CellGroupSummarySheetXSSFCreator(CellGroup cellGroup, CustomXSSFWorkbook workbook) {
    this.workbook = workbook;
    this.cellGroup = cellGroup;
    sheet = workbook.cloneSheet(workbook.getSheetIndex(CELL_GROUP_TEMPLATE_TABLE_NAME));
    workbook.setSheetName(workbook.getSheetIndex(sheet), cellGroup.getName());
  }


  public void execute() {

    for (Row row : sheet) {
      for (Cell cell : row) {
        XSSFCell xssfCell = (XSSFCell) cell;
        handleCellCellGroup(xssfCell);
      }
    }

  }


  private void handleCellCellGroup(Cell cell) {
    Vector<String> keys = Utils.decodeKey(cell.getStringCellValue());

    if (keys.size() == 0) {
      return;
    }
    if (keys.get(0)
        .equals(Keys.NAME)) {
      writeStringAttributeToCellColumn(cell, result -> result.getName());
    } else if (keys.get(0)
        .equals(Keys.GROUP_NAME)) {
      cell.setCellValue(cellGroup.getName());
    } else {
      writeDoubleAttributetoCellColumn(cell, getInterface(keys));
    }

  }



  private ToDoubleFunction<CellResult> getInterface(Vector<String> keys) {
    if (keys.size() == 1) {
      return getMapperSingleKey(keys.get(0));
    } else if (keys.size() == 2) {
      ToDoubleFunction<CellResult> singleKey = getMapperSingleKey(keys.get(0));
      switch (keys.get(1)) {
        case Keys.DIVIDE_BY_AREA:
          return result -> singleKey.applyAsDouble(result) / result.getLightMeasurementDataSet()
              .getArea() / 10000;
        default:
          throw new IllegalArgumentException("Unexpected second Key: " + keys.get(1));
      }
    } else {
      throw new IllegalArgumentException("More than two Keys supplied... Only three allowed");
    }
  }

  private ToDoubleFunction<CellResult> getMapperSingleKey(String key) {
    return Utils.getValueGetter(key);
  }

  private void writeStringAttributeToCellColumn(Cell cell, Function<CellResult, String> function) {
    int columnIndex = cell.getColumnIndex();
    int currentRow = cell.getRowIndex();

    Cell currentCell;
    for (CellResult result : cellGroup.getCellResults()) {
      currentCell = getOrCreateCell(currentRow++, columnIndex);
      workbook.writeValueToCell(currentCell, function.apply(result));
    }

  }

  private void writeDoubleAttributetoCellColumn(Cell cell,
      ToDoubleFunction<CellResult> valueMapper) {
    int columnIndex = cell.getColumnIndex();
    int currentRow = cell.getRowIndex();

    Cell currentCell;
    for (CellResult result : cellGroup.getCellResults()) {
      currentCell = getOrCreateCell(currentRow++, columnIndex);
      workbook.writeValueToCell(currentCell, valueMapper.applyAsDouble(result));
    }


    currentRow++;
    currentRow++;

    getOrCreateCell(currentRow, 0).setCellValue("Mean");

    currentCell = getOrCreateCell(currentRow++, columnIndex);
    workbook.writeValueToCell(currentCell, SummaryFunctions.getAverage(cellGroup, valueMapper));

    getOrCreateCell(currentRow, 0).setCellValue("Std");
    currentCell = getOrCreateCell(currentRow++, columnIndex);
    workbook.writeValueToCell(currentCell, SummaryFunctions.getStd(cellGroup, valueMapper));

    getOrCreateCell(currentRow, 0).setCellValue("Max");
    currentCell = getOrCreateCell(currentRow++, columnIndex);
    workbook.writeValueToCell(currentCell, SummaryFunctions.getMax(cellGroup, valueMapper));
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
