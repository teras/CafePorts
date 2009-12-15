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
public class IsObsolete extends Operation {

    public IsObsolete() {
        super("is obsolete");
    }

    public boolean isValid(String portdata, String userdata) {
        return portdata != null && portdata.indexOf("=") < 0;
    }

    public UserData newUserData() {
        return new UserEmpty();
    }
}
