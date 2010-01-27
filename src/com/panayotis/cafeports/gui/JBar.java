/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.config.Config;
import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.gui.listeners.UnifiedDragListener;

import com.panayotis.cafeports.db.PortListManager;
import com.panayotis.cafeports.gui.JToolButton.Label;
import com.panayotis.utilities.Closure;
import com.panayotis.cafeports.gui.installer.PortProcess;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JBar extends JPanel implements ActionListener {

    private final static Preferences prefs = Preferences.userNodeForPackage(Config.class);
    /* */
    private final JPortWindow frame;
    private final JToolButton action;
    private final JToolButton selfupdate;
    private final JToolButton reload;
    private final JToolButton info;
    private final UnifiedDragListener drag;
    /* */
    private final JSelector iconsel;
    private final JSelector actionsel;
    private final SelectorListener listener;
    private final boolean[] toolbarselection = {true, false, false, false, false};
    private static final String[] toolbartext = {"Icon and Text", "Icon only", "Text only", null, "Use Small Size"};
    private static final String[] actionnames = {"Install", "Uninstall", "Upgrade", "Clean", "Activate", "Deactivate"};
    private final Closure action_do = new Closure() {

        public void exec(Object data) {
            PortListManager.updateData();
        }
    };

    public JBar(JPortWindow window) {
        this.frame = window;
        drag = new UnifiedDragListener(frame);
        iconsel = new JSelector();
        actionsel = new JSelector();
        listener = new SelectorListener();
        final JPanel lefts = new JPanel();
        final JPanel rights = new JPanel();

        lefts.setLayout(new GridLayout(1, 5));
        rights.setLayout(new GridLayout(1, 2));

        action = initButton("Action", "a", false);
        lefts.add(action);
        selfupdate = initButton("Selfupdate", "s", false);
        lefts.add(selfupdate);
        reload = initButton("Reload", "r", false);
        rights.add(reload);
        info = initButton("Info", "i", true);
        rights.add(info);

        restoreToolBarType();

        drag.register(this);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 4, 0, 0));
        add(lefts, BorderLayout.WEST);
        add(rights, BorderLayout.EAST);
        setComponentPopupMenu(iconsel);
        setEnabled(false);
    }

    public void setEnabled(boolean status) {
        super.setEnabled(status);
        action.setEnabled(status);
        selfupdate.setEnabled(status);
        reload.setEnabled(status);
        info.setEnabled(status);
    }

    private void setLabelType(Label labeltype) {
        action.setLabelType(labeltype);
        selfupdate.setLabelType(labeltype);
        reload.setLabelType(labeltype);
        info.setLabelType(labeltype);

        for (int i = 0; i < 3; i++)
            toolbarselection[i] = false;
        toolbarselection[labeltype.ordinal()] = true;
        iconsel.setItems(toolbartext, toolbarselection, false, 0, listener);
        frame.doLayout();
    }

    private void setIconSize(boolean smallSize) {
        action.setIconSize(smallSize);
        selfupdate.setIconSize(smallSize);
        reload.setIconSize(smallSize);
        info.setIconSize(smallSize);

        toolbarselection[4] = smallSize;
        iconsel.setItems(toolbartext, toolbarselection, false, 0, listener);
        frame.doLayout();
    }

    private void updateLabelType(int type_id) {
        if (type_id <= 2)
            setLabelType(Label.values()[type_id]);
        else
            setIconSize(!toolbarselection[4]);
        saveToolBarType();
    }

    private final JToolButton initButton(String label, String actioncommand, boolean toggle) {
        JToolButton button = new JToolButton(toggle);
        button.setLabel(label);
        button.setToolTipText(label);
        button.setFocusable(false);
        button.setActionCommand(actioncommand);
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent ev) {
        switch (ev.getActionCommand().charAt(0)) {
            case 'a':
                actionsel.fireSelector(actionnames, action, true, new Closure() {

                    public void exec(Object data) {
                        PortInfo[] ports = frame.getJPortList().getSelectedPorts();
                        if (ports == null || ports.length == 0)
                            return;
                        PortProcess.exec(actionnames[Integer.parseInt(data.toString())].toLowerCase(), ports, action_do);
                    }
                });
                break;
            case 's':
                PortProcess.exec("selfupdate", null, action_do);
                break;
            case 'r':
                PortListManager.forceReloadData();
                break;
            case 'i':
                frame.setInfoVisible(true);
                break;
        }
    }

    private void saveToolBarType() {
        int id = 0;
        for (Label l : Label.values()) {
            if (toolbarselection[id]) {
                prefs.put("ICON_TYPE", l.name());
                break;
            }
            id++;
        }
        prefs.putBoolean("SMALL_ICONS", toolbarselection[4]);
    }

    private void restoreToolBarType() {
        setLabelType(Label.valueOf(prefs.get("ICON_TYPE", "BOTH")));
        setIconSize(prefs.getBoolean("SMALL_ICONS", false));
    }

    public void updateActionStatus(boolean status) {
        action.setEnabled(status);
    }

    private class SelectorListener implements Closure {

        public void exec(Object data) {
            updateLabelType(Integer.parseInt(data.toString()));
        }
    }
}
