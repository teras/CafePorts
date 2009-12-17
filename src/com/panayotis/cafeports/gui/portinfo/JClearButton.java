/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JClearButton extends JButton {

    public JClearButton(String iconname) {
        super();
        setOpaque(false);
        setBorderPainted(false);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setIcon(new ImageIcon(JClearBottom.class.getResource(iconname)));
    }
}
