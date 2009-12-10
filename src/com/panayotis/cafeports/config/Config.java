/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.config;

import java.io.File;
import java.util.HashSet;
import java.util.prefs.Preferences;

/**
 *
 * @author teras
 */
public class Config {

    public static Config base = new Config();
    static final String PREFIX_PREF = "PREFIX_DIR";
    /* */
    static final String DEFAULT_PREFIX_DIR = "/opt/local/";
    static final String DEFAULT_SOURCE = "rsync.macports.org";
    /* */
    static final String VAR_MACPORTS = "var/macports/";
    static final String BINDIR = "bin/";
    static final String RELEASE_PORTS = "sources/" + DEFAULT_SOURCE + "/release/ports/";
    /* */
    static final String PORTINDEX = "PortIndex";
    static final String PORTCMD = "port";
    static final String RECEIPTS = "receipts/";
    /* */
    private String prefix;
    private String backup;
    /* */
    private boolean current_prefix_valid;
    private boolean backup_status;
    /* */
    private HashSet<ConfigListener> listeners;
    /* */
    Preferences prefs;

    public Config() {
        prefs = Preferences.userNodeForPackage(Config.class);
        prefix = prefs.get(PREFIX_PREF, DEFAULT_PREFIX_DIR);
        current_prefix_valid = true;
        listeners = new HashSet<ConfigListener>();
    }

    public String getPortIndex() {
        return prefix + VAR_MACPORTS + RELEASE_PORTS + PORTINDEX;
    }

    public String getPortCmd() {
        return prefix + BINDIR + PORTCMD;
    }

    public String getReceiptsDir() {
        return prefix + VAR_MACPORTS + RECEIPTS;
    }

    public String getPrefix() {
        return prefix;
    }

    public void backup() {
        backup = prefix;
        backup_status = current_prefix_valid;
    }

    public void restore() {
        prefix = backup;
        current_prefix_valid = backup_status;
    }

    public synchronized void setPrefix(String prefix) {
        if (!prefix.endsWith(File.separator))
            prefix += File.separator;
        if (this.prefix.equals(prefix))
            return;
        this.prefix = prefix;
        current_prefix_valid = true;    // Be optimistic!
        prefs.put(PREFIX_PREF, prefix);
        if (current_prefix_valid)
            for (ConfigListener listener : listeners)
                listener.configIsUpdated();
    }

    public void addListener(ConfigListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ConfigListener listener) {
        listeners.remove(listener);
    }

    public boolean isPrefixValid() {
        if (!current_prefix_valid)
            return false;
        File newport = new File(prefix, BINDIR + PORTCMD);
        if (newport.exists()) {
            File newportindex = new File(prefix, VAR_MACPORTS);
            if (newportindex.exists())
                return true;
        }
        return false;
    }

    public void setCurrentPrefixInvalid() {
        current_prefix_valid = false;
    }
}
