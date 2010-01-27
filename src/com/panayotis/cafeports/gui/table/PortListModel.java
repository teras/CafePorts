/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.table;

import com.panayotis.cafeports.db.PortList;
import com.panayotis.cafeports.gui.JSelector;
import com.panayotis.utilities.Closure;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author teras
 */
public class PortListModel extends AbstractTableModel implements SelectableColumns {

    private static final String[] Columns = {"⎈", "Name", "Categories", "Version", "Variants", "Installed Version", "Home page", "Maintainers", "Description"};
    private static final String[] ColData = {"installflags", "name", "categories", "version", "variants", "installed_version", "homepage", "maintainers", "description"};
    public static final int FIRST_COLUMN_SIZE = 16;
    public static final int PREFERRED_COLUMN_SIZE = 100;
    public static final int MAXIMUM_COLUMN_SIZE = 500;
    public static final int MINIMUM_COLUMN_SIZE = 10;
    /* Core variables */
    private boolean[] visible_cols = {true, true, false, true, false, false, false, false, true};
    private PortList list;
    /* helper variables */
    private JSelector sel = new JSelector();
    private int[] correspondence;

    public PortListModel(PortList list, PortListModel old) {
        this.list = list;
        if (old != null)
            System.arraycopy(old.visible_cols, 0, visible_cols, 0, visible_cols.length);
        calculateCorrespondences();
    }

    public PortListModel() {
        this(PortList.getEmptyPortList(), null);
    }

    private final synchronized void calculateCorrespondences() {
        int cols = 0;
        for (int i = 0; i < visible_cols.length; i++)
            if (visible_cols[i])
                cols++;
        correspondence = new int[cols];

        cols = 0;
        for (int i = 0; i < visible_cols.length; i++)
            if (visible_cols[i]) {
                correspondence[cols] = i;
                cols++;
            }
    }

    public int getRowCount() {
        return list.getSize();
    }

    public int getColumnCount() {
        return correspondence.length;
    }

    public String getColumnName(int columnIndex) {
        return Columns[correspondence[columnIndex]];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        String val = list.getItem(rowIndex).getData(ColData[correspondence[columnIndex]]);
        if (val != null)
            if (correspondence[columnIndex] == 0)
                val = String.valueOf(val.charAt(0));
        return val;
    }

    public void selectVisibleColumns(MouseEvent event, final Closure call_me_back) {
        JComponent comp = (JComponent) event.getSource();
        sel.fireSelector(Columns, visible_cols, false, comp, event.getX(), event.getY(), new Closure() {

            public void exec(Object data) {
                int idx = Integer.parseInt(data.toString());
                if (idx >= 0 && idx < visible_cols.length)
                    visible_cols[idx] = !visible_cols[idx];
                calculateCorrespondences();
                call_me_back.exec(null);
            }
        });
    }
}
