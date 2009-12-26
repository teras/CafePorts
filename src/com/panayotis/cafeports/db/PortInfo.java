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
                store(values, key, val);
            flipflop = !flipflop;
        }
    }

    private static void store(HashMap<String, String> values, String key, String value) {
        /* Fix email */
        if (key.equals("maintainers"))
            if (!value.equals("nomaintainer")) {
                StringBuffer buffer = new StringBuffer();
                StringTokenizer tk = new StringTokenizer(value);
                while (tk.hasMoreTokens()) {
                    String person = tk.nextToken();
                    int atsign = person.indexOf("@");
                    if (atsign >= 0)
                        buffer.append(person);
                    else {
                        int colon = person.indexOf(":");
                        if (colon < 0)
                            buffer.append(person).append("@macports.org");
                        else
                            buffer.append(person.substring(colon + 1)).append('@').append(person.substring(0, colon));
                    }
                    buffer.append(',');
                }
                value = buffer.substring(0, buffer.length() - 1);
            } else
                value = null;

        if (value != null) {
            value = value.replace("{", "").replace("}", "");
            values.put(key, value);
        }
    }

    private static final int countChar(String str, char q) {
        int found = 0;
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == q)
                found++;
        return found;
    }

    public void setInstalledInfo(String vers) {
        if (vers == null) {
            values.remove("installflags");
            values.remove("installed_version");
        } else {
            String version = vers.substring(1, vers.indexOf(":"));
            String installflags = (vers.charAt(0) == '✔' ? "✔" : "◼") + (version.equals(getData("version")) ? "=" : ">");
            values.put("installflags", installflags);
            values.put("installed_version", version);
        }
    }

    public String toString() {
        return "[Port " + values.get("name") + " " + values.get("version") + "]";
    }
}
