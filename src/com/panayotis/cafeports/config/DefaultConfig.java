/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.config;

/**
 *
 * @author teras
 */
public class DefaultConfig {

    public static final String DEFAULT_DIR = "/opt/local/";
    public static final String DEFAULT_SOURCE = "rsync.macports.org";
    public static final String MACPORTS = DEFAULT_DIR + "var/macports/";
    /* */
    public static final String BINDIR = DEFAULT_DIR + "bin/";
    public static final String PORTCMD = BINDIR + "port";
    /* */
    public static final String RELEASE_PORTS = MACPORTS + "sources/" + DEFAULT_SOURCE + "/release/ports/";
    public static final String PORTINDEX = RELEASE_PORTS + "PortIndex";
    /* */
    public static final String RECEIPTS = MACPORTS + "receipts/";
}
