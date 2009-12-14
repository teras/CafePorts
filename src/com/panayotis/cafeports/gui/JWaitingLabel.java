/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JWaitingLabel extends JLabel {

    private static Icon stage1 = new ImageIcon(JWaitingLabel.class.getResource("/icons/stage1.png"));
    private static Icon stage2 = new ImageIcon(JWaitingLabel.class.getResource("/icons/stage2.png"));

    public JWaitingLabel() {
        super();
        setOpaque(true);
        setVerticalTextPosition(JLabel.CENTER);
        setBorder(new EmptyBorder(6, 12, 6, 12));
        setIconTextGap(8);
    }

    public void setInvalidPath() {
        setBackground(Color.red);
        setText("MacPorts PREFIX directory is not valid; usually \"/opt/local/\"");
        setIcon(null);
    }

    public void setWaiting(boolean stage) {
        setBackground(new Color(250, 200, 100));
        setText("Please wait... reading database");
        setIcon(stage ? stage2 : stage1);
    }
}
