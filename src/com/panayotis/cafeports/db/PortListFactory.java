/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.db;

import com.panayotis.cafeports.config.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author teras
 */
public class PortListFactory {

    private static final HashMap<String, Tuplet> hashes = new HashMap<String, Tuplet>();

    public static boolean update(PortList newlist, PortList oldlist) throws PortListException {
        boolean base_has_changed = updateBase(newlist, oldlist);
        updateInstalled(newlist);
        return base_has_changed;
    }

    private static boolean updateBase(PortList newlist, PortList oldlist) throws PortListException {
        File cfile = new File(Config.base.getPortIndex());
        if (!(cfile.exists() && cfile.isFile() && cfile.canRead()))
            throw new PortListException("File " + cfile.getPath() + " is not parsable.");

        if (oldlist != null && oldlist.isNotUpdated(cfile.lastModified(), cfile.length()))
            newlist.copy(oldlist);
        else {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(Config.base.getPortIndex()));
                String line;
                while ((line = in.readLine()) != null)
                    newlist.add(new PortInfo(line, in.readLine()));
                newlist.sort();
            } catch (Exception ex) {
                throw new PortListException(ex.getMessage());
            } finally {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
        newlist.setUpdated(cfile.lastModified(), cfile.length());
        return true;
    }

    private static boolean updateInstalled(PortList list) throws PortListException {
        try {
            HashMap<String, String> installed = new HashMap<String, String>();
            File receipts = new File(Config.base.getReceiptsDir());
            File recp;
            String name;
            Tuplet oldtup, newtup;
            String data;
            if (!(receipts.exists() && receipts.canRead() && receipts.isDirectory()))
                throw new PortListException("Unable to initialize " + receipts.getPath());

            for (File port : new File(Config.base.getReceiptsDir()).listFiles())
                if (port.isDirectory())
                    for (File vers : port.listFiles())
                        if (vers.isDirectory()) {
                            name = port.getName();
                            recp = new File(vers, "receipt.bz2");
                            if (recp.isFile() && recp.canRead()) {
                                oldtup = hashes.get(name);
                                newtup = new Tuplet(recp);
                                hashes.put(name, newtup);
                                installed.put(port.getName(), name);
                            }
                        }
            for (PortInfo port : list.getList())
                port.setInstalledVersion(installed.get(port.getData("name")));
        } catch (Exception ex) {
            throw new PortListException(ex.getMessage());
        }
        return true;
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

    private final static class Tuplet {

        private long time;
        private long size;

        private Tuplet(File f) {
            time = f.lastModified();
            size = f.length();
        }
    }
}
