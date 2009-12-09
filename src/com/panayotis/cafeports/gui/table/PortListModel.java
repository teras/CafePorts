/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.table;

import com.panayotis.cafeports.db.PortList;
import com.panayotis.cafeports.gui.JSelector;
import com.panayotis.utilities.Closure;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author teras
 */
public class PortListModel extends AbstractTableModel implements SelectableColumns {

    public static final String[] Columns = {"⎈", "Name", "Version", "Installed Version", "Description"};
    public static final String[] ColData = {"isinstalled", "name", "version", "installed_version", "description"};
    public static final int[] PreferredColumnSize = {14, 100, 100, 100};    // Last column sould NOT be defined, or else auto-size will not be performed!
    public static final int[] MaximumColumnSize = {14, 500, 500, 500};      // Last column sould NOT be defined, or else auto-size will not be performed!
    public static final int[] MinimumColumnSize = {14, 10, 10, 10};         // Last column sould NOT be defined, or else auto-size will not be performed!
    /* */
    private PortList list;
    private boolean[] visible_cols = {true, true, true, false, true};
    private JSelector sel = new JSelector();

    public PortListModel(PortList list) {
        this.list = list;
    }

    public int getRowCount() {
        return list.getSize();
    }

    public int getColumnCount() {
        return Columns.length;
    }

    public String getColumnName(int columnIndex) {
        return Columns[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return list.getItem(rowIndex).getData(ColData[columnIndex]);
    }

    public void selectVisibleColumns(MouseEvent event) {
        JComponent comp = (JComponent)event.getSource();
        sel.showColumns(Columns, visible_cols, comp, event.getX(), event.getY(), new Closure() {

            public void exec(Object data) {
                System.out.println(data.toString());
            }
        });
    }
}
