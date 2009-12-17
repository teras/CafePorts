/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.config.Config;
import com.panayotis.cafeports.gui.listeners.UnifiedDragListener;

import com.panayotis.cafeports.db.PortInfo;
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
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JBar extends JPanel implements ActionListener {

    private final static Preferences prefs = Preferences.userNodeForPackage(Config.class);
    /* */
    private final JPortWindow frame;
    private final JToolButton install;
    private final JToolButton remove;
    private final JToolButton update;
    private final JToolButton activate;
    private final JToolButton deactivate;
    private final JToolButton selfupdate;
    private final JToolButton reload;
    private final JToolButton info;
    private final UnifiedDragListener drag;
    /* */
    private final JSelector sel;
    private final SelectorListener listener;
    private final boolean[] toolbarselection = {true, false, false, false, false};
    private final String[] toolbartext = {"Icon and Text", "Icon only", "Text only", null, "Use Small Size"};

    public JBar(JPortWindow window) {
        this.frame = window;
        drag = new UnifiedDragListener(frame);
        sel = new JSelector();
        listener = new SelectorListener();
        final JPanel lefts = new JPanel();
        final JPanel rights = new JPanel();

        lefts.setLayout(new GridLayout(1, 5));
        rights.setLayout(new GridLayout(1, 2));

        reload = initButton("Reload", "%", false);
        rights.add(reload);
        info = initButton("Info", "?", true);
        rights.add(info);

        install = initButton("Install", "i", false);
        lefts.add(install);
        remove = initButton("Remove", "r", false);
        lefts.add(remove);
        update = initButton(("Upgrade"), "u", false);
        lefts.add(update);
        activate = initButton("Activate", "a", false);
        lefts.add(activate);
        deactivate = initButton("Deactivate", "d", false);
        lefts.add(deactivate);
        selfupdate = initButton("Selfupdate", "s", false);
        lefts.add(selfupdate);

        restoreToolBarType();

        drag.register(this);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 4, 0, 0));
        add(lefts, BorderLayout.WEST);
        add(rights, BorderLayout.EAST);
        setComponentPopupMenu(sel);
        setEnabled(false);
    }

    public void setEnabled(boolean status) {
        super.setEnabled(status);
        install.setEnabled(status);
        remove.setEnabled(status);
        update.setEnabled(status);
        activate.setEnabled(status);
        deactivate.setEnabled(status);
        selfupdate.setEnabled(status);
        reload.setEnabled(status);
        info.setEnabled(status);
    }

    private void setLabelType(Label labeltype) {
        install.setLabelType(labeltype);
        remove.setLabelType(labeltype);
        update.setLabelType(labeltype);
        activate.setLabelType(labeltype);
        deactivate.setLabelType(labeltype);
        selfupdate.setLabelType(labeltype);
        reload.setLabelType(labeltype);
        info.setLabelType(labeltype);

        for (int i = 0; i < 3; i++)
            toolbarselection[i] = false;
        toolbarselection[labeltype.ordinal()] = true;
        sel.setItems(toolbartext, toolbarselection, 0, listener);
        frame.doLayout();
    }

    private void setIconSize(boolean smallSize) {
        install.setIconSize(smallSize);
        remove.setIconSize(smallSize);
        update.setIconSize(smallSize);
        activate.setIconSize(smallSize);
        deactivate.setIconSize(smallSize);
        selfupdate.setIconSize(smallSize);
        reload.setIconSize(smallSize);
        info.setIconSize(smallSize);

        toolbarselection[4] = smallSize;
        sel.setItems(toolbartext, toolbarselection, 0, listener);
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
        String basecommand = null;
        boolean require_ports = false;
        PortInfo[] ports = new PortInfo[0];

        switch (ev.getActionCommand().charAt(0)) {
            case 'i':
                basecommand = "install";
                require_ports = true;
                break;
            case 'r':
                basecommand = "uninstall";
                require_ports = true;
                break;
            case 'u':
                basecommand = "upgrade";
                require_ports = true;
                break;
            case 'a':
                require_ports = true;
                basecommand = "activate";
                break;
            case 'd':
                require_ports = true;
                basecommand = "deactivate";
                break;
            case 's':
                basecommand = "selfupdate";
                break;
            case '%':
                PortListManager.forceReloadData();
                break;
            case '?':
                frame.setInfoVisible(true);
                return;
            default:
        }
        if (require_ports) {
            ports = frame.getJPortList().getSelectedPorts();
            if (ports == null || ports.length == 0)
                return;
        }
        Closure self_l = new Closure() {

            public void exec(Object line) {
                PortListManager.updateData();
            }
        };
        PortProcess.exec(basecommand, ports, self_l);
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

    private class SelectorListener implements Closure {

        public void exec(Object data) {
            updateLabelType(Integer.parseInt(data.toString()));
        }
    }
}
