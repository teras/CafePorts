/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.installer;

import com.panayotis.utilities.Closure;
import com.panayotis.utilities.Commander;
import com.panayotis.cafeports.config.Config;
import com.panayotis.cafeports.db.PortInfo;
import com.panayotis.cafeports.sudo.PipedLauncher;
import com.panayotis.cafeports.sudo.SudoManager;
import javax.swing.SwingUtilities;

/**
 *
 * @author teras
 */
public class PortProcess {

    private static Commander comm;
    private static JProcess jproc;

    public static void exec(String command, PortInfo[] list, final Closure call_parent) {
        /* Define closures */
        final Closure out = new Closure() {

            public void exec(Object line) {
                if (!line.toString().trim().isEmpty())
                    jproc.updateStatus(line.toString());
                System.out.println("Debug O: " + line);
            }
        };
        final Closure err = new Closure() {

            public void exec(Object line) {
                if (!line.toString().trim().isEmpty())
                    jproc.updateError(line.toString());
                System.out.println("Debug E: " + line);
            }
        };
        final Closure begin = new Closure() {

            public void exec(Object commander) {
                comm = (Commander) commander;
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jproc.updateStatus(null);
                        jproc.setVisible(true);
                    }
                });
            }
        };
        final Closure cancel = new Closure() {

            public void exec(Object line) {
                PipedLauncher.sendKillSignal(comm);
            }
        };

        /* initialize execution */
        if (list == null || list.length < 0 || command == null)
            return;
        final String[] exe = new String[2 + list.length];
        exe[0] = Config.base.getPortCmd();
        exe[1] = command;
        for (int i = 0; i < list.length; i++)
            exe[i + 2] = list[i].getData("name");

        jproc = new JProcess(cancel);
        Thread sudothread = new Thread() {

            public void run() {
                SudoManager.execute(exe, out, err, begin);
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {

                        public void run() {
                            call_parent.exec(null);
                            jproc.finishExec();
                        }
                    });
                } catch (Exception ex) {
                }
            }
        };
        sudothread.start();
    }
}
