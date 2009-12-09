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
public class NotBelongs extends Operation {

    public NotBelongs() {
        super("is not");
    }

    public boolean isValid(String portdata, String userdata) {
        if (portdata == null)
            return false;
        portdata = " " + portdata + " ";
        return portdata.indexOf(userdata) < 0;
    }

    public UserData newUserData() {
        return new UserList();
    }
}
