/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.utilities.MutableArray;
import com.panayotis.utilities.ImmutableList;

/**
 *
 * @author teras
 */
public abstract class PortData implements Nameable {

    private final String name;
    private final String tag;
    private final MutableArray<Operation> ops;

    public PortData(String name, String tag) {
        if (name == null)
            throw new NullPointerException("Source name could not be null");
        this.name = name;
        this.tag = tag;
        ops = new MutableArray<Operation>();
    }

    public PortData clone() {
        PortData s = null;
        try {
            s = getClass().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

    public String getName() {
        return name;
    }

    protected void addOperation(Operation op) {
        ops.add(op);
    }

    public ImmutableList<Operation> getOperations() {
        return ops;
    }

    public String getData(PortInfo p) {
        return p.getData(tag).toLowerCase();
    }

    public String getTag() {
        return tag;
    }
}
