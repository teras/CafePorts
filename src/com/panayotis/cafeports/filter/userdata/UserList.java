/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.userdata;

import com.panayotis.cafeports.filter.UpdateableUserData;
import com.panayotis.cafeports.filter.UserData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author teras
 */
public class UserList extends UserData implements UpdateableUserData {

    public UserList() {
        super(new JComboBox());
        JComboBox cmb = (JComboBox) getComponent();
        cmb.putClientProperty("JComboBox.isSquare", Boolean.TRUE);
        cmb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setData(((JComboBox) getComponent()).getSelectedItem().toString().toLowerCase());
            }
        });
    }

    public void updateData(Vector<String> data) {
        JComboBox cmb = (JComboBox) getComponent();
        cmb.setModel(new DefaultComboBoxModel(data));
    }
}
