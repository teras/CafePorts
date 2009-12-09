/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author teras
 */
public class JClearText extends javax.swing.JTextArea {

    private final static Color transparent = new Color(0, 0, 0, 0);
    private Color background;

    public JClearText(int height) {
        super();
        background = getBackground();
        setBackground(transparent);
        setEnabled(false);
        setDisabledTextColor(getForeground());
        setLineWrap(true);
        setWrapStyleWord(true);
        setDragEnabled(false);
    }

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public void setBackground(Color back) {
        background = back == null ? background : back;
    }
}
