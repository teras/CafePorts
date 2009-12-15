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
    private String lText = "";
    private Icon lIcon = null;

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
        lIcon = new ImageIcon(getClass().getResource("/icons/" + label.replace(" ", "").toLowerCase() + ".png"));
        resetLabels();
    }

    public void setLabelType(Label l) {
        lType = l;
    }

    public void setEnabled(boolean status) {
        super.setEnabled(status);
        button.setEnabled(status);
    }

    private void resetLabels() {
        switch (lType) {
            case ICON:
                button.setIcon(lIcon);
                button.setText(null);
                break;
            case TEXT:
                button.setIcon(null);
                button.setText(lText);
                break;
            case BOTH:
                button.setIcon(lIcon);
                button.setText(lText);
                break;
        }
        if (getParent() != null)
            getParent().doLayout();
    }

    public enum Location {

        FIRST, LAST, MIDDLE, ONLY
    };

    public enum Label {

        ICON, TEXT, BOTH
    };
}
