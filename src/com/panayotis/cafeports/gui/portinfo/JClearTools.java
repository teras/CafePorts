/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.portinfo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.StringTokenizer;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JClearTools extends JClearPanel {

    private final JClearButton webB;
    private final JClearButton emailB;
    private final JClearButton trackerB;
    private final JClearButton clipboardB;
    private String web;
    private String email;
    private String tracker;
    private String info;

    /* Here we make sure that info is n ot small. Not the smartest place to put this code */
    private static final int EXTRA_RIGHT_SPACE = 100;

    public JClearTools() {
        webB = new JClearButton("/icons/buttons/web.png");
        emailB = new JClearButton("/icons/buttons/email.png");
        trackerB = new JClearButton("/icons/buttons/tracker.png");
        clipboardB = new JClearButton("/icons/buttons/clipboard.png");
        JClearPanel area = new JClearPanel();

        webB.setToolTipText("Go to project homepage");
        emailB.setToolTipText("e-Mail maintainers");
        trackerB.setToolTipText("Go to tracker");
        clipboardB.setToolTipText("Copy information to lcipboard");

        webB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                openURL(web);
            }
        });
        emailB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                openURL(email);
            }
        });
        trackerB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                openURL(tracker);
            }
        });
        clipboardB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (info != null)
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(info), null);
            }
        });

        area.setLayout(new GridLayout(1, 4, 4, 0));
        area.add(webB);
        area.add(emailB);
        area.add(trackerB);
        area.add(clipboardB);

        setLayout(new BorderLayout());
        add(area, BorderLayout.WEST);
        setBorder(new EmptyBorder(4, 8, 4, 8 + EXTRA_RIGHT_SPACE));
        setOpaque(true);
    }

    public void setWeb(String URL) {
        web = URL;
        webB.setEnabled(web != null);
    }

    public void setName(String name) {
        tracker = "http://trac.macports.org/search?q=" + name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private void openURL(String url) {
        if (url != null)
            try {
                Class fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
                openURL.invoke(null, new Object[]{url});
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    public void setEMail(String mail) {
        if (mail != null && mail.equals("nomaintainer"))
            mail = null;
        if (mail != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("mailto:");
            StringTokenizer tk = new StringTokenizer(mail);
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
            mail = buffer.substring(0, buffer.length() - 1);
        }
        this.email = mail;
        emailB.setEnabled(email != null);
    }
}
