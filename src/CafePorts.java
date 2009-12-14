/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.panayotis.cafeports.config.CafePortsApp;
import com.panayotis.cafeports.db.PortListManager;
import com.panayotis.cafeports.gui.JPortWindow;

/**
 *
 * @author teras
 */
public class CafePorts {

    public static void main(String[] args) {
        new CafePortsApp();
        JPortWindow window = new JPortWindow();
        window.setVisible(true);
        PortListManager.init(window);
    }
}
