/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.portdata;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.filter.PortData;
import com.panayotis.cafeports.filter.operation.Contains;
import com.panayotis.cafeports.filter.operation.NotContains;
import com.panayotis.cafeports.filter.operation.NotStarts;
import com.panayotis.cafeports.filter.operation.Starts;

/**
 *
 * @author teras
 */
public class PortName extends PortData {

    {
        addOperation(new Contains());
        addOperation(new NotContains());
        addOperation(new Starts());
        addOperation(new NotStarts());
    }

    public PortName() {
        super("Name");
    }

    public String getData(PortInfo p) {
        return p.getData("name");
    }
}
