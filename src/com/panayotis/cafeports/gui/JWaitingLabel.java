/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.config.JConfiguration;
import com.panayotis.cafeports.db.UpdateListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JWaitingLabel extends JPanel implements UpdateListener {

    private static Icon[] stages = {
        new ImageIcon(JWaitingLabel.class.getResource("/icons/stage1.png")),
        new ImageIcon(JWaitingLabel.class.getResource("/icons/stage2.png"))
    };
    private static final Color WaitingColor = new Color(133, 198, 252);
    private static final Color ErrorColor = new Color(255, 90, 39);
    private final static int MAXRESOLUTION = 1000;
    /* */
    private final JLabel text;
    private final JProgressBar progress;
    private final JButton openprefs;

    public JWaitingLabel() {
        super();
        text = new JLabel();
        progress = new JProgressBar();
        openprefs = new JButton();

        text.setVerticalTextPosition(JLabel.CENTER);
        text.setIconTextGap(8);

        progress.setMinimum(0);
        progress.setMaximum(MAXRESOLUTION);

        openprefs.setText("Preferences");
        openprefs.putClientProperty("JButton.buttonType", "textured");
        openprefs.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                JConfiguration.fireDisplay();
            }
        });

        setOpaque(true);
        setBorder(new EmptyBorder(6, 12, 6, 20));
    }

    public Dimension getMinimumSize() {
        Dimension size = super.getMinimumSize();
        size.height = 100;
        return size;
    }

    public void setInvalidPath(String error) {
        removeAll();
        setLayout(new BorderLayout(4, 0));
        add(openprefs, BorderLayout.EAST);
        add(text, BorderLayout.CENTER);
        setBackground(ErrorColor);
        text.setText(error);
        text.setIcon(null);
        doLayout();
    }

    public void setWaiting(boolean stage) {
        removeAll();
        setLayout(new BorderLayout(20, 0));
        add(text, BorderLayout.WEST);
        add(progress, BorderLayout.CENTER);
        setBackground(WaitingColor);
        text.setText("Please wait... reading database");
        doLayout();
    }

    public void setStage(int stage) {
        text.setIcon(stages[stage]);
        progress.setVisible(false);
    }

    public void setPercent(float percent) {
        progress.setVisible(true);
        progress.setValue((int) (MAXRESOLUTION * percent));
    }
}
