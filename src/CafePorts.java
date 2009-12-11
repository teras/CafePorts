/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.panayotis.cafeports.config.CafePortsApp;
import com.panayotis.cafeports.config.Config;
import com.panayotis.cafeports.config.ConfigListener;
import com.panayotis.cafeports.config.JConfiguration;
import com.panayotis.cafeports.db.PortList;
import com.panayotis.cafeports.db.PortListException;
import com.panayotis.cafeports.gui.JPortWindow;
import javax.swing.JOptionPane;

/**
 *
 * @author teras
 */
public class CafePorts {

    public static final JPortWindow window = new JPortWindow();

    public static void main(String[] args) {
        new CafePortsApp();
        window.setVisible(true);
        Config.base.addListener(new ConfigListener() {

            public void configIsUpdated() {
                JConfiguration.dialog.setVisible(false);
                loadData();
            }
        });
        loadData();
    }

    public synchronized static void loadData() {
        new Thread() {

            public void run() {
                if (!Config.base.isPrefixValid()) {
                    window.setStatus(JPortWindow.Status.ERROR);
                    JConfiguration.dialog.fireDisplay();
                    return;
                } else
                    window.setStatus(JPortWindow.Status.LOADING);

                try {
                    PortList.getFilteredPortList();
                } catch (PortListException ex) {
                    Config.base.setCurrentPrefixInvalid();
                    window.setStatus(JPortWindow.Status.ERROR);
                    JOptionPane.showMessageDialog(null, "Unable to initialize Macports\n" + ex.getMessage());
                    JConfiguration.dialog.fireDisplay();
                    return;
                }

                JConfiguration.dialog.setVisible(false);
                window.setStatus(JPortWindow.Status.OK);
            }
        }.start();
    }
}
