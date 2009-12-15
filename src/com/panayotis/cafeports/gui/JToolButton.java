/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JToolButton extends JPanel {

    private final AbstractButton button;
    private final JShadowLabel label;
    /* */
    private Label lType = Label.BOTH;
    private String lText = "";
    private Icon lIcon = null;

    public JToolButton(boolean isToggle) {
        label = new JShadowLabel();
        button = isToggle ? new JToggleButton() : new JButton();
        new CombinedMouseListener(this, label);

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setLabelFor(button);
        label.setFont(label.getFont().deriveFont(11f));
        label.setBorder(new EmptyBorder(0, 2, 0, 2));

        button.setFocusable(false);

        setBorder(new EmptyBorder(0, 0, 4, 0));
        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        add(label, BorderLayout.SOUTH);
    }

    public void setCapsulePosition(Location loc) {
        button.putClientProperty("JButton.buttonType", "segmentedCapsule");
        button.putClientProperty("JButton.segmentPosition", loc.name().toLowerCase());
    }

    public void setToolTipText(String tip) {
        super.setToolTipText(tip);
        button.setToolTipText(tip);
        label.setToolTipText(tip);
    }

    public void setFocusable(boolean status) {
        super.setFocusable(status);
        button.setFocusable(status);
    }

    public void addMouseListener(MouseListener listener) {
        //      super.addMouseListener(listener);
        label.addMouseListener(listener);
    }

    public void addMouseMotionListener(MouseMotionListener listener) {
        //       super.addMouseMotionListener(listener);
        label.addMouseMotionListener(listener);
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
        label.setEnabled(status);
    }

    private void resetLabels() {
        switch (lType) {
            case NONE:
                button.setIcon(null);
                label.setText(null);
                break;
            case ICON:
                button.setIcon(lIcon);
                label.setText(null);
                break;
            case TEXT:
                button.setIcon(null);
                label.setText(lText);
                break;
            case BOTH:
                button.setIcon(lIcon);
                label.setText(lText);
                break;
        }
        if (getParent() != null)
            getParent().doLayout();
    }

    public enum Location {

        FIRST, LAST, MIDDLE, ONLY
    };

    public enum Label {

        NONE, ICON, TEXT, BOTH
    };
}
