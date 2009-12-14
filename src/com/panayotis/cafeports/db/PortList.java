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
    private final HashMap<String, Vector<String>> cats = new HashMap<String, Vector<String>>();
    private final MutableArray<PortInfo> list = new MutableArray<PortInfo>();
    private long this_last_modified;
    private long this_last_size;

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

    private PortList() {
    }

    private PortList(PortList oldlist, boolean updatable) {
        this_last_modified = -1;
        this_last_size = -1;
        if (updatable)
            PortListFactory.update(this, oldlist);
        else {
            cats.putAll(oldlist.cats);
            this_last_modified = oldlist.this_last_modified;
            this_last_size = oldlist.this_last_size;
        }
    }

    public void add(PortInfo portInfo) {
        list.add(portInfo);
    }

    public void copy(PortList oldlist) {
        list.clear();
        list.addAll(oldlist.list);
        cats.clear();
        cats.putAll(oldlist.cats);
    }

    public void sort() {
        Collections.sort(list);
        cats.clear();
        PortListFactory.getCategoryWithTag(this, "variants");
        PortListFactory.getCategoryWithTag(this, "categories");
        PortListFactory.getCategoryWithTag(this, "platforms");
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
//        System.out.println(this_last_modified + " - " + lastModified);
        return (this_last_modified == lastModified && this_last_size == length);
    }

    void setUpdated(long lastModified, long length) {
        this_last_modified = lastModified;
        this_last_size = length;
    }
}
