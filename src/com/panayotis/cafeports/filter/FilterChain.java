/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter;

import com.panayotis.cafeports.filter.FilterFactory.Filter;
import com.panayotis.cafeports.gui.JFilters;
import com.panayotis.utilities.ImmutableList;
import com.panayotis.utilities.MutableArray;
import java.awt.Component;

/**
 *
 * @author teras
 */
public class FilterChain {

    private final MutableArray<Filter> items;
    private int selectedFilter;

    public FilterChain(JFilters container) {
        items = new MutableArray<Filter>();
        items.add(FilterFactory.base.getNextFilter(container));
        selectedFilter = 0;
    }

    public void addFilter(Filter f, Filter afterFilter) {
        if (afterFilter == null)
            selectedFilter = 0;
        else {
            selectedFilter = indexOf(afterFilter);
            if (selectedFilter < 0)
                selectedFilter = items.size();
            else
                selectedFilter++;
        }
        items.add(selectedFilter, f);
    }

    public void removeFilter(Filter f) {
        int location = indexOf(f);
        if (location < 0)
            return;
        items.remove(location);
        if (selectedFilter > location)
            selectedFilter--;
        if (selectedFilter >= items.size())
            selectedFilter = items.size() - 1;
    }

    public void replaceFilter(Filter newf, Filter oldf) {
        int location = indexOf(oldf);
        if (location < 0)
            return;
        items.set(location, newf);
        selectedFilter = location;
    }

    public ImmutableList<Filter> getList() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

    public Filter getSelectedFilter() {
        return items.get(selectedFilter);
    }

    private int indexOf(Filter f) {
        int location = items.indexOf(f);
        if (location < 0)
            System.out.println("Filter not found");
        return location;
    }

    public void updateFocus(Component component) {
        Filter f;
        for (int i = 0; i < items.size(); i++) {
            f = items.get(i);
            if (f.getUserData().belongsHere(component)) {
                selectedFilter = i;
                return;
            }
        }
    }
}
