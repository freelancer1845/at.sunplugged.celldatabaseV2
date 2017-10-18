package at.sunplugged.celldatabaseV2.export.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import at.sunplugged.celldatabaseV2.export.internal.GroupSummary;
import at.sunplugged.celldatabaseV2.export.internal.PostCalculator;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.DatamodelPackage;
import datamodel.DatamodelPackage.Literals;
import datamodel.UIDataPoint;

public class ExcelOutputHelper {

  private static final String SHEET_NAME_SUMMARY = "Summary";

  private static final String[] GROUP_ROW_NAMES =
      new String[] {"Cell", "Voc[V]", "Jsc[A/mm^2]", "Rp[ohm/mm^2]", "RpDark[ohm/mm^2]",
          "Rs[ohm/mm^2]", "RsDark[ohm/mm^2]", "MP[W/mm^2]", "Efficency[%]", "FillFactor",};

  private static final EAttribute[] LITERALS =
      new EAttribute[] {Literals.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE,
          Literals.CELL_RESULT__SHORT_CIRCUIT_CURRENT, Literals.CELL_RESULT__PARALLEL_RESISTANCE,
          Literals.CELL_RESULT__DARK_PARALLEL_RESISTANCE, Literals.CELL_RESULT__SERIES_RESISTANCE,
          Literals.CELL_RESULT__DARK_SERIES_RESISTANCE, Literals.CELL_RESULT__MAXIMUM_POWER,
          Literals.CELL_RESULT__EFFICIENCY, Literals.CELL_RESULT__FILL_FACTOR};

  private boolean executed = false;

  private final List<CellResult> cellResults;

  private final List<CellGroup> cellGroups;

  private final Map<CellGroup, GroupSummary> groupSummarys;

  private final Path path;

  private final String fileName;

  private List<EAttribute> resAttribs =
      DatamodelPackage.eINSTANCE.getCellResult().getEAllAttributes();

  private XSSFWorkbook workbook = new XSSFWorkbook();

  private CellStyle dateCellStyle;

  private CellStyle headerCellStyle;
  {
    CellStyle cellStyle = workbook.createCellStyle();
    CreationHelper createHelper = workbook.getCreationHelper();
    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
    dateCellStyle = cellStyle;
    headerCellStyle = workbook.createCellStyle();

    headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
  }

  private XSSFSheet summarySheet;

  public ExcelOutputHelper(List<CellResult> cellResults, String fileName) {
    this.cellResults =
        cellResults.stream().map(result -> EcoreUtil.copy(result)).collect(Collectors.toList());
    this.fileName = fileName;

    this.cellGroups = null;
    this.groupSummarys = new HashMap<>();
    this.path = null;
  }

  public ExcelOutputHelper(List<CellGroup> cellGroups, Path path) {
    this.cellGroups =
        cellGroups.stream().map(group -> EcoreUtil.copy(group)).collect(Collectors.toList());
    this.path = path;

    this.groupSummarys = this.cellGroups.stream()
        .collect(Collectors.toMap(group -> group, group -> new GroupSummary(group)));

    this.fileName = null;
    this.cellResults = null;
  }

  public void execute() {
    if (executed == true) {
      throw new IllegalStateException(
          "Use a new instance of ExcelOutputHelper!!! Already executed...");
    }
    executed = true;
    Job job = new Job("ExcelOutput Job") {
      protected IStatus run(IProgressMonitor monitor) {

        if (cellGroups != null) {
          createGroupsSummaryFile(monitor);
        } else if (cellResults != null) {
          createResultsSummaryFile(monitor);
        }


        return Status.OK_STATUS;

      }



    };

    job.setPriority(Job.INTERACTIVE);
    job.schedule();
  }

