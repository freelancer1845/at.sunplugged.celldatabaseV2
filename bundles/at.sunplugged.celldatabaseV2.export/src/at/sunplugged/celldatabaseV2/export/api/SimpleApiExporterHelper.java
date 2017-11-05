package at.sunplugged.celldatabaseV2.export.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.odftoolkit.simple.SpreadsheetDocument;
import at.sunplugged.celldatabaseV2.export.Activator;
import at.sunplugged.celldatabaseV2.export.internal.CellGroupSummarySheetCreator;
import at.sunplugged.celldatabaseV2.export.internal.CellResultSheetCreator;
import datamodel.CellGroup;
import datamodel.CellResult;
import datamodel.DatamodelFactory;

public class SimpleApiExporterHelper {

  private static String CELL_RESULTS_TEMPLATE = "resources/cellResultTemplate.ods";

  public static void exportCellResults(String outputFileName, List<CellResult> _results)
      throws Exception {
    File cellResultTemplateFile = new File(Paths
        .get(FileLocator.getBundleFile(Activator.getContext()
            .getBundle())
            .getAbsolutePath(), CELL_RESULTS_TEMPLATE)
        .toString());

    if (cellResultTemplateFile.exists() == false) {
      throw new IOException("Template file not found...");
    }
    SpreadsheetDocument document = SpreadsheetDocument.loadDocument(cellResultTemplateFile);

    Collection<CellResult> results = EcoreUtil.copyAll(_results);
    for (CellResult result : results) {
      CellResultSheetCreator cellResultSheetCreator = new CellResultSheetCreator(result);
      cellResultSheetCreator.execute(document);
    }
    CellGroup cellGroup = DatamodelFactory.eINSTANCE.createCellGroup();
    cellGroup.getCellResults()
        .addAll(results);

    CellGroupSummarySheetCreator creator = new CellGroupSummarySheetCreator(cellGroup, document);
    creator.execute();
    document.getTableByName(CellResultSheetCreator.TEMPLATE_SHEET)
        .remove();
    document.getTableByName(CellGroupSummarySheetCreator.CELL_GROUP_TEMPLATE_TABLE_NAME)
        .remove();
    document.save(outputFileName);

  }

}
