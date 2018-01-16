package at.sunplugged.celldatabasev2.rcp.data;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import datamodel.CellGroup;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class GroupTableViewerFX {

  private TableView table;
  private FXCanvas canvas;

  @SuppressWarnings("rawtypes")
  public GroupTableViewerFX(Composite parent, CellGroup group) {
    canvas = new FXCanvas(parent, SWT.NONE);
    GridDataFactory.fillDefaults().grab(true, true).applyTo(canvas);

    BorderPane layout = new BorderPane();
    Scene scene = new Scene(layout, Color.rgb(parent.getShell().getBackground().getRed(),
        parent.getShell().getBackground().getGreen(), parent.getShell().getBackground().getBlue()));

    canvas.setScene(scene);

    table = new TableView<>();

    table.setEditable(false);

    TableColumn nameCol = new TableColumn("Name");
    TableColumn vocCol = new TableColumn("Voc");
    TableColumn iscCol = new TableColumn("Isc");

    table.getColumns().addAll(nameCol, vocCol, iscCol);

    layout.setCenter(table);


  }

  public Control getControl() {
    return canvas;
  }

}