  private void createGroupsSummaryFile(IProgressMonitor monitor) {
    monitor.beginTask("Export Group Summarys", 5);

    String[] rowNames = new String[] {"Group", "Voc[V]", "VocSTD[V]", "Jsc[A/mm^2]",
        "JscSTD[A/mm^2]", "Rp[ohm/mm^2]", "RpSTD[ohm/mm^2]", "RpDark[ohm/mm^2]",
        "RpDarkSTD[ohm/mm^2]", "Rs[ohm/mm^2]", "RsSTD[ohm/mm^2]", "RsDark[ohm/mm^2]",
        "RsDarkSTD[ohm/mm^2]", "MP[W/mm^2]", "MPSTD[W/mm^2]", "Efficency[%]", "EfficencySTD[%]",
        "FillFactor", "FillFactorSTD"};


    monitor.subTask("Creating Summary Sheet");

    summarySheet = workbook.createSheet(SHEET_NAME_SUMMARY);

    XSSFRow cRow;
    XSSFCell cCell;
    int rowId = 0;
    int colId = 0;

    cRow = summarySheet.createRow(rowId++);

    for (String row : rowNames) {
      cCell = cRow.createCell(colId++);
      cCell.setCellValue(row);
    }
    colId = 0;

    for (CellGroup group : cellGroups) {
      colId = 0;
      XSSFSheet groupSheet = workbook.createSheet(group.getName());
      createSingleGroupSheet(groupSheet, group);



      cRow = summarySheet.createRow(rowId++);

      cRow.createCell(colId++).setCellValue(group.getName());
      GroupSummary summary = groupSummarys.get(group);

      for (EAttribute literal : LITERALS) {
        if (literal == Literals.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE
            || literal == Literals.CELL_RESULT__EFFICIENCY
            || literal == Literals.CELL_RESULT__FILL_FACTOR) {
          writeValueToCell(cRow.createCell(colId++),
              summary.getAverage(literal, (value, result) -> value), literal);

          writeValueToCell(cRow.createCell(colId++),
              summary.getStd(literal, (value, result) -> value), literal);

        } else {
          writeValueToCell(cRow.createCell(colId++), summary.getAverage(literal,
              (value, result) -> value / result.getLightMeasurementDataSet().getArea() / 1000000),
              literal);
          writeValueToCell(cRow.createCell(colId++), summary.getStd(literal,
              (value, result) -> value / result.getLightMeasurementDataSet().getArea() / 1000000),
              literal);
        }
      }

    }


    writeWorkbook(path.toString(), workbook);
  };

