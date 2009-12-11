/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.portdata;

import com.panayotis.cafeports.filter.PortData;
import com.panayotis.cafeports.filter.operation.IsInstalled;
import com.panayotis.cafeports.filter.operation.NotInstalled;

/**
 *
 * @author teras
 */
public class PortStatus extends PortData {

    {
        addOperation(new IsInstalled());
        addOperation(new NotInstalled());
    }

    public PortStatus() {
        super("Port", "isinstalled");
    }
}
