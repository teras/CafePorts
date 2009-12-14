/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.config;

import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;

/**
 *
 * @author teras
 */
public class ApplicationHandler extends ApplicationAdapter {

    public ApplicationHandler() {
    }

    public void handleAbout(ApplicationEvent event) {
    }

    public void handlePreferences(ApplicationEvent event) {
        JConfiguration.fireDisplay(false);
    }

    public void handleQuit(ApplicationEvent event) {
        System.exit(0);
    }

    public void handleOpenFile(ApplicationEvent event) {
    }
}
