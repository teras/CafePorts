/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.sudo;

import com.panayotis.utilities.Closure;
import com.panayotis.utilities.Commander;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author teras
 */
public class PipedLauncher {

    private final static String KILLSIGNAL = "KILLSIGNAL";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Commander c = new Commander(args);

            /* Piping out/error stream to this process stream */
            Closure out = new Closure() {

                public void exec(Object data) {
                    System.out.println(data);
                    System.out.flush();
                }
            };
            Closure err = new Closure() {

                public void exec(Object data) {
                    System.err.println(data);
                    System.err.flush();
                }
            };
            Closure finish = new Closure() {

                public void exec(Object data) {
                    System.exit(0);
                }
            };
            c.setOutListener(out);
            c.setErrListener(err);
            c.setEndListener(finish);
            c.exec();

            /* listening to stdin for "KILL" signal */
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = in.readLine()) != null)
                if (line.equals(KILLSIGNAL))
                    c.kill();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public final static void sendKillSignal(Commander c) {
        if (c != null)
            c.sendLine(KILLSIGNAL);
    }
}
