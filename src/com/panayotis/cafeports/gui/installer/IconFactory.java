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
        map.put("dependencies", "Computing dependencies for");
        map.put("fetching", "Fetching");
        map.put("fattempt", "Attempting to fetch");
        map.put("verifing", "Verifying checksum(s) for");
        map.put("extracting", "Extracting");
        map.put("configuring", "Configuring");
        map.put("building", "Building");
        map.put("staging", "Staging");
        map.put("installing", "Installing");
        map.put("activating", "Activating");
        map.put("cleaning", "Cleaning");
        map.put("patches", "Applying patches");
        map.put("deactivating", "Deactivating");
        map.put("uninstalling", "Uninstalling");
    }

    public static Icon getIconByLine(String line) {
        if (line == null)
            return getIconByName(DEFAULT_ICON);
        line = getIconText(line);
        for (String key : map.keySet())
            if (line.startsWith(map.get(key))) {
                Icon icon = icons.get(key);
                if (icon == null) {
                    icon = getIconByName(key);
                    if (icon == null)
                        System.out.println("Error while fetching " + key);
                    icons.put(key, icon);
                }
                return icon;
            }
        return getIconByName(DEFAULT_ICON);
    }

    public static Icon getIconByName(String name) {
        return new ImageIcon(IconFactory.class.getResource(LOCATION + name + ".png"));
    }

    public static String getIconText(String line) {
        if (line == null)
            return "Initializing...";
        return line.startsWith(HEADER) ? line.substring(HEADER.length()) : line;
    }
}
