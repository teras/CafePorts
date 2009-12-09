/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.filter.Operation;
import com.panayotis.cafeports.filter.PortData;
import com.panayotis.cafeports.filter.FilterFactory;
import com.panayotis.cafeports.filter.FilterFactory.Filter;
import com.panayotis.utilities.Closure;
import com.panayotis.utilities.ImmutableList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JFilterItem extends JPanel implements Closure {

    private static final Color back = new Color(230, 225, 225);
    private final static JSelector opselector = new JSelector();
    private final JButton level1;
    private final JButton level2;
    private final JButton plus;
    private final JButton minus;
    private final Filter filter;
    private final JFilters container;
    private boolean last_popup_is_source = false;

    public JFilterItem(Filter f, JFilters container) {
        super(new BorderLayout());
        this.filter = f;
        this.container = container;

        level1 = initButton(f.getPortData().getName(), null, "1");
        level2 = initButton(f.getOperation().getName(), null, "2");
        plus = initButton(null, new ImageIcon(getClass().getResource("/icons/plus.png")), "+");
        plus.setToolTipText("Insert constrain");
        minus = initButton(null, new ImageIcon(getClass().getResource("/icons/minus.png")), "-");
        minus.setToolTipText("Remove constrain");
        minus.setEnabled(false);

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.setBorder(new EmptyBorder(3, 5, 3, 3));
        left.add(level1, BorderLayout.WEST);
        left.add(level2, BorderLayout.EAST);

        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);
        right.add(minus, BorderLayout.WEST);
        right.add(plus, BorderLayout.EAST);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(f.getUserData().getComponent(), BorderLayout.CENTER);
        setBackground(back);
    }

    private JButton initButton(String name, Icon icon, String cmd) {
        JButton button = new JButton();
        if (name != null)
            button.setText(name);
        if (icon != null)
            button.setIcon(icon);
        button.putClientProperty("JButton.buttonType", "roundRect");
        button.setFocusable(false);
        button.setActionCommand(cmd);
        final JFilterItem self = this;
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("1")) {
                    opselector.showCategories((ImmutableList) FilterFactory.base.getSources(), (JButton) e.getSource(), self);
                    last_popup_is_source = true;
                } else if (e.getActionCommand().equals("2")) {
                    opselector.showCategories((ImmutableList) filter.getPortData().getOperations(), (JButton) e.getSource(), self);
                    last_popup_is_source = false;
                } else if (e.getActionCommand().equals("+"))
                    container.addFilter(FilterFactory.base.getNextFilter(container), filter);
                else if (e.getActionCommand().equals("-"))
                    container.removeFilter(filter);
            }
        });
        return button;
    }

    public void exec(Object index) {
        PortData p;
        Operation o;
        if (last_popup_is_source) {
            p = FilterFactory.base.getSources().getItem(Integer.parseInt(index.toString()));
            o = p.getOperations().getItem(0);
        } else {
            p = filter.getPortData();
            o = p.getOperations().getItem(Integer.parseInt(index.toString()));
        }
        Filter newf = FilterFactory.base.getFilter(p, o, container);
        container.replaceFilter(newf, filter);
    }

    void setRemovable(boolean status) {
        minus.setEnabled(status);
    }
}
