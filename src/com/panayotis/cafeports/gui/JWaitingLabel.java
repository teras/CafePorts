/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

/**
 *
 * @author teras
 */
public class JWaitingLabel extends JLabel {

    public JWaitingLabel() {
        super(" Please wait... reading database");
        setBackground(new Color(250, 200, 100));
        setOpaque(true);
        setVerticalTextPosition(JLabel.CENTER);
    }

    public Dimension getPreferredSize() {
        Dimension superD = super.getPreferredSize();
        return new Dimension(superD.width, superD.height + 12);
    }
}
