/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.config;

import com.apple.eawt.Application;

/**
 *
 * @author teras
 */
public class CafePortsApp extends Application {

    public CafePortsApp() {
        setEnabledPreferencesMenu(true);
        addApplicationListener(new ApplicationHandler());
    }
}
