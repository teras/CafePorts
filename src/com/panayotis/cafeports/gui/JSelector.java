/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.utilities.ImmutableList;
import com.panayotis.cafeports.filter.Nameable;
import com.panayotis.utilities.Closure;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 *
 * @author teras
 */
public class JSelector extends JPopupMenu {

    public void showCategories(ImmutableList<Nameable> operations, JComponent comp, final Closure listener) {
        setItems(operations, listener);
        show(comp, comp.getBorder().getBorderInsets(comp).left / 2, comp.getHeight() - comp.getBorder().getBorderInsets(comp).bottom + 1);
    }

    public void showColumns(String[] values, boolean[] selected, JComponent comp, int x, int y, final Closure listener) {
        setItems(values, selected, 1, listener);
        show(comp, x, y);
    }

    public void setItems(String[] values, boolean[] selected, int offset, final Closure listener) {
        removeAll();
        for (int i = offset; i < values.length; i++) {
            String val = values[i];
            JComponent item;
            if (val == null)
                item = new JSeparator();
            else {
                JCheckBoxMenuItem itemc = new JCheckBoxMenuItem(values[i]);
                itemc.setActionCommand(Integer.toString(i));
                itemc.setState(selected[i]);
                itemc.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        if (listener != null)
                            listener.exec(e.getActionCommand());
                    }
                });
                item = itemc;
            }
            add(item);
        }
    }

    public void setItems(ImmutableList<Nameable> operations, final Closure listener) {
        removeAll();
        int index = 0;
        for (Nameable op : operations) {
            JMenuItem item = new JMenuItem(op.getName());
            item.setActionCommand(Integer.toString(index));
            item.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (listener != null)
                        listener.exec(e.getActionCommand());
                }
            });
            add(item);
            index++;
        }
    }
}
