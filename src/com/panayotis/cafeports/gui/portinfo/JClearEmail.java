/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.util.StringTokenizer;

/**
 *
 * @author teras
 */
public class JClearEmail extends JClearTextureButton {

    public void setURL(String email) {
        if (email != null && email.equals("nomaintainer"))
            email = null;
        if (email != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("mailto:");
            StringTokenizer tk = new StringTokenizer(email);
            while (tk.hasMoreTokens()) {
                String person = tk.nextToken();
                int atsign = person.indexOf("@");
                if (atsign >= 0)
                    buffer.append(person);
                else {
                    int colon = person.indexOf(":");
                    if (colon < 0)
                        buffer.append(person).append("@macports.org");
                    else
                        buffer.append(person.substring(colon + 1)).append('@').append(person.substring(0, colon));
                }
                buffer.append(',');
            }
            email = buffer.substring(0, buffer.length() - 1);
        }
        super.setURL(email);
    }
}
