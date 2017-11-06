package at.sunplugged.celldatabaseV2.export.internal;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CustomXSSFWorkbook extends XSSFWorkbook {

  private CellStyle dateCellStyle;

  private CellStyle headerCellStyle;

  private CellStyle doubleCellStyle;

  private CellStyle samllDoubleCellStyle;
  {
    CellStyle cellStyle = createCellStyle();
    CreationHelper createHelper = getCreationHelper();
    cellStyle.setDataFormat(createHelper.createDataFormat()
        .getFormat("m/d/yy h:mm"));
    dateCellStyle = cellStyle;
    headerCellStyle = createCellStyle();

    headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);

    cellStyle = createCellStyle();
    cellStyle.setDataFormat(createHelper.createDataFormat()
        .getFormat("0.000"));
    doubleCellStyle = cellStyle;
    cellStyle = createCellStyle();
    cellStyle.setDataFormat(createHelper.createDataFormat()
        .getFormat("0.000E+00"));
    samllDoubleCellStyle = cellStyle;
  }


  public CustomXSSFWorkbook() {
    super();
    setup();
  }

  public CustomXSSFWorkbook(InputStream is) throws IOException {
    super(is);
    setup();
  }

  public CustomXSSFWorkbook(OPCPackage pkg) throws IOException {
    super(pkg);

    setup();
  }

  private void setup() {
    this.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
  }


  public void writeValueToCell(Cell cell, Object value) {
    if (value instanceof Double) {
      Double doubleValue = (Double) value;
      if (Math.abs(doubleValue) > 0.01) {
        cell.setCellStyle(doubleCellStyle);
      } else {
        cell.setCellStyle(samllDoubleCellStyle);
      }
      cell.setCellValue(doubleValue);
    } else if (value instanceof String) {
      cell.setCellStyle(headerCellStyle);
      cell.setCellValue((String) value);
    }
  }


}
