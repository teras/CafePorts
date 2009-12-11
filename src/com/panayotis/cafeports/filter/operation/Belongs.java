/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.operation;

import com.panayotis.cafeports.filter.Operation;
import com.panayotis.cafeports.filter.UserData;
import com.panayotis.cafeports.filter.userdata.UserList;

/**
 *
 * @author teras
 */
public class Belongs extends Operation {

    public Belongs() {
        super("is");
    }

    public boolean isValid(String portdata, String userdata) {
        if (portdata == null || userdata == null)
            return false;
        portdata = " " + portdata + " ";
        return portdata.indexOf(userdata) >= 0;
    }

    public UserData newUserData() {
        return new UserList();
    }
}
