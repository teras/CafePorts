/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.explodingpixels.UnifiedToolbarButtonUI;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author teras
 */
public class JToolButton extends JPanel {

    private static final UnifiedToolbarButtonUI uibutton = new UnifiedToolbarButtonUI();
    private final AbstractButton button;
    /* */
    private Label lType = Label.BOTH;
    private boolean smallicon = false;
    private String lText = "";
    private Icon lIcon_s = null;
    private Icon lIcon_l = null;

    public JToolButton(boolean isToggle) {
        button = isToggle ? new JToggleButton() : new JButton();
        button.setUI(uibutton);
        setFont(getFont().deriveFont(11f));
        add(button);
        button.setFocusable(false);
    }

    public void setToolTipText(String tip) {
        super.setToolTipText(tip);
        button.setToolTipText(tip);
    }

    public void setFocusable(boolean status) {
        super.setFocusable(status);
        button.setFocusable(status);
    }

    public void setActionCommand(String command) {
        button.setActionCommand(command);
    }

    public void addActionListener(ActionListener al) {
        button.addActionListener(al);
    }

    public void setLabel(String label) {
        lText = label;
        lIcon_s = new ImageIcon(getClass().getResource("/icons/" + label.replace(" ", "").toLowerCase() + "_s.png"));
        lIcon_l = new ImageIcon(getClass().getResource("/icons/" + label.replace(" ", "").toLowerCase() + "_l.png"));
        resetLabels();
    }

    public void setIconSize(boolean small) {
        smallicon = small;
        resetLabels();
    }

    public void setLabelType(Label l) {
        lType = l;
        resetLabels();
    }

    public void setEnabled(boolean status) {
        super.setEnabled(status);
        button.setEnabled(status);
    }

    private void resetLabels() {
        switch (lType) {
            case ICON:
                button.setIcon(smallicon ? lIcon_s : lIcon_l);
                button.setText(null);
                break;
            case TEXT:
                button.setIcon(null);
                button.setText(lText);
                break;
            case BOTH:
                button.setIcon(smallicon ? lIcon_s : lIcon_l);
                button.setText(lText);
                break;
        }
    }

    public enum Label {

        BOTH, ICON, TEXT
    };
}
