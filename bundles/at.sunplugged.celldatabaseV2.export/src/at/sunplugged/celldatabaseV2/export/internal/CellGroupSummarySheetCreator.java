package at.sunplugged.celldatabaseV2.export.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import datamodel.CellGroup;
import datamodel.CellResult;

public class CellGroupSummarySheetCreator {

  public static final String CELL_GROUP_TEMPLATE_TABLE_NAME = "TemplateCellGroup";

  private static final Logger LOG = LoggerFactory.getLogger(CellGroupSummarySheetCreator.class);

  private CellGroup cellGroup;

  private Table table;

  private String tableName;

  private Pattern keyPattern = Pattern.compile("#[a-zA-Z0-9]+");

  public CellGroupSummarySheetCreator(CellGroup cellGroup, SpreadsheetDocument document) {
    this.cellGroup = cellGroup;

    Table templateTable = document.getTableByName(CELL_GROUP_TEMPLATE_TABLE_NAME);
    table = document.insertSheet(templateTable, document.getSheetCount() - 1);
    tableName = cellGroup.getName();
    System.out.println(tableName);
  }


  public void execute() {
    table.setTableName(tableName);

    List<Row> copyRows = new ArrayList<>(table.getRowList());
    int colCount = copyRows.get(0)
        .getCellCount();
    copyRows.forEach(row -> {

      for (int col = 0; col < colCount; col++) {

        Cell cell = row.getCellByIndex(col);
        handleCellCellGroup(cell);
      }
    });

  }


  private void handleCellCellGroup(Cell cell) {
    Matcher matcher = keyPattern.matcher(cell.getStringValue());
    Vector<String> keys = new Vector<>();
    while (matcher.find()) {
      keys.add(matcher.group());
    }

    if (keys.size() == 0) {
      return;
    }
    if (keys.get(0)
        .equals(Keys.NAME)) {
      writeStringAttributeToCellColumn(cell, result -> result.getName());
    } else if (keys.get(0)
        .equals(Keys.GROUP_NAME)) {
      cell.setStringValue(cellGroup.getName());
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
    switch (key) {
      case Keys.VOC:
        return result -> result.getOpenCircuitVoltage();
      case Keys.ISC:
        return result -> result.getSeriesResistance();
      case Keys.JSC:
        return result -> result.getSeriesResistance() / result.getLightMeasurementDataSet()
            .getArea() / 10000;
      case Keys.RP:
        return result -> result.getParallelResistance();
      case Keys.RP_DARK:
        return result -> result.getDarkParallelResistance();
      case Keys.RS:
        return result -> result.getSeriesResistance();
      case Keys.RS_DARK:
        return result -> result.getDarkSeriesResistance();
      case Keys.MP:
        return result -> result.getMaximumPower();
      case Keys.EFF:
        return result -> result.getEfficiency();
      case Keys.FF:
        return result -> result.getFillFactor();
      default:
        throw new IllegalArgumentException("Unhandled Key: " + key);
    }
  }

  private void writeStringAttributeToCellColumn(Cell cell, Function<CellResult, String> function) {
    int columnIndex = cell.getColumnIndex();
    int currentRow = cell.getRowIndex();

    Cell currentCell;
    for (CellResult result : cellGroup.getCellResults()) {
      currentCell = table.getCellByPosition(columnIndex, currentRow++);
      currentCell.setStringValue(function.apply(result));
    }

  }

  private void writeDoubleAttributetoCellColumn(Cell cell,
      ToDoubleFunction<CellResult> valueMapper) {
    int columnIndex = cell.getColumnIndex();
    int currentRow = cell.getRowIndex();

    Cell currentCell;
    for (CellResult result : cellGroup.getCellResults()) {
      currentCell = table.getCellByPosition(columnIndex, currentRow++);
      currentCell.setDoubleValue(valueMapper.applyAsDouble(result));
    }

    table.getCellByPosition(0, currentRow)
        .setStringValue("Mean");
    currentCell = table.getCellByPosition(columnIndex, currentRow++);
    currentCell.setDoubleValue(SummaryFunctions.getAverage(cellGroup, valueMapper));

    table.getCellByPosition(0, currentRow)
        .setStringValue("Std");
    currentCell = table.getCellByPosition(columnIndex, currentRow++);
    currentCell.setDoubleValue(SummaryFunctions.getStd(cellGroup, valueMapper));

    table.getCellByPosition(0, currentRow)
        .setStringValue("Max");
    currentCell = table.getCellByPosition(columnIndex, currentRow++);
    currentCell.setDoubleValue(SummaryFunctions.getMax(cellGroup, valueMapper));
  }



}
