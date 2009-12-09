/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPortInfo.java
 *
 * Created on 8 Δεκ 2009, 3:18:13 πμ
 */
package com.panayotis.cafeports.gui.portinfo;

import com.panayotis.cafeports.db.PortInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JPortInfo extends javax.swing.JFrame {

    private final static int BORDEREDGE = 8;
    private final static int TEXTGAP = 4;
    private final static Color transparent = new Color(0, 0, 0, 5);
    private final static Color background = new Color(30, 30, 50, 240);
    private final static Color foreground = Color.white;
    /* */
    private final Window frame;
    private final JClearPanel viewport;
    private final JRoundEdge name;
    private final JClearLabel version;
    private final JClearText description;
    private final JClearText long_description;
    private final JClearButton url;
    private final JClearEmail email;
    /* */
    private boolean first_run = true;

    /** Creates new form JPortInfo */
    public JPortInfo(Window frame) {
        name = new JRoundEdge(16, 16);
        this.frame = frame;
        url = new JClearButton();
        email = new JClearEmail();

        JRoundEdge lower = new JRoundEdge(8, 0);

        getRootPane().putClientProperty("Window.style", "small");
        setBackground(transparent);
        setUndecorated(true);

        viewport = new JClearPanel();
        viewport.setLayout(new BoxLayout(viewport, BoxLayout.Y_AXIS));
        viewport.setBackground(background);

        lower.setBackground(background);
        lower.setUpside(true);
        name.setBackground(background);
        name.setForeground(foreground);

        viewport.add(name);
        version = initLabel("Version", 3);
        description = initTextField("", 1, 40, false, false);
        long_description = initTextField("", 4, 80, true, true);

        url.setBackground(background);
        url.setText("Project Homepage");
        email.setBackground(background);
        email.setText("E-mail maintainer");

        viewport.add(url);
        viewport.add(email);
        viewport.add(lower);

        setLayout(new BorderLayout());
        add(viewport, BorderLayout.NORTH);
        pack();
        setResizable(false);
        updateInfo(null);
    }

    public void updateInfo(final PortInfo info) {
        if (info == null) {
            name.setText("---");
            version.setText("---");
            description.setText("Not any package selected");
            long_description.setText("Please select a package to display information");
            url.setVisible(false);
            email.setVisible(false);
        } else {
            name.setText(info.getData("name"));
            version.setText(info.getData("version"));
            description.setText(info.getData("description"));
            long_description.setText(info.getData("long_description"));
            url.setURL(info.getData("homepage"));
            email.setURL(info.getData("maintainers"));
        }
        pack();
        try {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    pack();
                }
            });
        } catch (Exception ex) {
        }
    }

    public void setVisible(boolean status) {
        if (first_run)
            setLocation(frame.getX() + frame.getWidth() + 4, frame.getY());
        first_run = false;
        super.setVisible(status);
        if (!status)
            dispose();
    }

    private JClearLabel initLabel(String name, int border) {
        JClearPanel area = new JClearPanel();
        JClearLabel nameL = new JClearLabel(name);
        JClearLabel valueL = new JClearLabel();

        nameL.setForeground(foreground);
        nameL.setBackground(background);
        valueL.setForeground(foreground);
        valueL.setBackground(background);
        valueL.setFont(valueL.getFont().deriveFont(valueL.getFont().getStyle() | java.awt.Font.BOLD));

        if (name.equals(""))
            area.setLayout(new BorderLayout());
        else
            area.setLayout(new BorderLayout(TEXTGAP, 0));
        area.setBorder(new EmptyBorder(border, BORDEREDGE, border, BORDEREDGE));
        area.setBackground(background);
        area.setOpaque(false);

        area.add(nameL, BorderLayout.WEST);
        area.add(valueL, BorderLayout.CENTER);

        viewport.add(area);
        return valueL;
    }

    private JClearText initTextField(String name, int border, int height, boolean isSmaller, boolean isItalics) {
        JClearPanel area = new JClearPanel();
        JClearLabel nameL = new JClearLabel(name);
        JClearText valueT = new JClearText(height);

        nameL.setForeground(foreground);
        nameL.setBackground(background);
        nameL.setFont(nameL.getFont().deriveFont(nameL.getFont().getSize() - 1f));

        area.setLayout(new BorderLayout(TEXTGAP, 0));
        area.setBorder(new EmptyBorder(border, BORDEREDGE, border, BORDEREDGE));
        area.setBackground(background);
        area.setOpaque(false);

        valueT.setDisabledTextColor(foreground);
        if (isSmaller)
            valueT.setFont(valueT.getFont().deriveFont(valueT.getFont().getSize() * 0.9f));
        if (isItalics)
            valueT.setFont(valueT.getFont().deriveFont(valueT.getFont().getStyle() | Font.ITALIC));

        area.add(nameL, BorderLayout.NORTH);
        area.add(valueT, BorderLayout.CENTER);

        viewport.add(area);
        return valueT;
    }
}
