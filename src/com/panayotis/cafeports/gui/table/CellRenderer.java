/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author teras
 */
public class CellRenderer extends DefaultTableCellRenderer {

    Color background = new Color(237, 243, 254);

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if ((row % 2) == 0)
            setBackground(Color.WHITE);
        else
            setBackground(background);
        setForeground(Color.BLACK);
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return this;
    }
}
