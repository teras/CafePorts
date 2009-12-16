/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui;

import com.panayotis.cafeports.db.PortList;
import com.panayotis.cafeports.filter.FilterChain;
import com.panayotis.cafeports.filter.FilterFactory.Filter;
import com.panayotis.cafeports.filter.UserData;
import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

/**
 *
 * @author teras
 */
public class JFilters extends JPanel {

    private final JPortWindow corewindow;
    private final FilterChain chain;

    public JFilters(JPortWindow coreWindow) {
        this.corewindow = coreWindow;
        chain = new FilterChain(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new MatteBorder(1, 0, 1, 0, Color.DARK_GRAY));

        KeyboardFocusManager fman = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        fman.addPropertyChangeListener(
                new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent e) {
                        if (("focusOwner".equals(e.getPropertyName())) &&
                                e.getNewValue() instanceof Component &&
                                UserData.isUserDataComponent((Component) e.getNewValue()))
                            chain.updateFocus((Component) e.getNewValue());
                    }
                });
        updateSelfVisuals();
    }

    public void updateVisuals() {
        updateSelfVisuals();
        requestListUpdate();
    }

    private void updateSelfVisuals() {
        removeAll();
        JFilterItem itm = null;
        int size = chain.getSize();
        for (Filter item : chain.getList()) {
            itm = new JFilterItem(item, this);
            itm.setRemovable(size > 1);
            add(itm);
        }
        chain.getSelectedFilter().getUserData().getComponent().requestFocus();
        Component c = this;
        while (!(c instanceof JFrame) && c != null)
            c = c.getParent();
        if (c != null)
            c.validate();
    }

    public void requestListUpdate() {
        PortList.updateFilters(chain);
        corewindow.updateList();
    }

    public void addFilter(Filter nextFilter, Filter filter) {
        chain.addFilter(nextFilter, filter);
        updateVisuals();
    }

    public void removeFilter(Filter filter) {
        chain.removeFilter(filter);
        updateVisuals();
    }

    public void replaceFilter(Filter newf, Filter filter) {
        chain.replaceFilter(newf, filter);
        updateVisuals();
    }
}
