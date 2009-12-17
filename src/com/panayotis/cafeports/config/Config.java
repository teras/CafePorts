/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.prefs.Preferences;

/**
 *
 * @author teras
 */
public class Config {

    public static Config base = new Config();
    private static final String PREFIX_PREF = "PREFIX_DIR";
    /* */
    static final String DEFAULT_PREFIX_DIR = "/opt/local/";
    /* */
    static final String PORTCOMMAND = "bin/port";
    static final String SOURCESLIST = "etc/macports/sources.conf";
    static final String MACPORTS = "var/macports/sources/";
    static final String RECEIPTSDIR = "var/macports/receipts/";
    /* */
    static final String PORTINDEXFILE = "PortIndex";
    /* */
    private String prefix;
    private boolean current_prefix_valid;
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

    public ArrayList<String> getPortIndices() {
        BufferedReader in = null;
        ArrayList<String> res = new ArrayList<String>();
        try {
            String line;
            in = new BufferedReader(new FileReader(new File(prefix, SOURCESLIST)));
            while ((line = in.readLine()) != null) {
                line = line.replace("[default]", "").replace("[nosync]", "");
                line = line.trim();
                if (line.startsWith("#"))
                    line = "";
                if (!line.equals("")) {
                    if (line.startsWith("file://"))
                        line = line.substring("file://".length());
                    else if (line.startsWith("rsync://"))
                        line = prefix + MACPORTS + line.substring("rsync://".length());
                    if (!line.endsWith("/"))
                        line += "/";
                    line.trim();
                    if (!line.equals(""))
                        res.add(line + PORTINDEXFILE);
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }
        return res;
    }

    public String getPortCmd() {
        return prefix + PORTCOMMAND;
    }

    public String getReceiptsDir() {
        return prefix + RECEIPTSDIR;
    }

    public String getPrefix() {
        return prefix;
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
        return (new File(prefix, SOURCESLIST).exists());
    }

    public void setCurrentPrefixInvalid() {
        current_prefix_valid = false;
    }
}
