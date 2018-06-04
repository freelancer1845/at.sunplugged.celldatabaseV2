package at.sunplugged.celldatabasev2.rcp.data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import datamodel.CellGroup;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.util.SummaryFunctions;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class GroupTableViewerFX {

  private TableView table;
  private FXCanvas canvas;
  private final CellGroup group;
  private TableView statisticsTable;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public GroupTableViewerFX(Composite parent, CellGroup group) {
    this.group = group;
    canvas = new FXCanvas(parent, SWT.NONE);
    GridDataFactory.fillDefaults().grab(true, true).applyTo(canvas);

    BorderPane layout = new BorderPane();
    Scene scene = new Scene(layout, Color.rgb(parent.getShell().getBackground().getRed(),
        parent.getShell().getBackground().getGreen(), parent.getShell().getBackground().getBlue()));

    canvas.setScene(scene);

    table = new TableView<>();

    table.setEditable(false);


    TableColumn nameCol = new TableColumn("Name");
    nameCol.setCellValueFactory(result -> new ReadOnlyStringWrapper(
        ((CellDataFeatures<CellResult, String>) result).getValue().getName()));

    TableColumn vocCol = createDoubleColumn("Voc[V]", 2, result -> result.getOpenCircuitVoltage());

    boolean needsVocPerCellColumn =
        group.getCellResults().stream().anyMatch(result -> result.getNumberOfCells() > 1);
    TableColumn vocPerCelCol =
        createDoubleColumn("Voc/Cell[V]", 2, result -> result.getVocPerCell());

    TableColumn jscCol =
        createDoubleColumn("Jsc[A/cm^2]", 2, result -> result.getShortCircuitCurrent()
            / result.getLightMeasurementDataSet().getArea() / 10000);
    TableColumn rsCol = createDoubleColumn("Rs[ohm]", 4, result -> result.getSeriesResistance());
    TableColumn rsDarkCol =
        createDoubleColumn("RsDark[ohm]", 4, result -> result.getDarkSeriesResistance());
    TableColumn rpCol = createDoubleColumn("Rp[ohm]", 4, result -> result.getParallelResistance());
    TableColumn rpDarkCol =
        createDoubleColumn("RpDark[ohm]", 4, result -> result.getDarkParallelResistance());
    TableColumn pmmpCol = createDoubleColumn("Pmpp[W/cm^2]", 4,
        result -> result.getMaximumPower() / result.getLightMeasurementDataSet().getArea() / 10000);
    TableColumn vmmpCol =
        createDoubleColumn("Vmpp[V]", 4, result -> result.getMaximumPowerVoltage());
    TableColumn imppCol =
        createDoubleColumn("Impp[A]", 4, result -> result.getMaximumPowerCurrent());
    TableColumn effCol = createDoubleColumn("Efficiency[%]", 3, result -> result.getEfficiency());
    TableColumn ffCol = createDoubleColumn("FillFactor[%]", 3, result -> result.getFillFactor());

    if (needsVocPerCellColumn == true) {
      table.getColumns().addAll(nameCol, vocCol, vocPerCelCol, jscCol, rsCol, rsDarkCol, rpCol,
          rpDarkCol, pmmpCol, vmmpCol, imppCol, effCol, ffCol);
    } else {
      table.getColumns().addAll(nameCol, vocCol, jscCol, rsCol, rsDarkCol, rpCol, rpDarkCol,
          pmmpCol, vmmpCol, imppCol, effCol, ffCol);
    }


    List<CellResult> results = new ArrayList<>(group.getCellResults());
    CellResult meanDummy = DatamodelFactory.eINSTANCE.createCellResult();
    meanDummy.setName("Mean");
    meanDummy.setOpenCircuitVoltage(
        SummaryFunctions.getAverage(group, result -> result.getOpenCircuitVoltage()));
    meanDummy.setNumberOfCells(
        (int) Math.round(SummaryFunctions.getAverage(group, result -> result.getNumberOfCells())));
    meanDummy.setShortCircuitCurrent(
        SummaryFunctions.getAverage(group, result -> result.getShortCircuitCurrent()
            / result.getLightMeasurementDataSet().getArea() / 10000));
    meanDummy.setSeriesResistance(
        SummaryFunctions.getAverage(group, result -> result.getSeriesResistance()));
    meanDummy.setDarkSeriesResistance(
        SummaryFunctions.getAverage(group, result -> result.getDarkSeriesResistance()));
    meanDummy.setParallelResistance(
        SummaryFunctions.getAverage(group, result -> result.getParallelResistance()));
    meanDummy.setDarkParallelResistance(
        SummaryFunctions.getAverage(group, result -> result.getDarkParallelResistance()));
    meanDummy.setMaximumPowerCurrent(
        SummaryFunctions.getAverage(group, result -> result.getMaximumPowerCurrent()));
    meanDummy.setMaximumPowerVoltage(
        SummaryFunctions.getAverage(group, result -> result.getMaximumPowerVoltage()
            / result.getLightMeasurementDataSet().getArea() / 10000));
    meanDummy.setEfficiency(SummaryFunctions.getAverage(group, result -> result.getEfficiency()));
    meanDummy.setFillFactor(SummaryFunctions.getAverage(group, result -> result.getFillFactor()));

    CellResult stdDummy = DatamodelFactory.eINSTANCE.createCellResult();
    stdDummy.setName("Std");
    stdDummy.setOpenCircuitVoltage(
        SummaryFunctions.getStd(group, result -> result.getOpenCircuitVoltage()));
    stdDummy.setNumberOfCells(meanDummy.getNumberOfCells());
    stdDummy.setShortCircuitCurrent(
        SummaryFunctions.getStd(group, result -> result.getShortCircuitCurrent()
            / result.getLightMeasurementDataSet().getArea() / 10000));
    stdDummy.setSeriesResistance(
        SummaryFunctions.getStd(group, result -> result.getSeriesResistance()));
    stdDummy.setDarkSeriesResistance(
        SummaryFunctions.getStd(group, result -> result.getDarkSeriesResistance()));
    stdDummy.setParallelResistance(
        SummaryFunctions.getStd(group, result -> result.getParallelResistance()));
    stdDummy.setDarkParallelResistance(
        SummaryFunctions.getStd(group, result -> result.getDarkParallelResistance()));
    stdDummy.setMaximumPowerCurrent(
        SummaryFunctions.getStd(group, result -> result.getMaximumPowerCurrent()));
    stdDummy.setMaximumPowerVoltage(
        SummaryFunctions.getStd(group, result -> result.getMaximumPowerVoltage()
            / result.getLightMeasurementDataSet().getArea() / 10000));
    stdDummy.setEfficiency(SummaryFunctions.getStd(group, result -> result.getEfficiency()));
    stdDummy.setFillFactor(SummaryFunctions.getStd(group, result -> result.getFillFactor()));

    CellResult maxDummy = DatamodelFactory.eINSTANCE.createCellResult();
    maxDummy.setName("Max");
    maxDummy.setOpenCircuitVoltage(
        SummaryFunctions.getMax(group, result -> result.getOpenCircuitVoltage()));
    maxDummy.setNumberOfCells(
        (int) Math.round(SummaryFunctions.getMax(group, result -> result.getNumberOfCells())));
    maxDummy.setShortCircuitCurrent(
        SummaryFunctions.getMax(group, result -> result.getShortCircuitCurrent()
            / result.getLightMeasurementDataSet().getArea() / 10000));
    maxDummy.setSeriesResistance(
        SummaryFunctions.getMax(group, result -> result.getSeriesResistance()));
    maxDummy.setDarkSeriesResistance(
        SummaryFunctions.getMax(group, result -> result.getDarkSeriesResistance()));
    maxDummy.setParallelResistance(
        SummaryFunctions.getMax(group, result -> result.getParallelResistance()));
    maxDummy.setDarkParallelResistance(
        SummaryFunctions.getMax(group, result -> result.getDarkParallelResistance()));
    maxDummy.setMaximumPowerCurrent(
        SummaryFunctions.getMax(group, result -> result.getMaximumPowerCurrent()));
    maxDummy.setMaximumPowerVoltage(
        SummaryFunctions.getMax(group, result -> result.getMaximumPowerVoltage()
            / result.getLightMeasurementDataSet().getArea() / 10000));
    maxDummy.setEfficiency(SummaryFunctions.getMax(group, result -> result.getEfficiency()));
    maxDummy.setFillFactor(SummaryFunctions.getMax(group, result -> result.getFillFactor()));

    CellMeasurementDataSet dummySetMean = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
    dummySetMean.setArea(1 / 10000.0);
    meanDummy.setLightMeasurementDataSet(dummySetMean);
    CellMeasurementDataSet dummySetStd = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
    dummySetStd.setArea(1 / 10000.0);
    stdDummy.setLightMeasurementDataSet(dummySetStd);
    CellMeasurementDataSet dummySetMax = DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
    dummySetMax.setArea(1 / 10000.0);
    maxDummy.setLightMeasurementDataSet(dummySetMax);
    results.add(meanDummy);
    results.add(stdDummy);
    results.add(maxDummy);
    table.getItems().setAll(results);



    VBox vbox = new VBox();

    vbox.getChildren().add(table);
    layout.setCenter(vbox);


  }


  public Control getControl() {
    return canvas;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private TableColumn createDoubleColumn(String column, int significantDigits,
      Function<CellResult, Double> getter) {
    TableColumn col = new TableColumn<>(column);
    col.setCellValueFactory(element -> new ReadOnlyDoubleWrapper(
        getter.apply((((CellDataFeatures<CellResult, String>) element).getValue()))));
    col.setCellFactory(new DoubleFormatCellFactory(significantDigits));
    return col;
  }

  private class DoubleFormatCellFactory implements Callback<TableColumn, TableCell> {

    private final int siginifcantDigits;

    public DoubleFormatCellFactory(int siginifcantDigits) {
      this.siginifcantDigits = siginifcantDigits;
    }

    @Override
    public TableCell call(TableColumn arg0) {
      TableCell cell = new TableCell<CellResult, Double>() {
        @Override
        protected void updateItem(Double arg0, boolean arg1) {
          super.updateItem(arg0, arg1);
          setText(arg1 ? null : getString());
          setGraphic(null);

        }

        private String getString() {
          String ret = "";
          if (getItem() != null) {
            String gi = getItem().toString();
            System.out.println(gi);
            try {
              BigDecimal decimal =
                  new BigDecimal(Double.parseDouble(gi), new MathContext(siginifcantDigits));
              ret = decimal.toPlainString();
            } catch (NumberFormatException e) {
              ret = "NaN";
            }



          } else {
            ret = "0.00";
          }
          return ret;
        }

      };
      return cell;
    }

  }

}
