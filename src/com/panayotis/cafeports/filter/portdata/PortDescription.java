/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.panayotis.cafeports.filter.portdata;

import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.filter.PortData;
import com.panayotis.cafeports.filter.operation.Contains;
import com.panayotis.cafeports.filter.operation.NotContains;

/**
 *
 * @author teras
 */
public class PortDescription extends PortData {
    {
        addOperation(new Contains());
        addOperation(new NotContains());
    }

    public PortDescription() {
        super("Description");
    }

    public String getData(PortInfo p) {
        return p.getData("description");
    }


}
