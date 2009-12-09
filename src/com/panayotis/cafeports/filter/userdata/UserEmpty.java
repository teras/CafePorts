/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.userdata;

import com.panayotis.cafeports.filter.UserData;
import javax.swing.JLabel;

/**
 *
 * @author teras
 */
public class UserEmpty extends UserData {

    public UserEmpty() {
        super(new JLabel());
    }

    public String getData() {
        return null;
    }
}
