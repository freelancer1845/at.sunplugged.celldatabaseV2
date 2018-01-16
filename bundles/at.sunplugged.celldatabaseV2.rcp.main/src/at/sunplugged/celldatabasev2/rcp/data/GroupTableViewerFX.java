package at.sunplugged.celldatabasev2.rcp.data;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import datamodel.CellGroup;
import datamodel.CellResult;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class GroupTableViewerFX {

  private TableView table;
  private FXCanvas canvas;
  private final CellGroup group;

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
    TableColumn vocCol = new TableColumn("Voc");
    vocCol.setCellValueFactory(result -> new ReadOnlyDoubleWrapper(
        ((CellDataFeatures<CellResult, String>) result).getValue().getOpenCircuitVoltage()));
    TableColumn iscCol = new TableColumn("Isc");
    iscCol.setCellValueFactory(result -> new ReadOnlyDoubleWrapper(
        ((CellDataFeatures<CellResult, String>) result).getValue().getShortCircuitCurrent()));

    table.getColumns().addAll(nameCol, vocCol, iscCol);
    table.getItems().setAll(group.getCellResults());
    layout.setCenter(table);


  }

  public Control getControl() {
    return canvas;
  }

}
