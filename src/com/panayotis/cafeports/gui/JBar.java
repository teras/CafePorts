/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.gui.listeners.UnifiedDragListener;
import static com.panayotis.cafeports.gui.JToolButton.Location.*;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.db.PortListManager;
import com.panayotis.utilities.Closure;
import com.panayotis.cafeports.gui.installer.PortProcess;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JBar extends JPanel implements ActionListener {

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

    public JBar(JPortWindow window) {
        this.frame = window;
        drag = new UnifiedDragListener(frame);
        final JPanel lefts = new JPanel();
        final JPanel rights = new JPanel();

        lefts.setLayout(new GridLayout(1, 5));
        rights.setLayout(new GridLayout(1, 2));

        reload = initButton("Reload", ONLY, "%", false);
        rights.add(reload);
        info = initButton("Info", ONLY, "?", true);
        rights.add(info);

        install = initButton("Install", FIRST, "i", false);
        lefts.add(install);
        remove = initButton("Remove", LAST, "r", false);
        lefts.add(remove);
        update = initButton(("Upgrade"), FIRST, "u", false);
        lefts.add(update);
        activate = initButton("Activate", MIDDLE, "a", false);
        lefts.add(activate);
        deactivate = initButton("Deactivate", LAST, "d", false);
        lefts.add(deactivate);
        selfupdate = initButton("Selfupdate", ONLY, "s", false);
        lefts.add(selfupdate);

        drag.register(this);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 4, 0, 0));
        add(lefts, BorderLayout.WEST);
        add(rights, BorderLayout.EAST);
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

    private final JToolButton initButton(String label, JToolButton.Location position, String actioncommand, boolean toggle) {
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
                frame.setInfoVisible(((JToggleButton) ev.getSource()).isSelected());
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
}
