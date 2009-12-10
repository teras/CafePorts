/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.panayotis.cafeports.config.CafePortsApp;
import com.panayotis.cafeports.config.Config;
import com.panayotis.cafeports.config.ConfigListener;
import com.panayotis.cafeports.gui.JPortWindow;

/**
 *
 * @author teras
 */
public class CafePorts {

    public static void main(String[] args) {
        new CafePortsApp();
        final JPortWindow w = new JPortWindow();
        w.setVisible(true);
        w.triggerDataIntialization();
    }
}
