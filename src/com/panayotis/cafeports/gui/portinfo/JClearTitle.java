/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JClearTitle extends JClearPanel {

    private final JLabel label;
    private final JRounder tl;
    private final JRounder tr;
    private final JClearButton closeB;

    public JClearTitle() {
        label = new JClearLabel();
        tl = new JRounder(JRounder.Location.TOPLEFT);
        tr = new JRounder(JRounder.Location.TOPRIGHT);
        closeB = new JClearButton("/icons/buttons/close.png");
        JClearPanel left = new JClearPanel();
        JClearPanel right = new JClearPanel();
        JClearPanel leftedge = new JClearPanel();
        JClearPanel rightedge = new JClearPanel();

        Dimension size = new Dimension(8, 8);
        tl.setPreferredSize(size);
        tr.setPreferredSize(size);

        closeB.setToolTipText("Close info window");

        left.setLayout(new BorderLayout());
        leftedge.setLayout(new BorderLayout());
        leftedge.add(tl, BorderLayout.NORTH);
        left.add(leftedge, BorderLayout.WEST);
        left.add(closeB, BorderLayout.CENTER);

        right.setLayout(new BorderLayout());
        rightedge.setLayout(new BorderLayout());
        rightedge.add(tr, BorderLayout.NORTH);
        right.add(rightedge, BorderLayout.EAST);

        label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | java.awt.Font.BOLD));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(new EmptyBorder(2, 4, 2, 14));

        setLayout(new BorderLayout());
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(label, BorderLayout.CENTER);
        setBackground(getBackground());
        setForeground(getForeground());
        setOpaque(true);
    }

    public void setText(String text) {
        label.setText(text);
        doLayout();
    }

    public void addCloseListener(ActionListener l) {
        closeB.addActionListener(l);
    }
}