  private void createSingleGroupSheet(XSSFSheet sheet, CellGroup cellGroup) {


    XSSFRow cRow;
    XSSFCell cCell;
    int rowId = 0;
    int colId = 0;


    cRow = sheet.createRow(rowId++);
    for (String rowName : GROUP_ROW_NAMES) {
      sheet.getColumnHelper().setColWidth(colId, 18);
      cCell = cRow.createCell(colId++);
      cCell.setCellValue(rowName);
      cCell.setCellStyle(headerCellStyle);
    }

    for (CellResult result : cellGroup.getCellResults()) {
      cRow = sheet.createRow(rowId++);
      colId = 0;
      writeValueToCell(cRow.createCell(colId++), result.getName());
      writeValueToCell(cRow.createCell(colId++), result.getOpenCircuitVoltage(),
          Literals.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE);
      writeValueToCell(cRow.createCell(colId++),
          result.getShortCircuitCurrent() / result.getLightMeasurementDataSet().getArea() / 1000000,
          Literals.CELL_RESULT__SHORT_CIRCUIT_CURRENT);
      writeValueToCell(cRow.createCell(colId++),
          result.getParallelResistance() / result.getLightMeasurementDataSet().getArea() / 1000000,
          Literals.CELL_RESULT__PARALLEL_RESISTANCE);
      writeValueToCell(
          cRow.createCell(colId++), result.getDarkParallelResistance()
              / result.getLightMeasurementDataSet().getArea() / 1000000,
          Literals.CELL_RESULT__DARK_PARALLEL_RESISTANCE);
      writeValueToCell(cRow.createCell(colId++),
          result.getSeriesResistance() / result.getLightMeasurementDataSet().getArea() / 1000000,
          Literals.CELL_RESULT__SERIES_RESISTANCE);
      writeValueToCell(
          cRow.createCell(colId++), result.getDarkSeriesResistance()
              / result.getLightMeasurementDataSet().getArea() / 1000000,
          Literals.CELL_RESULT__DARK_SERIES_RESISTANCE);
      writeValueToCell(cRow.createCell(colId++),
          result.getMaximumPower() / result.getLightMeasurementDataSet().getArea() / 1000000,
          Literals.CELL_RESULT__MAXIMUM_POWER);
      writeValueToCell(cRow.createCell(colId++), result.getEfficiency(),
          Literals.CELL_RESULT__EFFICIENCY);
      writeValueToCell(cRow.createCell(colId++), result.getFillFactor(),
          Literals.CELL_RESULT__FILL_FACTOR);
    }
    cRow = sheet.createRow(rowId++);
    XSSFRow averageRow = sheet.createRow(rowId++);
    XSSFRow stdRow = sheet.createRow(rowId++);

    colId = 0;
    averageRow.createCell(colId).setCellValue("Average");
    stdRow.createCell(colId++).setCellValue("Std");

    GroupSummary summary = groupSummarys.get(cellGroup);



    for (EAttribute literal : LITERALS) {
      if (literal == Literals.CELL_RESULT__OPEN_CIRCUIT_VOLTAGE
          || literal == Literals.CELL_RESULT__EFFICIENCY
          || literal == Literals.CELL_RESULT__FILL_FACTOR) {
        createAverageAndStdRowForLiteral(averageRow, stdRow, colId++, summary, literal,
            (value, result) -> value);
      } else {
        createAverageAndStdRowForLiteral(averageRow, stdRow, colId++, summary, literal,
            (value, result) -> value / result.getLightMeasurementDataSet().getArea() / 1000000);
      }

    }
  }

  private void createAverageAndStdRowForLiteral(XSSFRow averageRow, XSSFRow stdRow, int colId,
      GroupSummary summary, EAttribute attribute, PostCalculator postCalculator) {

    writeValueToCell(averageRow.createCell(colId), summary.getAverage(attribute, postCalculator),
        attribute);
    writeValueToCell(stdRow.createCell(colId), summary.getStd(attribute, postCalculator),
        attribute);
  }



  private void createResultsSummaryFile(IProgressMonitor monitor) {
    monitor.subTask("Creating Summary Sheet");


    CellGroup tempGroup = DatamodelFactory.eINSTANCE.createCellGroup();
    tempGroup.setName("Summary");
    tempGroup.getCellResults().addAll(cellResults);

    groupSummarys.put(tempGroup, new GroupSummary(tempGroup));


    createSingleGroupSheet(workbook.createSheet(SHEET_NAME_SUMMARY), tempGroup);

    monitor.worked(1);

    monitor.subTask("Creating Result Sheets");

    cellResults.stream().forEach(res -> {
      createCellResultSheet(res);
      monitor.worked(1);
    });

    monitor.subTask("Writing to hard drive...");

    writeWorkbook(fileName, workbook);
    monitor.worked(1);

    monitor.done();

  }

  private void createSummarySheet() {
    summarySheet = workbook.createSheet(SHEET_NAME_SUMMARY);

    XSSFRow cRow;
    XSSFCell cCell;

    int rowId = 0;
    int colId = 0;

    cRow = summarySheet.createRow(rowId++);

    for (EAttribute attrib : resAttribs) {
      cCell = cRow.createCell(colId++);
      cCell.setCellValue(attrib.getName());
    }

    for (CellResult res : cellResults) {
      cRow = summarySheet.createRow(rowId++);
      colId = 0;
      for (EAttribute attrib : resAttribs) {
        cCell = cRow.createCell(colId++);
        writeValueToCell(cCell, res.eGet(attrib), attrib);

      }
    }

    for (int i = 0; i < 15; i++) {
      summarySheet.getColumnHelper().setColWidth(i, 18);
    }
  }

