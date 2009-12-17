/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.Graphics;

/**
 *
 * @author teras
 */
public class JClearLabel extends javax.swing.JLabel {

    public JClearLabel(String label) {
        super(label);
    }

    public JClearLabel() {
        super();
    }

    public void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }
}
