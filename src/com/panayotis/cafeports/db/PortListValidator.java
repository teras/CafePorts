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
public class PortListValidator {

    private static PortListValidator plv;

    /* */
    private final JPortWindow window;

    private final static PortListValidator getValidator() {
        if (plv == null)
            throw new NullPointerException("PortListValidator not initialized yet!");
        return plv;
    }

    public final static void init(JPortWindow window) {
        if (window == null)
            throw new NullPointerException("PortListValidator window should not be null");
        plv = new PortListValidator(window);

    }

    public final static void forceReloadData() {
        PortList.invalidatePortLists();
        updateData();
    }

    public static void updateData() {
        PortList.updateBaseList();
        getValidator().window.setStatus(JPortWindow.Status.OK);
    }

    private PortListValidator(JPortWindow window) {
        this.window = window;
        Config.base.addListener(new ConfigListener() {

            public void configIsUpdated() {
                JConfiguration.dialog.setVisible(false);
                loadData();
            }
        });
        loadData();
    }

    public synchronized void loadData() {
        new Thread() {

            public void run() {
                if (!Config.base.isPrefixValid()) {
                    window.setStatus(JPortWindow.Status.ERROR);
                    JConfiguration.dialog.fireDisplay();
                    return;
                } else
                    window.setStatus(JPortWindow.Status.LOADING);

                try {
                    updateData();
                    JConfiguration.dialog.setVisible(false);
                } catch (PortListException ex) {
                    Config.base.setCurrentPrefixInvalid();
                    window.setStatus(JPortWindow.Status.ERROR);
                    JOptionPane.showMessageDialog(null, "Unable to initialize Macports\n" + ex.getMessage());
                    JConfiguration.dialog.fireDisplay();
                    return;
                }
            }
        }.start();
    }
}
