/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.sudo;

import com.panayotis.utilities.Closure;
import com.panayotis.utilities.Commander;

/**
 *
 * @author teras
 */
public class SudoManager {

    private static final String[] pipedsudo = {
        "sudo",
        "-S",
        "-p",
        "",
        System.getProperty("java.home") + "/bin/java",
        "-cp",
        System.getProperty("java.class.path"),
        "com.panayotis.cafeports.sudo.PipedLauncher"
    };
    private static JSudo inst;
    private static boolean sudo_is_ok;

    public static void execute(String[] command, Closure out, Closure err, Closure begin) {
        String pass = SudoManager.getPassword();
        if (pass == null)
            return;
        String[] exec = new String[command.length + pipedsudo.length];
        System.arraycopy(pipedsudo, 0, exec, 0, pipedsudo.length);
        System.arraycopy(command, 0, exec, pipedsudo.length, command.length);

        Commander com = new Commander(exec);
        com.setOutListener(out);
        com.setErrListener(err);

        begin.exec(com);
        com.exec();
        com.sendLine(pass);
        com.waitFor();
    }

    static boolean testSudo(String pass) {
        if (pass == null || pass.equals(""))
            return false;

        final String SIGNATURE = "_ALL_OK_";
        clearSudo();
        sudo_is_ok = false;
        String[] command = {"sudo", "-S", "echo", SIGNATURE};

        Commander sudo = new Commander(command);
        sudo.setOutListener(new Closure() {

            public void exec(Object line) {
                if (line.equals(SIGNATURE))
                    sudo_is_ok = true;
            }
        });
        sudo.exec();
        sudo.sendLine(pass);
        sudo.terminateInput();
        sudo.waitFor();
        clearSudo();
        return sudo_is_ok;
    }

    private static void clearSudo() {
        Commander clear = new Commander(new String[]{"sudo", "-k"});
        clear.exec();
        clear.terminateInput();
        clear.waitFor();
    }

    private static String getPassword() {
        if (inst == null)
            inst = new JSudo();

        String pass = inst.getUserPass();
        if (testSudo(pass))
            return pass;

        inst.setVisible(true);
        return inst.getUserPass();
    }
}