  private void createCellResultSheet(CellResult res) {

    XSSFSheet sheet = workbook.createSheet(res.getName());

    int rowId = 0;
    int colId = 0;
    XSSFRow nameRow = sheet.createRow(rowId++);
    XSSFRow valueRow = sheet.createRow(rowId++);

    XSSFCell cCell;

    for (EAttribute attr : resAttribs) {
      cCell = nameRow.createCell(colId);
      cCell.setCellStyle(headerCellStyle);
      writeValueToCell(cCell, attr.getName());
      cCell = valueRow.createCell(colId++);
      writeValueToCell(cCell, res.eGet(attr), attr);
    }

    colId = 0;

    CellMeasurementDataSet dataSet = res.getLightMeasurementDataSet();
    if (dataSet == null) {
      return;
    }

    rowId++;
    XSSFRow seperatorRow = sheet.createRow(rowId++);
    cCell = seperatorRow.createCell(0);
    cCell.setCellValue("Measurement Data");
    cCell.setCellStyle(headerCellStyle);

    nameRow = sheet.createRow(rowId++);
    valueRow = sheet.createRow(rowId++);
    for (EAttribute attr : DatamodelPackage.eINSTANCE.getCellMeasurementDataSet()
        .getEAttributes()) {
      cCell = nameRow.createCell(colId);
      cCell.setCellStyle(headerCellStyle);
      writeValueToCell(cCell, attr.getName());
      cCell = valueRow.createCell(colId++);
      writeValueToCell(cCell, dataSet.eGet(attr), attr);

    }

    rowId++;
    XSSFRow seperatorRow2 = sheet.createRow(rowId++);
    cCell = seperatorRow2.createCell(0);
    cCell.setCellValue("Measured Data");
    cCell.setCellStyle(headerCellStyle);

    XSSFRow dataNameRow = sheet.createRow(rowId++);

    cCell = dataNameRow.createCell(0);
    cCell.setCellStyle(headerCellStyle);
    cCell.setCellValue("Voltage[V]");
    cCell = dataNameRow.createCell(1);
    cCell.setCellStyle(headerCellStyle);
    cCell.setCellValue("Current [A]");

    XSSFRow cRow;
    for (UIDataPoint dataPoint : dataSet.getData()) {
      cRow = sheet.createRow(rowId++);
      cCell = cRow.createCell(0);
      cCell.setCellValue(dataPoint.getVoltage());
      cCell = cRow.createCell(1);
      cCell.setCellValue(dataPoint.getCurrent());
    }

    for (int i = 0; i < 15; i++) {
      sheet.getColumnHelper().setColWidth(i, 18);
    }

  }

  private void writeWorkbook(String filePath, XSSFWorkbook workbook) {
    try (FileOutputStream out = new FileOutputStream(new File(filePath))) {

      workbook.write(out);

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void writeValueToCell(XSSFCell cell, Object value) {
    writeValueToCell(cell, value, null);
  }

  private void writeValueToCell(XSSFCell cell, Object value, EAttribute attr) {
    if (value != null) {
      if (attr != null) {
        if (attr.getEAttributeType().equals(EcorePackage.Literals.EDOUBLE)) {
          cell.setCellValue((double) value);
        } else if (attr.getEAttributeType().equals(EcorePackage.Literals.EDATE)) {

          Date date = (Date) value;
          cell.setCellValue((Date) date);
          cell.setCellStyle(dateCellStyle);
        } else {
          cell.setCellValue(value.toString());
        }
      } else {
        cell.setCellValue(value.toString());
      }

    } else {
      cell.setCellValue("");
    }
  }

}

