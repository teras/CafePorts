/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.db;

import com.panayotis.cafeports.config.Config;
import com.panayotis.cafeports.config.ConfigListener;
import com.panayotis.cafeports.config.JConfiguration;
import com.panayotis.cafeports.gui.JPortWindow;
import javax.swing.JOptionPane;

/**
 *
 * @author teras
 */
public class PortListManager {

    private static PortListManager plv;

    /* */
    private final JPortWindow window;

    private final static PortListManager getValidator() {
        if (plv == null)
            throw new NullPointerException("PortListValidator not initialized yet!");
        return plv;
    }

    public final static void init(JPortWindow window) {
        if (window == null)
            throw new NullPointerException("PortListValidator window should not be null");
        plv = new PortListManager(window);

    }

    public final static void forceReloadData() {
        PortList.invalidatePortLists();
        updateData();
    }

    public synchronized static void updateData() {
        new Thread() {

            public void run() {
                if (!Config.base.isPrefixValid()) {
                    getValidator().window.setStatus(JPortWindow.Status.ERROR);
                    JConfiguration.dialog.fireDisplay();
                    return;
                } else
                    getValidator().window.setStatus(JPortWindow.Status.LOADING);

                try {
                    /* The following line will load everything and set filters.
                     * Will break here, if not right
                     */
                    getValidator().window.getFilters().requestListUpdate();
                    /* Everything OK */
                    getValidator().window.setStatus(JPortWindow.Status.OK);
                    JConfiguration.dialog.setVisible(false);
                } catch (PortListException ex) {
                    getValidator().window.setStatus(JPortWindow.Status.ERROR);
                    Config.base.setCurrentPrefixInvalid();
                    JOptionPane.showMessageDialog(null, "Unable to initialize Macports\n" + ex.getMessage());
                    JConfiguration.dialog.fireDisplay();
                    return;
                }
            }
        }.start();
    }

    private PortListManager(JPortWindow window) {
        this.window = window;
        Config.base.addListener(new ConfigListener() {

            public void configIsUpdated() {
                JConfiguration.dialog.setVisible(false);
                updateData();
            }
        });
        updateData();
    }
}
