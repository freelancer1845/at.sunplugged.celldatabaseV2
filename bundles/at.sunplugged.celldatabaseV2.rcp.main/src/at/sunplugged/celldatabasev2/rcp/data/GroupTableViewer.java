package at.sunplugged.celldatabasev2.rcp.data;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import datamodel.CellGroup;
import datamodel.CellResult;

public class GroupTableViewer extends TableViewer {

  private final CellGroup group;

  public GroupTableViewer(Composite parent, CellGroup group) {
    super(parent, SWT.BORDER | SWT.FULL_SELECTION);
    this.group = group;

    create();
  }

  private void create() {
    this.setContentProvider(ArrayContentProvider.getInstance());

    TableViewerColumn columnName = createColumnFor(this, "Name");
    columnName.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        return ((CellResult) element).getName();
      }
    });


    TableViewerColumn columnVoc = createColumnFor(this, "Voc");
    columnVoc.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        return String.valueOf(((CellResult) element).getOpenCircuitVoltage());
      }
    });
    TableViewerColumn columnIsc = createColumnFor(this, "Isc");
    columnIsc.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        return String.valueOf(((CellResult) element).getSeriesResistance());
      }

    });

    setInput(group.getCellResults());
    getTable().setHeaderVisible(true);
    getTable().setLinesVisible(true);

  }

  private TableViewerColumn createColumnFor(TableViewer viewer2, String string) {
    TableViewerColumn column = new TableViewerColumn(viewer2, SWT.NONE);
    column.getColumn().setWidth(200);
    column.getColumn().setText(string);
    column.getColumn().setMoveable(true);
    return column;
  }



}
