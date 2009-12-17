/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 *
 * @author teras
 */
public class JClearPanel extends javax.swing.JPanel {

    public static final Color clearColor = new Color(0, 0, 0, 0);

    public JClearPanel() {
        super();
    }

    public void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void setForeground(Color color) {
        super.setForeground(color);
        for (Component c : getComponents())
            c.setForeground(color);
    }

    public void setBackground(Color color) {
        super.setBackground(color);
        for (Component c : getComponents())
            c.setBackground(color);
    }
}
