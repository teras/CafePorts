/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.operation;

import com.panayotis.cafeports.filter.Operation;
import com.panayotis.cafeports.filter.UserData;
import com.panayotis.cafeports.filter.userdata.UserEmpty;

/**
 *
 * @author teras
 */
public class NotInstalled extends Operation {

    public NotInstalled() {
        super("is not installed");
    }

    public boolean isValid(String portdata, String userdata) {
        return portdata == null;
    }

    public UserData newUserData() {
        return new UserEmpty();
    }
}
