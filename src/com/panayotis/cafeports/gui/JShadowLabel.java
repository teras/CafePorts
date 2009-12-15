/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author teras
 */
class JShadowLabel extends JLabel {

    private boolean enableEmphasis;
    public static final Color emphasisColor = new Color(255, 255, 255, 112);
    public static final Color focusedColor = new Color(0, 0, 0);
    public static final Color unfocusedColor = new Color(64, 64, 64);

    public JShadowLabel() {
        super();
    }

    public JShadowLabel(String label) {
        super(label);
    }

    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.height += 1;
        return d;
    }

    public Color getForeground() {
        Color retVal;
        Window window = SwingUtilities.getWindowAncestor(this);
        boolean hasFoucs = window != null && window.isFocused();
        if (enableEmphasis)
            retVal = emphasisColor;
        else if (hasFoucs)
            retVal = focusedColor;
        else
            retVal = unfocusedColor;

        return retVal;
    }

    @Override
    protected void paintComponent(Graphics g) {
        enableEmphasis = true;
        g.translate(0, 1);
        super.paintComponent(g);
        enableEmphasis = false;
        g.translate(0, -1);
        super.paintComponent(g);
    }
}
