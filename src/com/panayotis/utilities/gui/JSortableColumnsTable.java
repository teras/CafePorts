/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.utilities.gui;

import com.panayotis.cafeports.gui.table.*;
import com.panayotis.utilities.Closure;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author teras
 */
public class JSortableColumnsTable extends JScrollPane {

    private final JTable tbl;
    private final TableSorter sorter;
    private final JButton colsel;

    public JSortableColumnsTable() {
        super();
        tbl = new JTable();
        sorter = new TableSorter(tbl);
        colsel = new JButton(new ImageIcon(getClass().getResource("/icons/colsel.png")));

        sorter.setSortingStatus(1, TableSorter.ASCENDING);
        tbl.setGridColor(new Color(220, 220, 235));
        updateColumnSizes();

        colsel.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent ev) {
                ((SelectableColumns) sorter.getTableModel()).selectVisibleColumns(ev, new Closure() {

                    public void exec(Object data) {
                        sorter.fireTableStructureChanged();
                        updateColumnSizes();
                    }
                });
            }
        });

        tbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tbl.setDefaultRenderer(Object.class, new CellRenderer());
        setEnabled(false);
        setViewportView(tbl);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setCorner(JScrollPane.UPPER_RIGHT_CORNER, colsel);
    }



    public ListSelectionModel getSelectionModel() {
        return tbl.getSelectionModel();
    }

    public void updateColumnSizes() {
        TableColumnModel model = tbl.getColumnModel();
        TableColumn col;
        for (int i = 0; i < model.getColumnCount() - 1; i++) {    // It is IMPORTANT to leave out last column, so that it will be auto-resized
            col = model.getColumn(i);
            col.setPreferredWidth(i == 0 ? PortListModel.FIRST_COLUMN_SIZE : PortListModel.PREFERRED_COLUMN_SIZE);
            col.setMinWidth(i == 0 ? PortListModel.FIRST_COLUMN_SIZE : PortListModel.MINIMUM_COLUMN_SIZE);
            col.setMaxWidth(i == 0 ? PortListModel.FIRST_COLUMN_SIZE : PortListModel.MAXIMUM_COLUMN_SIZE);
        }
    }

    public void clearList() {
        sorter.setTableModel(new PortListModel());
        setEnabled(false);
    }

    public void setEnabled(boolean status) {
        super.setEnabled(status);
        colsel.setEnabled(status);
    }

    public TableSorter getTableSorterModel() {
        return sorter;
    }

    public JTable getTable() {
        return tbl;
    }
}
