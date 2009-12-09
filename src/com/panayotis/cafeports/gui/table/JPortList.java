/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.table;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.db.PortList;
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
public class JPortList extends JScrollPane {

    private final JTable tbl;
    private final TableSorter sorter;
    private final JButton colsel;

    public JPortList() {
        super();
        tbl = new JTable();
        sorter = new TableSorter(new PortListModel(PortList.getEmptyPortList()), tbl.getTableHeader());
        colsel = new JButton(new ImageIcon(getClass().getResource("/icons/colsel.png")));

        sorter.setSortingStatus(1, TableSorter.ASCENDING);
        tbl.setGridColor(new Color(220, 220, 235));
        tbl.setModel(sorter);
        updateColumnSizes();

        colsel.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent ev) {
                ((SelectableColumns) sorter.getTableModel()).selectVisibleColumns(ev);
            }
        });
        colsel.setEnabled(false);

        tbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tbl.setDefaultRenderer(Object.class, new CellRenderer());
        setViewportView(tbl);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setCorner(JScrollPane.UPPER_RIGHT_CORNER, colsel);
    }

    public PortInfo[] getSelectedPorts() {
        int[] rows = tbl.getSelectedRows();
        PortInfo[] ports = new PortInfo[rows.length];
        for (int i = 0; i < rows.length; i++)
            ports[i] = PortList.getFilteredPortList().getItem(sorter.modelIndex(rows[i]));
        return ports;
    }

    public void updatePortList() {
        sorter.setTableModel(new PortListModel(PortList.getFilteredPortList()));
        updateColumnSizes();
        colsel.setEnabled(true);
    }

    private void updateColumnSizes() {
        TableColumnModel model = tbl.getColumnModel();
        TableColumn col;
        for (int i = 0; i < model.getColumnCount() - 1; i++) {    // It is IMPORTANT to leave out last column, so that it will be auto-resized
            col = model.getColumn(i);
            col.setPreferredWidth(PortListModel.PreferredColumnSize[i]);
            col.setMinWidth(PortListModel.MinimumColumnSize[i]);
            col.setMaxWidth(PortListModel.MaximumColumnSize[i]);
        }
    }

    public ListSelectionModel getSelectionModel() {
        return tbl.getSelectionModel();
    }
}
