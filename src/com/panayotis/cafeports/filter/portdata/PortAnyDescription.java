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
public class PortAnyDescription extends PortData {

    {
        addOperation(new Contains());
        addOperation(new NotContains());
    }

    public PortAnyDescription() {
        super("Name or Description", null);
    }

    public String getData(PortInfo p) {
        StringBuffer buf = new StringBuffer();
        String dat;
        dat = p.getData("name");
        if (dat != null)
            buf.append(dat).append(' ');
        dat = p.getData("description");
        if (dat != null)
            buf.append(dat).append(' ');
        dat = p.getData("long_description");
        if (dat != null)
            buf.append(dat).append(' ');
        return buf.toString().toLowerCase();
    }
}
