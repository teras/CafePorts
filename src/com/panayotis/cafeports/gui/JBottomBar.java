/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.gui.listeners.UnifiedDragListener;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JBottomBar extends JLabel {

    private final static Color upper = new Color(200, 200, 200);
    private final static Color lower = new Color(180, 180, 180);

    public JBottomBar(JPortWindow frame) {
        new UnifiedDragListener(frame).register(this);
        setText(" ");
        setFont(getFont().deriveFont(11f));
        setBorder(new EmptyBorder(1, 8, 1, 16));
        setOpaque(false);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, upper, 0, h, lower);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
        super.paintComponent(g);
    }

    public void setHowMany(int all, int filtered, int selected) {
        setText(filtered + " ports shown out of " + all + " ports. " + selected + " selected.");
    }
}
