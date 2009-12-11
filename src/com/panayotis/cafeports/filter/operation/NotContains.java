/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.operation;

import com.panayotis.cafeports.filter.Operation;
import com.panayotis.cafeports.filter.UserData;
import com.panayotis.cafeports.filter.userdata.UserString;

/**
 *
 * @author teras
 */
public class NotContains extends Operation {

    public NotContains() {
        super("does not contain");
    }

    public boolean isValid(String portdata, String userdata) {
        if (portdata == null || userdata == null)
            return false;
        return portdata.indexOf(userdata) < 0;
    }

    public UserData newUserData() {
        return new UserString();
    }
}
