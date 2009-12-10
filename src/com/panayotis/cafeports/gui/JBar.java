/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.db.PortList;
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

    private final JPanel lefts;
    private final JPortWindow frame;

    public JBar(JPortWindow window) {
        lefts = new JPanel();
        this.frame = window;

        final Point oldpos = new Point();
        final Point newpos = new Point();
        final Component comp = this;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 4, 0, 0));

        lefts.setLayout(new GridLayout(1, 5));
        add(lefts, BorderLayout.WEST);

        AbstractButton expand = initButton("Info", "only", "?", true);
        add(expand, BorderLayout.EAST);


        AbstractButton install = initButton("Install", "first", "i", false);
        lefts.add(install);
        AbstractButton remove = initButton("Remove", "last", "r", false);
        lefts.add(remove);
//        AbstractButton update = initButton("Update", "only", "u", false);
//        lefts.add(update);
//        AbstractButton activate = initButton("Activate", "first", "a", false);
//        lefts.add(activate);
//        AbstractButton deactivate = initButton("Deactivate", "last", "d", false);
//        lefts.add(deactivate);

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
    }

    private final AbstractButton initButton(String label, String position, String actioncommand, boolean toggle) {
        AbstractButton button;
        if (toggle)
            button = new JToggleButton();
        else
            button = new JButton();
        button.putClientProperty("JButton.buttonType", "segmentedCapsule");
        button.putClientProperty("JButton.segmentPosition", position);
        button.setIcon(new ImageIcon(getClass().getResource("/icons/" + label.toLowerCase() + ".png")));
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
        switch (ev.getActionCommand().charAt(0)) {
            case 'i':
                basecommand = "install";
                break;
            case 'r':
                basecommand = "uninstall";
                break;
            case 'u':
                basecommand = "update";
                break;
            case 'a':
                basecommand = "activate";
                break;
            case 'd':
                basecommand = "deactivate";
                break;
            case '?':
                frame.setInfoVisible(((JToggleButton)ev.getSource()).isSelected());
                return;
            default:
        }
        PortInfo[] ports = frame.getJPortList().getSelectedPorts();
        if (ports == null || ports.length == 0)
            return;
        Closure self_l = new Closure() {

            public void exec(Object line) {
                PortList.updateBaseList();
                frame.getFilters().requestListUpdate();
            }
        };
        PortProcess.exec(basecommand, ports, self_l);
    }
}
