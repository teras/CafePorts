/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.db;

import com.panayotis.cafeports.config.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author teras
 */
public class PortParser {

    public static void init(PortList list) throws PortListException {
        readDB(list);
        updateInstalled(list);
    }

    private static void readDB(PortList list) throws PortListException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(Config.base.getPortIndex()));
            String line;
            while ((line = in.readLine()) != null)
                list.add(new PortInfo(line, in.readLine()));
        } catch (Exception ex) {
            throw new PortListException(ex.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }
    }

    public static void updateInstalled(PortList list) throws PortListException {
        try {
            HashMap<String, String> installed = new HashMap<String, String>();
            for (File port : new File(Config.base.getReceiptsDir()).listFiles())
                if (port.isDirectory())
                    for (File vers : port.listFiles())
                        if (vers.isDirectory()) {
                            installed.put(port.getName(), vers.getName());
                            break;
                        }
            for (PortInfo port : list.getList())
                port.setInstalledVersion(installed.get(port.getData("name")));
        } catch (Exception ex) {
            throw new PortListException(ex.getMessage());
        }
    }

    public static void getCategoryWithTag(PortList list, String tag) {
        HashSet<String> set = new HashSet<String>();
        String data;
        for (PortInfo item : list.getList()) {
            data = item.getData(tag);
            if (data != null) {
                StringTokenizer tk = new StringTokenizer(data);
                while (tk.hasMoreTokens())
                    set.add(tk.nextToken());
            }
        }
        Vector<String> out = new Vector(set);
        Collections.sort(out);
        list.addCategory(tag, out);
    }
}
