/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter;

import com.panayotis.cafeports.filter.FilterFactory.Filter;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JComponent;

/**
 *
 * @author teras
 */
public abstract class UserData {

    private static String USERDATA_SIGNATURE = "USERDATA_SIGNATURE";
    private JComponent component;
    private Filter parent;
    private String data;

    public UserData(JComponent comp) {
        this.component = comp;
        setSignature(comp);
        data = null;
    }

    public JComponent getComponent() {
        return component;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        if (data.equals(this.data))
            return;
        this.data = data;
        parent.requestUpdateUserData();
    }

    private static void setSignature(Component comp) {
        comp.setName(USERDATA_SIGNATURE);
        if (comp instanceof Container)
            for (Component item : ((Container) comp).getComponents())
                setSignature(item);
    }

    public abstract void visualsNeedUpdating();

    public boolean belongsHere(Component request) {
        return belongsHereImpl(component, request);
    }

    private static final boolean belongsHereImpl(Component self, Component request) {
        if (request == null)
            return false;
        if (self == request)
            return true;
        if (self instanceof Container)
            for (Component item : ((Container) self).getComponents())
                if (belongsHereImpl(item, request))
                    return true;
        return false;
    }

    public static boolean isUserDataComponent(Component comp) {
        if (comp == null)
            return false;
        return USERDATA_SIGNATURE.equals(comp.getName());
    }

    void setFilter(Filter parent) {
        this.parent = parent;
    }
}
