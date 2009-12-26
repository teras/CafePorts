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
public class NotStarts extends Operation {

    public NotStarts() {
        super("does not start with");
    }

    public boolean isValid(String portdata, String userdata) {
        if (portdata == null || userdata == null)
            return false;
        if (userdata.equals(""))
            return true;
        return !portdata.startsWith(userdata);
    }

    public UserData newUserData() {
        return new UserString();
    }
}
