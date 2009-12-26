/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.table;

import com.panayotis.utilities.gui.JSortableColumnsTable;
import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.db.PortList;

/**
 *
 * @author teras
 */
public class JPortList extends JSortableColumnsTable {

    public PortInfo[] getSelectedPorts() {
        int[] rows = getTable().getSelectedRows();
        PortInfo[] ports = new PortInfo[rows.length];
        for (int i = 0; i < rows.length; i++)
            ports[i] = PortList.getFilteredPortList().getItem(getTableSorterModel().modelIndex(rows[i]));
        return ports;
    }

    public void updatePortList() {
        getTableSorterModel().setTableModel(new PortListModel(PortList.getFilteredPortList(), (PortListModel) getTableSorterModel().getTableModel()));
        updateColumnSizes();
        setEnabled(true);
    }
}
