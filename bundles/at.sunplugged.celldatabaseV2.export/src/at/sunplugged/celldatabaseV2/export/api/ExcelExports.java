package at.sunplugged.celldatabaseV2.export.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.emf.ecore.util.EcoreUtil;
import at.sunplugged.celldatabaseV2.common.FileUtils;
import at.sunplugged.celldatabaseV2.export.internal.CellGroupSummarySheetXSSFCreator;
import at.sunplugged.celldatabaseV2.export.internal.CellGroupsSummarySheetXSSFCreator;
import at.sunplugged.celldatabaseV2.export.internal.CellResultSheetXSSFCreator;
import at.sunplugged.celldatabaseV2.export.internal.CustomXSSFWorkbook;
import datamodel.CellGroup;
import datamodel.CellResult;
import datamodel.DatamodelFactory;

public class ExcelExports {

  private static String cellResultTemplate = "excelTemplate/template.xlsx";

  public static void exportCellResults(String outputFileName, List<CellResult> _results)
      throws IOException {

    Collection<CellResult> results = EcoreUtil.copyAll(_results);
    CustomXSSFWorkbook workbook = createRawTemplatedWorkbook(outputFileName);


    CellGroup cellGroup = DatamodelFactory.eINSTANCE.createCellGroup();
    cellGroup.getCellResults()
        .addAll(results);

    CellGroupSummarySheetXSSFCreator creator =
        new CellGroupSummarySheetXSSFCreator(cellGroup, workbook);
    creator.execute();


    for (CellResult result : results) {
      CellResultSheetXSSFCreator cellResultSheetCreator = new CellResultSheetXSSFCreator(result);
      cellResultSheetCreator.execute(workbook);
    }

    workbook.removeSheetAt(workbook.getSheetIndex(CellResultSheetXSSFCreator.TEMPLATE_SHEET));
    workbook.removeSheetAt(
        workbook.getSheetIndex(CellGroupSummarySheetXSSFCreator.CELL_GROUP_TEMPLATE_TABLE_NAME));
    workbook.removeSheetAt(
        workbook.getSheetIndex(CellGroupsSummarySheetXSSFCreator.CELL_GROUPS_TEMPLATE));
    try (FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
      workbook.write(outputStream);
    } catch (IOException e) {
      throw e;
    }

  }

  public static void exportCellGroups(Collection<CellGroup> cellGroups, String outputFileName)
      throws IOException {
    List<CellGroup> cellGroupsClone = cellGroups.stream()
        .map(group -> EcoreUtil.copy(group))
        .collect(Collectors.toList());
    CustomXSSFWorkbook workbook = createRawTemplatedWorkbook(outputFileName);


    CellGroupsSummarySheetXSSFCreator groupsSheetCreator =
        new CellGroupsSummarySheetXSSFCreator(cellGroups, workbook);

    groupsSheetCreator.execute();


    for (CellGroup cellGroup : cellGroupsClone) {
      CellGroupSummarySheetXSSFCreator creator =
          new CellGroupSummarySheetXSSFCreator(cellGroup, workbook);
      creator.execute();
    }

    workbook.removeSheetAt(workbook.getSheetIndex(CellResultSheetXSSFCreator.TEMPLATE_SHEET));
    workbook.removeSheetAt(
        workbook.getSheetIndex(CellGroupSummarySheetXSSFCreator.CELL_GROUP_TEMPLATE_TABLE_NAME));
    workbook.removeSheetAt(
        workbook.getSheetIndex(CellGroupsSummarySheetXSSFCreator.CELL_GROUPS_TEMPLATE));
    try (FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
      workbook.write(outputStream);
    } catch (IOException e) {
      throw e;
    }

  }

  private static CustomXSSFWorkbook createRawTemplatedWorkbook(String outputFileName)
      throws IOException {
    File cellResultTemplateFile;
    CustomXSSFWorkbook workbook;
    cellResultTemplateFile = FileUtils.locateRootFile(cellResultTemplate);
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
      workbook = new CustomXSSFWorkbook(inputStream);
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException e) {
      throw e;
    }
    return workbook;
  }


  private ExcelExports() {

  }
}
