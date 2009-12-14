/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.db;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author teras
 */
public class PortInfo implements Comparable {

    private final HashMap<String, String> values;

    public PortInfo(String line1, String line2) {
        values = new HashMap<String, String>();
        //    values.put("name", line1.substring(0, line1.indexOf(' ')));
        getValues(values, line2);
    }

    public String getData(String dataname) {
        return values.get(dataname);
    }

    public int compareTo(Object o) {
        if (!(o instanceof PortInfo))
            return -1;
        String v = values.get("name");
        if (v == null)
            return -1;
        return v.compareTo(((PortInfo) o).values.get("name"));
    }

    private static final void getValues(HashMap<String, String> values, String data) {
        boolean flipflop = true;
        String tk;
        String val;
        String key = null;
        StringTokenizer tknzr = new StringTokenizer(data, " ");
        int openB, closeB;
        while (tknzr.hasMoreTokens()) {
            tk = tknzr.nextToken();
            val = tk;
            if (tk.charAt(0) == '{') {
                /* Lazy counting */
                openB = countChar(tk, '{');
                closeB = countChar(tk, '}');
                /* search for closing bracket */
                while (openB > closeB && tknzr.hasMoreTokens()) {
                    tk = tknzr.nextToken();
                    openB += countChar(tk, '{');
                    closeB += countChar(tk, '}');
                    val += " " + tk;
                }
                val = val.substring(1, val.length() - 1);
            }
            if (flipflop)
                key = val;
            else
                values.put(key, val);
            flipflop = !flipflop;
        }
    }

    private static final int countChar(String str, char q) {
        int found = 0;
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == q)
                found++;
        return found;
    }

    public void setInstalledVersion(String vers) {
        if (vers == null) {
            values.remove("isinstalled");
            values.remove("installed_version");
        } else {
            values.put("isinstalled", "✔");
            values.put("installed_version", vers);
        }
    }

    public String toString() {
        return "[Port " + values.get("name") + " " + values.get("version") + "]";
    }
}
