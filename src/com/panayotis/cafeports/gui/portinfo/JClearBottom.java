/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 *
 * @author teras
 */
public class JClearBottom extends JClearPanel {

    public JClearBottom(int height) {
        JRounder bl = new JRounder(JRounder.Location.BOTTOMLEFT);
        JRounder br = new JRounder(JRounder.Location.BOTTOMRIGHT);

        Dimension size = new Dimension(height, height);
        br.setPreferredSize(size);
        bl.setPreferredSize(size);

        setLayout(new BorderLayout());
        add(bl, BorderLayout.WEST);
        add(br, BorderLayout.EAST);
        setPreferredSize(size);
        setOpaque(true);
    }
}
