/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.db;

import com.panayotis.cafeports.config.Config;
import com.panayotis.cafeports.config.ConfigListener;
import com.panayotis.cafeports.gui.JPortWindow;

/**
 *
 * @author teras
 */
public class PortListManager {

    private static PortListManager manager;
    /* */
    private final JPortWindow window;

    private final static PortListManager getValidator() {
        if (manager == null)
            throw new NullPointerException("PortListValidator not initialized yet!");
        return manager;
    }

    public final static void init(JPortWindow window) {
        if (window == null)
            throw new NullPointerException("PortListValidator window should not be null");
        manager = new PortListManager(window);
        updateData();
    }

    public final static void forceReloadData() {
        PortList.invalidatePortLists();
        updateData();
    }

    public synchronized static void updateData() {
        Thread work = new Thread() {

            public void run() {
                if (!Config.base.isPrefixValid()) {
                    getValidator().window.setStatus(JPortWindow.Status.ERROR, "Prefix directory " + Config.base.getPrefix() + " is not valid");
                    return;
                } else
                    getValidator().window.setStatus(JPortWindow.Status.LOADING_1, null);
                try {
                    /*  Will break here, if not right */
                    PortList.initBaseList(getValidator().window.getUpdateListener());
                    /* Everything OK */
                    getValidator().window.setStatus(JPortWindow.Status.OK, null);
                } catch (PortListException ex) {
                    getValidator().window.setStatus(JPortWindow.Status.ERROR, ex.getMessage());
                    Config.base.setCurrentPrefixInvalid();
                    return;
                }
            }
        };
        work.start();
    }

    private PortListManager(JPortWindow window) {
        this.window = window;
        Config.base.addListener(new ConfigListener() {

            public void configIsUpdated() {
                updateData();
            }
        });
    }
}
