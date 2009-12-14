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
    private long this_last_modified;
    private long this_last_size;

    private PortList() {
        cats = null;
    }

    private PortList(PortList oldlist) {
        this_last_modified = -1;
        this_last_size = -1;
        if (oldlist == null) {
            PortListFactory.update(this);
            Collections.sort(list);
            cats = new HashMap<String, Vector<String>>();
            PortListFactory.getCategoryWithTag(this, "variants");
            PortListFactory.getCategoryWithTag(this, "categories");
            PortListFactory.getCategoryWithTag(this, "platforms");
        } else {
            cats = oldlist.cats;
            this_last_modified = oldlist.this_last_modified;
            this_last_size = oldlist.this_last_size;
        }
    }

    private static final PortList getBasePortList() {
        if (base == null)
            base = new PortList(null);
        return base;
    }

    public static final void invalidatePortLists() {
        base = null;
        filtered = null;
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
        filtered = null;
        PortListFactory.update(getBasePortList());
    }

    public static final void updateFilters(FilterChain chain) {
        filtered = new PortList(getBasePortList()); // empty port list
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

    boolean isNotUpdated(long lastModified, long length) {
        return (this_last_modified == lastModified && this_last_size == length);
    }

    void setUpdated(long lastModified, long length) {
        this_last_modified = lastModified;
        this_last_size = length;
    }
}
