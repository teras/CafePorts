/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.db.PortList;
import com.panayotis.cafeports.db.PortListValidator;
import com.panayotis.utilities.Closure;
import com.panayotis.cafeports.gui.installer.PortProcess;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JBar extends JPanel {

    private final JPortWindow frame;
    private final AbstractButton install;
    private final AbstractButton remove;
    private final AbstractButton update;
    private final AbstractButton activate;
    private final AbstractButton deactivate;
    private final AbstractButton selfupdate;
    private final AbstractButton reload;
    private final AbstractButton info;

    public JBar(JPortWindow window) {
        this.frame = window;
        final Component comp = this;
        final Point oldpos = new Point();
        final Point newpos = new Point();
        final JPanel lefts = new JPanel();
        final JPanel rights = new JPanel();

        lefts.setLayout(new GridLayout(1, 5));
        rights.setLayout(new GridLayout(1, 2));

        reload = initButton("Reload", "only", "%", false);
        rights.add(reload);
        info = initButton("Info", "only", "?", true);
        rights.add(info);

        install = initButton("Install", "first", "i", false);
        lefts.add(install);
        remove = initButton("Remove", "last", "r", false);
        lefts.add(remove);
        update = initButton(("Update"), "first", "u", false);
        lefts.add(update);
        activate = initButton("Activate", "middle", "a", false);
        lefts.add(activate);
        deactivate = initButton("Deactivate", "last", "d", false);
        lefts.add(deactivate);
        selfupdate = initButton("Self update", "only", "s", false);
        lefts.add(selfupdate);

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 4, 0, 0));
        add(lefts, BorderLayout.WEST);
        add(rights, BorderLayout.EAST);

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                Component c = e.getComponent();
                while (c.getParent() != null)
                    c = c.getParent();
                oldpos.setLocation(e.getPoint());
                SwingUtilities.convertPointToScreen(oldpos, comp);
                oldpos.x -= frame.getX();
                oldpos.y -= frame.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                newpos.setLocation(e.getPoint());
                SwingUtilities.convertPointToScreen(newpos, comp);
                frame.setLocation(newpos.x - oldpos.x, newpos.y - oldpos.y);
            }
        });
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

    private final AbstractButton initButton(String label, String position, String actioncommand, boolean toggle) {
        AbstractButton button;
        if (toggle)
            button = new JToggleButton();
        else
            button = new JButton();
        button.putClientProperty("JButton.buttonType", "segmentedCapsule");
        button.putClientProperty("JButton.segmentPosition", position);
        button.setIcon(new ImageIcon(getClass().getResource("/icons/" + label.replace(" ", "").toLowerCase() + ".png")));
        button.setToolTipText(label);
        button.setFocusable(false);
        button.setActionCommand(actioncommand);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ev) {
                callAction(ev);
            }
        });
        return button;
    }

    private void callAction(ActionEvent ev) {
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
            case 's':
                basecommand = "selfupdate";
                break;
            case 'a':
                require_ports = true;
                basecommand = "activate";
                break;
            case 'd':
                require_ports = true;
                basecommand = "deactivate";
                break;
            case '%':
                PortListValidator.forceReloadData();
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
                PortListValidator.updateData();
            }
        };
        PortProcess.exec(basecommand, ports, self_l);
    }
}
