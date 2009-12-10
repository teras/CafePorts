/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.db;

import com.panayotis.cafeports.filter.FilterChain;
import com.panayotis.cafeports.filter.FilterFactory.Filter;
import com.panayotis.utilities.ImmutableList;
import com.panayotis.utilities.MutableArray;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author teras
 */
public class PortList {

    private static PortList base = null;
    private static PortList filtered = null;
    private static PortList empty = new PortList();
    /* */
    private final MutableArray<PortInfo> list = new MutableArray<PortInfo>();
    private final HashMap<String, Vector<String>> cats;

    private PortList() {
        cats = null;
    }

    private PortList(PortList oldlist) throws PortListException {
        if (oldlist == null) {
            PortParser.init(this);
            Collections.sort(list);
            cats = new HashMap<String, Vector<String>>();
            PortParser.getCategoryWithTag(this, "variants");
            PortParser.getCategoryWithTag(this, "categories");
            PortParser.getCategoryWithTag(this, "platforms");
        } else
            cats = oldlist.cats;
    }

    private static final PortList getBasePortList() {
        if (base == null)
            try {
                base = new PortList(null);
            } catch (PortListException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        return base;
    }

    public static final PortList getEmptyPortList() {
        return empty;
    }

    public static final PortList getFilteredPortList() {
        if (filtered == null)
            filtered = getBasePortList();
        return filtered;
    }

    public static void updateBaseList() {
        try {
            PortParser.updateInstalled(getBasePortList());
        } catch (PortListException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static final void updateFilters(FilterChain chain) {
        try {
            filtered = new PortList(getBasePortList()); // empty port list
        } catch (PortListException ex) {
        }
        boolean valid;
        for (PortInfo p : getBasePortList().getList()) {
            valid = true;
            for (Filter f : chain.getList())
                if (!f.isValid(p)) {
                    valid = false;
                    break;
                }
            if (valid)
                filtered.add(p);
        }
    }

    public void add(PortInfo portInfo) {
        list.add(portInfo);
    }

    public String toString() {
        return "[List of " + list.size() + " ports]";
    }

    public int getSize() {
        return list.size();
    }

    public PortInfo getItem(int rowIndex) {
        return list.get(rowIndex);
    }

    public ImmutableList<PortInfo> getList() {
        return list;
    }

    public void addCategory(String tag, Vector<String> items) {
        cats.put(tag, items);
    }

    public Vector<String> getCategory(String tag) {
        return cats.get(tag);
    }
}
