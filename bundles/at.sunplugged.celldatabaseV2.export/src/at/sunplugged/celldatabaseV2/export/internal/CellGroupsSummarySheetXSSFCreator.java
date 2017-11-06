package at.sunplugged.celldatabaseV2.export.internal;

import java.util.Collection;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import datamodel.CellGroup;
import datamodel.CellResult;

public class CellGroupsSummarySheetXSSFCreator {

  public static final String CELL_GROUPS_TEMPLATE = "CellGroupsTemplate";

  private Collection<CellGroup> cellGroups;

  private CustomXSSFWorkbook workbook;

  private XSSFSheet sheet;

  public CellGroupsSummarySheetXSSFCreator(Collection<CellGroup> cellGroups,
      CustomXSSFWorkbook workbook) {
    this.workbook = workbook;
    this.cellGroups = cellGroups;
    sheet = workbook.cloneSheet(workbook.getSheetIndex(CELL_GROUPS_TEMPLATE));
    workbook.setSheetName(workbook.getSheetIndex(sheet), "Groups");

  }

  public void execute() {
    for (Row row : sheet) {
      for (Cell cell : row) {
        XSSFCell xssfCell = (XSSFCell) cell;
        handleCell(xssfCell);
      }
    }
  }

  private void handleCell(XSSFCell cell) {
    Vector<String> keys = Utils.decodeKey(cell.getStringCellValue());
    if (keys.size() == 0) {
      return;
    }
    switch (keys.get(0)) {
      case Keys.GROUP_NAME:
      case Keys.NAME:
        writeCellColumnString(cell, group -> group.getName());
        break;
      default:
        ToDoubleFunction<CellResult> finalValueGetter;
        ToDoubleFunction<CellResult> valueGetter = Utils.getValueGetter(keys.get(0));
        if (keys.contains(Keys.DIVIDE_BY_AREA)) {
          finalValueGetter =
              result -> valueGetter.applyAsDouble(result) / result.getLightMeasurementDataSet()
                  .getArea() / 10000.0;
          keys.removeElement(Keys.DIVIDE_BY_AREA);
        } else {
          finalValueGetter = valueGetter;
        }
        if (keys.size() > 1) {
          switch (keys.get(1)) {
            case Keys.MAX:
              writeCellColumnDouble(cell, group -> group.getCellResults()
                  .stream()
                  .mapToDouble(finalValueGetter)
                  .max()
                  .getAsDouble());
              break;
            case Keys.STD:
              writeCellColumnDouble(cell, group -> {
                StandardDeviation std = new StandardDeviation();

                double mean = group.getCellResults()
                    .stream()
                    .mapToDouble(finalValueGetter)
                    .average()
                    .getAsDouble();
                double[] values = group.getCellResults()
                    .stream()
                    .mapToDouble(finalValueGetter)
                    .toArray();
                return std.evaluate(values, mean);
              });
              break;
            default:
              throw new IllegalArgumentException("Unexpected second Key: " + keys.get(1));
          }
        } else {
          writeCellColumnDouble(cell, group -> group.getCellResults()
              .stream()
              .mapToDouble(finalValueGetter)
              .average()
              .getAsDouble());
        }
        break;

    }
  }


  private void writeCellColumnString(XSSFCell cell, Function<CellGroup, String> function) {
    int colIndex = cell.getColumnIndex();
    int rowIndex = cell.getRowIndex();

    for (CellGroup group : cellGroups) {
      workbook.writeValueToCell(getOrCreateCell(rowIndex++, colIndex), function.apply(group));
    }
  }

  private void writeCellColumnDouble(XSSFCell cell, ToDoubleFunction<CellGroup> function) {
    int colIndex = cell.getColumnIndex();
    int rowIndex = cell.getRowIndex();
    for (CellGroup group : cellGroups) {
      workbook.writeValueToCell(getOrCreateCell(rowIndex++, colIndex),
          function.applyAsDouble(group));
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
