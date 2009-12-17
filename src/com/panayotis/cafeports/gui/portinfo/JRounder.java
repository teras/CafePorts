/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.JLabel;

/**
 *
 * @author teras
 */
public class JRounder extends JLabel {

    private Location loc;

    public JRounder(Location loc) {
        if (loc == null)
            throw new NullPointerException("Location of JRounder should not be null");
        this.loc = loc;
    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        float xoff = width / 8f;
        float yoff = height / 8f;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.clearRect(0, 0, width, height);
        g2.setColor(getBackground());
        GeneralPath path = new GeneralPath();
        switch (loc) {
            case TOPLEFT:
                path.moveTo(0, height);
                path.quadTo(xoff, yoff, width, 0);
                path.lineTo(width, height);
                break;
            case TOPRIGHT:
                path.moveTo(0, 0);
                path.quadTo(width - xoff, yoff, width, height);
                path.lineTo(0, height);
                break;
            case BOTTOMLEFT:
                path.moveTo(0, 0);
                path.quadTo(xoff, height - yoff, width, height);
                path.lineTo(width, 0);
                break;
            case BOTTOMRIGHT:
                path.moveTo(0, 0);
                path.lineTo(width, 0);
                path.quadTo(width - xoff, height - yoff, 0, height);
                break;
        }
        path.closePath();
        g2.fill(path);
    }

    public enum Location {

        TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT
    };
}
