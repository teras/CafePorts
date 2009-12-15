/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.portdata;

import com.panayotis.cafeports.filter.PortData;
import com.panayotis.cafeports.filter.operation.IsInstalled;
import com.panayotis.cafeports.filter.operation.IsObsolete;
import com.panayotis.cafeports.filter.operation.NotInstalled;
import com.panayotis.cafeports.filter.operation.NotObsolete;

/**
 *
 * @author teras
 */
public class PortStatus extends PortData {

    {
        addOperation(new IsInstalled());
        addOperation(new NotInstalled());
        addOperation(new IsObsolete());
        addOperation(new NotObsolete());
    }

    public PortStatus() {
        super("Port", "installflags");
    }
}
