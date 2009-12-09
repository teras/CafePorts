/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.filter.userdata;

import com.panayotis.cafeports.filter.UserData;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author teras
 */
public class UserString extends UserData {

    public UserString() {
        super(new JTextField());
        JTextField tf = (JTextField) getComponent();
        tf.putClientProperty("JTextField.variant", "search");
        tf.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent arg0) {
                filterUpdated();
            }

            public void removeUpdate(DocumentEvent arg0) {
                filterUpdated();
            }

            public void changedUpdate(DocumentEvent arg0) {
                filterUpdated();
            }
        });
    }

    public String getData() {
        return ((JTextField) getComponent()).getText();
    }
}
