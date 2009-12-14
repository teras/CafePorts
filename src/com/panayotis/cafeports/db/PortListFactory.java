/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.db;

import com.panayotis.cafeports.config.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.tools.bzip2.CBZip2InputStream;

/**
 *
 * @author teras
 */
public class PortListFactory {

    private static final HashMap<String, Tuplet> hashes = new HashMap<String, Tuplet>();

    public static boolean update(PortList newlist, PortList oldlist, UpdateListener listener) throws PortListException {
        listener.setStage(0);
        boolean base_has_changed = updateBase(newlist, oldlist, listener);
        listener.setStage(1);
        updateInstalled(newlist, listener);
        return base_has_changed;
    }

    private static boolean updateBase(PortList newlist, PortList oldlist, UpdateListener listener) throws PortListException {
        File cfile = new File(Config.base.getPortIndex());
        if (!(cfile.exists() && cfile.isFile() && cfile.canRead()))
            throw new PortListException("File " + cfile.getPath() + " is not parsable.");

        if (oldlist != null && oldlist.isNotUpdated(cfile.lastModified(), cfile.length()))
            newlist.copy(oldlist);
        else {
            BufferedReader in = null;
            int count = 0;
            long all_size = new File(Config.base.getPortIndex()).length();
            long size_now = 0;
            try {
                in = new BufferedReader(new FileReader(Config.base.getPortIndex()));
                String line, line2;
                while ((line = in.readLine()) != null) {
                    line2 = in.readLine();
                    size_now += line.length() + line2.length() + 2;
                    if ((count % 100) == 0) {
                        count = 0;
                        listener.setPercent(((float) size_now) / all_size);
                    }
                    newlist.add(new PortInfo(line, line2));
                    count++;
                }
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

    private static boolean updateInstalled(PortList list, UpdateListener listener) throws PortListException {
        try {
            HashMap<String, String> installed = new HashMap<String, String>();
            File allreceipts = new File(Config.base.getReceiptsDir());
            File receiptfile;
            String portdirname;
            String hashname;
            Tuplet oldtuple, newtuple;
            if (!(allreceipts.exists() && allreceipts.canRead() && allreceipts.isDirectory()))
                throw new PortListException("Unable to initialize " + allreceipts.getPath());

            /* Check all files in receipts */
            File receiptsdir = new File(Config.base.getReceiptsDir());
            File[] receiptslist = receiptsdir.listFiles();
            long allfiles = receiptslist.length;
            long countfiles = 0;
            for (File portdir : receiptslist) {
                if (portdir.isDirectory())
                    /* in each 'port' directory, inside there is a list of other directories - one for each version */
                    for (File versiondir : portdir.listFiles())
                        if (versiondir.isDirectory()) {
                            portdirname = portdir.getName();
                            hashname = portdirname + "/" + versiondir.getName();
                            receiptfile = new File(versiondir, "receipt.bz2");
                            /* Only if this receipt exists */
                            if (receiptfile.isFile() && receiptfile.canRead()) {
                                newtuple = new Tuplet(receiptfile);
                                oldtuple = hashes.get(hashname);
                                /* file found which was not stored before! */
                                if (!newtuple.equals(oldtuple))
                                    hashes.put(hashname, newtuple);
                                System.out.print(hashname + ": ");
                                boolean b = isActive(receiptfile);
                                installed.put(portdir.getName(), portdirname);
                            }
                        }
                countfiles++;
                listener.setPercent(((float) countfiles) / allfiles);
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

    private static boolean isActive(File receiptfile) {
        try {
            FileInputStream fin = new FileInputStream(receiptfile);
            char b = (char) fin.read();
            char z = (char) fin.read();
            if (b != 'B' && z != 'Z')
                fin.reset();

            BufferedReader in = new BufferedReader(new InputStreamReader(new CBZip2InputStream(fin)));
            String line = in.readLine();
            line = in.readLine();
            int loc = line.startsWith("active") ? 0 : line.indexOf("active");
            System.out.println(" - " + loc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private final static class Tuplet {

        private long time;
        private long size;

        private Tuplet(File f) {
            time = f.lastModified();
            size = f.length();
        }

        public boolean equals(Tuplet other) {
            if (other == null)
                return false;
            return other.time == time && other.size == size;
        }
    }
}
