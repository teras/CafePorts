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
    private final HashMap<String, Vector<String>> cats;
    private MutableArray<PortInfo> list = new MutableArray<PortInfo>();
    private long this_last_modified;
    private long this_last_size;

    private PortList() {
        cats = null;
    }

    private PortList(PortList oldy, boolean updatable) {
        this_last_modified = -1;
        this_last_size = -1;
        if (updatable) {
            if (oldy == null)
                oldy = this;
            if (PortListFactory.update(oldy)) {
                Collections.sort(oldy.list);
                cats = new HashMap<String, Vector<String>>();
                PortListFactory.getCategoryWithTag(this, "variants");
                PortListFactory.getCategoryWithTag(this, "categories");
                PortListFactory.getCategoryWithTag(this, "platforms");
            } else {
                list = oldy.list;
                cats = oldy.cats;
            }
        } else
            cats = oldy.cats;
        this_last_modified = oldy.this_last_modified;
        this_last_size = oldy.this_last_size;
    }

    public static final void invalidatePortLists() {
        base = null;
        filtered = null;
    }

    public static final PortList getEmptyPortList() {
        return empty;
    }

    public static final PortList getFilteredPortList() {
        return filtered;
    }

    public static void initBaseList() {
        base = new PortList(base, true);
        filtered = base;
    }

    public static final void updateFilters(FilterChain chain) {
        filtered = new PortList(base, false); // Will initialize port list, if not present
        boolean valid;
        for (PortInfo p : base.getList()) {
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

    boolean isNotUpdated(long lastModified, long length) {
        return (this_last_modified == lastModified && this_last_size == length);
    }

    void setUpdated(long lastModified, long length) {
        this_last_modified = lastModified;
        this_last_size = length;
    }
}
