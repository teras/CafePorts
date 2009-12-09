/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter;

/**
 *
 * @author teras
 */
public abstract class Operation implements Nameable {

    private final String name;

    public Operation(String name) {
        if (name == null)
            throw new NullPointerException("Operation name could not be null");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Operation clone() {
        try {
            return getClass().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public abstract UserData newUserData();

    public abstract boolean isValid(String portdata, String userdata);
}
