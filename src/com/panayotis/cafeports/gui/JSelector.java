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

/**
 *
 * @author teras
 */
public class JSelector {

    private JPopupMenu selector = new JPopupMenu();

    public void showCategories(ImmutableList<Nameable> operations, JComponent comp, final Closure listener) {
        selector.removeAll();
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
            selector.add(item);
            index++;
        }
        selector.show(comp, comp.getBorder().getBorderInsets(comp).left / 2, comp.getHeight() - comp.getBorder().getBorderInsets(comp).bottom + 1);
    }

    public void showColumns(String[] values, boolean[] visible, JComponent comp, int x, int y, final Closure listener) {
        selector.removeAll();
        for (int i = 1; i < values.length; i++) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(values[i]);
            item.setActionCommand(Integer.toString(i));
            item.setState(visible[i]);
            item.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (listener != null)
                        listener.exec(e.getActionCommand());
                }
            });
            selector.add(item);
        }
        selector.show(comp, x, y);
    }

    public JSelector() {
    }
}
