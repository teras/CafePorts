/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.panayotis.cafeports.gui.JPortWindow;
import javax.swing.SwingUtilities;

/**
 *
 * @author teras
 */
public class CafePorts {

    public static void main(String[] args) {
        final JPortWindow w = new JPortWindow();
        w.setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                w.initTable();
            }
        });
    }
}
