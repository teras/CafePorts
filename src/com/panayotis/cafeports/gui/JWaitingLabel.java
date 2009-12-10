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
        super();
        setOpaque(true);
        setVerticalTextPosition(JLabel.CENTER);
    }

    public Dimension getPreferredSize() {
        Dimension superD = super.getPreferredSize();
        return new Dimension(superD.width, superD.height + 12);
    }

    public void setInvalidPath() {
        setBackground(Color.red);
        setText("  MacPorts PREFIX directory is not valid; usually \"/opt/local/\"");
    }

    public void setWaiting() {
        setBackground(new Color(250, 200, 100));
        setText(" Please wait... reading database");
    }
}
