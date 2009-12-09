/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JRoundEdge extends JPanel {

    private static final Color transparent = new Color(0, 0, 0, 0);
    private static final int MINIMUM_WIDTH = 200;
    /* */
    private final int compheight;
    private final JLabel label;
    private boolean isUpside;

    public JRoundEdge(int height, int border) {
        this.compheight = height;
        setLayout(new BorderLayout());
        label = new JClearLabel();
        label.setBackground(transparent);
        label.setForeground(getForeground());
        label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | java.awt.Font.BOLD));
        label.setHorizontalAlignment(JLabel.CENTER);
        if (border != 0) {
            setBorder(new EmptyBorder(0, border, 0, border));
            add(label, BorderLayout.CENTER);
        }
        isUpside = false;
    }

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        float cutoff = height;
        float quadoff = cutoff / 8f;

        float straightH, curvedH;
        if (isUpside) {
            straightH = 0;
            curvedH = height - quadoff;
        } else {
            straightH = height;
            curvedH = quadoff;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GeneralPath path = new GeneralPath();

        path.moveTo(0, straightH);
        path.lineTo(width, straightH);
        path.quadTo(width - quadoff, curvedH, width - cutoff, height - straightH);
        path.lineTo(cutoff, height - straightH);
        path.quadTo(quadoff, curvedH, 0, straightH);

        g2.clearRect(0, 0, width, height);
        g2.setColor(getBackground());
        g2.fill(path);
    }

    public Dimension getPreferredSize() {
        int width = compheight * 2 + label.getPreferredSize().width;
        if (width < MINIMUM_WIDTH)
            width = MINIMUM_WIDTH;
        return new Dimension(MINIMUM_WIDTH, compheight);
    }

    public Dimension getMaximumSize() {
        return new Dimension(10000, compheight);
    }

    public Dimension getMinimumSize() {
        return new Dimension(MINIMUM_WIDTH, compheight);
    }

    public void setText(String text) {
        label.setText(text);
        doLayout();
    }

    public void setUpside(boolean isUpside) {
        this.isUpside = isUpside;
    }

    public void setForeground(Color c) {
        super.setForeground(c);
        if (label != null)
            label.setForeground(c);
    }

    public void setBackground(Color c) {
        super.setBackground(c);
        if (label != null)
            label.setBackground(c);
    }
}
