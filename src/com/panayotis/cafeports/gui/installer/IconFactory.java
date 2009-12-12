/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.installer;

import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author teras
 */
public class IconFactory {

    public final static String DEFAULT_ICON = "default";
    public final static String WAIT_ICON = "wait";
    public final static String OK_ICON = "ok";
    public final static String ERROR_ICON = "error";
    private final static String LOCATION = "/icons/stages/";
    private final static String HEADER = "--->  ";
    private final static HashMap<String, String> map = new HashMap<String, String>();
    private final static HashMap<String, Icon> icons = new HashMap<String, Icon>();

    static {
        map.put("Computing dependencies for", "dependencies");
        map.put("Fetching", "fetching");
        map.put("Attempting to fetch", "fattempt");
        map.put("Verifying checksum(s) for", "verifing");
        map.put("Extracting", "extracting");
        map.put("Configuring", "configuring");
        map.put("Building", "building");
        map.put("Staging", "staging");
        map.put("Installing", "installing");
        map.put("Activating", "activating");
        map.put("Cleaning", "cleaning");
        map.put("Applying patches", "patches");
        map.put("Deactivating", "deactivating");
        map.put("Uninstalling", "uninstalling");
        map.put("Updating", "updating");
        map.put("MacPorts base is", "updating");
    }

    public static Icon getIconByLine(String line) {
        if (line == null)
            return getIconByName(DEFAULT_ICON);
        line = getIconText(line);
        for (String keyname : map.keySet())
            if (line.startsWith(keyname)) {
                String iconname = map.get(keyname);
                Icon icon = icons.get(iconname);
                if (icon == null) {
                    icon = getIconByName(iconname);
                    if (icon == null)
                        System.out.println("Error while fetching " + iconname);
                    icons.put(iconname, icon);
                }
                return icon;
            }
        return getIconByName(DEFAULT_ICON);
    }

    public static Icon getIconByName(String name) {
        return new ImageIcon(IconFactory.class.getResource(LOCATION + name + ".png"));
    }

    public static String getIconText(String line) {
        return line.substring(HEADER.length());
    }

    static boolean shoudUpdateDisplay(String line) {
        return line != null && line.startsWith(HEADER);
    }
}
