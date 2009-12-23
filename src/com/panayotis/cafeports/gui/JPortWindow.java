/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.db.PortList;
import com.panayotis.cafeports.db.UpdateListener;
import com.panayotis.cafeports.gui.portinfo.JPortInfo;
import com.panayotis.cafeports.gui.table.JPortList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author teras
 */
public class JPortWindow extends JFrame {

    private final JFilters filters;
    private final JWaitingLabel initialization;
    private final JPanel mainview;
    private final JPortList portlist;
    private final JPortInfo info;
    private final JBar bar;
    private final JBottomBar bottombar;

    public JPortWindow() {
        super();
        getRootPane().putClientProperty("apple.awt.brushMetalLook", Boolean.TRUE);

        filters = new JFilters(this);
        initialization = new JWaitingLabel();
        mainview = new JPanel(new BorderLayout());
        portlist = new JPortList();
        info = new JPortInfo(this);
        bar = new JBar(this);
        bottombar = new JBottomBar(this);
        JPanel viewport = new JPanel(new BorderLayout());

        mainview.add(initialization, BorderLayout.NORTH);
        mainview.add(portlist, BorderLayout.CENTER);

        viewport.add(mainview, BorderLayout.CENTER);
        viewport.add(bar, BorderLayout.NORTH);
        viewport.add(bottombar, BorderLayout.SOUTH);
        getContentPane().add(viewport);

        portlist.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent ev) {
                PortInfo[] selected = portlist.getSelectedPorts();
                if (selected == null || selected.length < 1)
                    info.updateInfo(null);
                else
                    info.updateInfo(selected[0]);
                bottombar.setHowMany(PortList.countBasePortList(), PortList.getFilteredPortList().getSize(), selected.length);
            }
        });

        addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent ev) {
                info.updateLocation();
            }

            public void componentMoved(ComponentEvent ev) {
                info.updateLocation();
            }
        });

        setSize(600, 400);
        setTitle("CaféPorts");
        setSizeLocked();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setStatus(final Status status, final String message) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    mainview.remove(filters);
                    mainview.remove(initialization);
                    switch (status) {
                        case LOADING_1:
                        case LOADING_2:
                            mainview.add(initialization, BorderLayout.NORTH);
                            initialization.setWaiting(status == Status.LOADING_2);
                            portlist.clearList();
                            bar.setEnabled(false);
                            break;
                        case ERROR:
                            mainview.add(initialization, BorderLayout.NORTH);
                            initialization.setInvalidPath(message);
                            portlist.clearList();
                            bar.setEnabled(false);
                            break;
                        case OK:
                            filters.requestListUpdate();
                            mainview.add(filters, BorderLayout.NORTH);
                            bar.setEnabled(true);
                            updateList();
                            break;
                    }
                    validate();
                    repaint();
                }
            });
        } catch (Exception ex) {
        }
    }

    public void setInfoVisible(boolean status) {
        info.setVisible(status);
    }

    public Dimension getMinimumSize() {
        return new Dimension(390, 270);
    }

    public void setSizeLocked() {
        final int x = getMinimumSize().width;
        final int y = getMinimumSize().height;
        addComponentListener(new java.awt.event.ComponentAdapter() {

            public void componentResized(ComponentEvent event) {
                setSize((getWidth() < x) ? x : getWidth(), (getHeight() < y) ? y : getHeight());
                repaint();
            }
        });
    }

    public JPortList getJPortList() {
        return portlist;
    }

    public JFilters getFilters() {
        return filters;
    }

    public UpdateListener getUpdateListener() {
        return initialization;
    }

    public void updateList() {
        portlist.updatePortList();
        bottombar.setHowMany(PortList.countBasePortList(), PortList.getFilteredPortList().getSize(), 0);
    }

    public enum Status {

        LOADING_1, LOADING_2, ERROR, OK
    }
}
