/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.config;

/**
 *
 * @author teras
 */
public class Config {

    public static Config base = new Config();
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

    public Config() {
        prefix = DEFAULT_PREFIX_DIR;
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

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
}
