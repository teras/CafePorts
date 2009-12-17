/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JClearTextureButton extends JClearPanel {

    private final JButton button;
    private String url;

    public JClearTextureButton() {
        super();
        setOpaque(false);
        button = new JButton();

        button.putClientProperty("JButton.buttonType", "textured");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if (url != null)
                    try {
                        Class fileMgr = Class.forName("com.apple.eio.FileManager");
                        Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
                        openURL.invoke(null, new Object[]{url});
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
            }
        });

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 8, 0, 8));
        add(button, BorderLayout.CENTER);
    }

    public void setURL(String url) {
        this.url = url;
        setVisible(url != null);
    }

    public void setText(String label) {
        button.setText(label);
    }
}
