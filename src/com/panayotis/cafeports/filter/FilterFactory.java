/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.db.PortList;
import com.panayotis.cafeports.filter.portdata.PortCategory;
import com.panayotis.cafeports.filter.portdata.PortDescription;
import com.panayotis.cafeports.filter.portdata.PortLongDescription;
import com.panayotis.cafeports.filter.portdata.PortMaintainer;
import com.panayotis.cafeports.filter.portdata.PortName;
import com.panayotis.cafeports.filter.portdata.PortPlatform;
import com.panayotis.cafeports.filter.portdata.PortStatus;
import com.panayotis.cafeports.filter.portdata.PortHomepage;
import com.panayotis.cafeports.filter.portdata.PortVariant;
import com.panayotis.cafeports.filter.portdata.PortVersion;
import com.panayotis.cafeports.gui.JFilters;
import com.panayotis.utilities.ImmutableList;
import com.panayotis.utilities.MutableArray;
import java.util.Vector;

/**
 *
 * @author teras
 */
public class FilterFactory {

    public static FilterFactory base = new FilterFactory();
    private MutableArray<PortData> sources;

    {
        sources = new MutableArray<PortData>();
        sources.add(new PortName());
        sources.add(new PortCategory());
        sources.add(new PortVersion());
        sources.add(new PortDescription());
        sources.add(new PortLongDescription());
        sources.add(new PortVariant());
        sources.add(new PortPlatform());
        sources.add(new PortHomepage());
        sources.add(new PortMaintainer());
        sources.add(new PortStatus());
    }

    public ImmutableList<PortData> getSources() {
        return sources;
    }

    public Filter getNextFilter(Filter old, JFilters container) {
        PortData p;
        Operation o;
        if (old == null) {
            p = sources.get(0);
            o = p.getOperations().getItem(0);
        } else {
            p = old.getPortData();
            o = old.getOperation();
        }
        Filter f = new Filter(p, o, container);
        if (old != null) {
            f.getUserData().setData(old.getUserData().getData());
            f.getUserData().visualsNeedUpdating();
        }
        return f;
    }

    public Filter getFilter(PortData port, Operation oper, JFilters container) {
        return new Filter(port, oper, container);
    }

    public class Filter {

        private final PortData port;
        private final Operation oper;
        private final UserData user;
        private final JFilters container;

        private Filter(PortData port, Operation oper, JFilters container) {
            this.port = port;
            this.oper = oper;
            this.user = oper.newUserData();
            this.user.setFilter(this);
            this.container = container;
            if (user instanceof UpdateableUserData) {
                String tag = port.getTag();
                Vector<String> data = PortList.getFilteredPortList().getCategory(tag);
                ((UpdateableUserData) user).updateData(data);
            }
        }

        public Operation getOperation() {
            return oper;
        }

        public PortData getPortData() {
            return port;
        }

        public UserData getUserData() {
            return user;
        }

        public boolean equals(Object o) {
            return o == this;
        }

        public boolean isValid(PortInfo p) {
            return oper.isValid(port.getData(p), user.getData());
        }

        void requestUpdateUserData() {
            container.requestListUpdate();
        }
    }
}
