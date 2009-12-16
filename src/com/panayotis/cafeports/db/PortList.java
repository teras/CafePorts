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

    public static final void invalidatePortLists() {
        base = null;
        filtered = null;
        PortListFactory.killCache();
    }

    public static final PortList getEmptyPortList() {
        return empty;
    }

    public static final int countBasePortList() {
        return base.getSize();
    }

    public static final PortList getFilteredPortList() {
        return filtered;
    }

    public static void initBaseList(UpdateListener listener) {
        base = new PortList(listener);
        filtered = base;
    }

    public static final void updateFilters(FilterChain chain) {
        filtered = new PortList(base); // Will initialize port list, if not present
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

    private PortList(PortList oldlist) {
        cats.putAll(oldlist.cats);
    }

    private PortList(UpdateListener listener) {
        PortListFactory.update(this, listener);
    }

    public void add(PortInfo portInfo) {
        list.add(portInfo);
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
}
