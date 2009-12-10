/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.config;

import java.io.File;
import java.util.HashSet;

/**
 *
 * @author teras
 */
public class Config {

    public static Config base = new Config();
    static final String DEFAULT_PREFIX_DIR = "/opt/localy/";
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
    private HashSet<ConfigListener> listeners;

    public Config() {
        prefix = DEFAULT_PREFIX_DIR;
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

    public synchronized void setPrefix(String prefix) {
        if (!prefix.endsWith(File.separator))
            prefix += File.separator;
        this.prefix = prefix;
        for (ConfigListener listener : listeners)
            listener.configIsUpdated();
    }

    public void addListener(ConfigListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ConfigListener listener) {
        listeners.remove(listener);
    }

    public static boolean isPrefixValid(String prefix) {
        File newport = new File(prefix, BINDIR + PORTCMD);
        if (newport.exists()) {
            File newportindex = new File(prefix, VAR_MACPORTS);
            if (newportindex.exists())
                return true;
        }
        return false;
    }
}
