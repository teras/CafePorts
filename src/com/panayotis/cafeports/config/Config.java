/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.config;

import com.panayotis.cafeports.db.PortList;
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
    private static final String PREF_PREFIX = "PREFIX_DIR";
    private static final String PREF_SUDO = "USE_SUDO";
    private static final String PREF_CUSTOMCMD = "USE_SUDO";
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
    private boolean current_prefix_valid;
    private HashSet<ConfigListener> listeners;
    private Preferences prefs;
    /* */
    private String prefix;
    private String customlauncher;
    private boolean useSUDO;

    public Config() {
        current_prefix_valid = true;
        listeners = new HashSet<ConfigListener>();
        prefs = Preferences.userNodeForPackage(Config.class);

        prefix = prefs.get(PREF_PREFIX, DEFAULT_PREFIX_DIR);
        useSUDO = prefs.getBoolean(PREF_SUDO, true);
        customlauncher = prefs.get(PREF_CUSTOMCMD, "");
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

    public String getPrefix() {
        return prefix;
    }

    public synchronized void setPrefix(String prefix) {
        if (!prefix.endsWith(File.separator))
            prefix += File.separator;
        if (this.prefix.equals(prefix))
            return;
        PortList.invalidatePortLists();
        this.prefix = prefix;
        current_prefix_valid = true;    // Be optimistic!
        prefs.put(PREF_PREFIX, prefix);
        if (current_prefix_valid)
            for (ConfigListener listener : listeners)
                listener.configIsUpdated();
    }

    public boolean isWithSudo() {
        return useSUDO;
    }

    public void setWithSudo(boolean sudostatus) {
        useSUDO = sudostatus;
        prefs.putBoolean(PREF_SUDO, useSUDO);
    }

    public String getLaunchCommand() {
        return customlauncher;
    }

    public void setLaunchCommand(String launchcmd) {
        customlauncher = launchcmd;
        prefs.put(PREF_CUSTOMCMD, customlauncher);
    }
}
