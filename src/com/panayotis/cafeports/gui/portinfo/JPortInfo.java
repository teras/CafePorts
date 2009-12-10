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
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author teras
 */
public class JPortInfo extends javax.swing.JFrame implements MouseListener, MouseMotionListener {

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
    private int dx = 5;
    private int dy = 0;
    private Point oldpos = new Point();
    private Point newpos = new Point();
    private final Component self;

    /** Creates new form JPortInfo */
    public JPortInfo(Window parentframe) {
        name = new JRoundEdge(16, 16);
        frame = parentframe;
        url = new JClearButton();
        email = new JClearEmail();
        self = this;

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
        updateLocation();

        glassPane = getGlassPane();
        glassPane.setVisible(true);
        glassPane.addMouseListener(this);
        glassPane.addMouseMotionListener(this);
        contentPane = getContentPane();
    }
    private Component glassPane;
    private Container contentPane;

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
        updateLocation();
        super.setVisible(status);
        if (!status)
            dispose();
    }

    public void updateLocation() {
        setLocation(frame.getX() + frame.getWidth() + dx, frame.getY() + dy);
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

    public void mouseClicked(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    public void mousePressed(MouseEvent e) {
        Component c = e.getComponent();
        while (c.getParent() != null)
            c = c.getParent();
        oldpos.setLocation(e.getPoint());
        redispatchMouseEvent(e, false);
    }

    public void mouseReleased(MouseEvent e) {
        newpos.setLocation(e.getPoint());
        dx += newpos.x - oldpos.x;
        dy += newpos.y - oldpos.y;
        redispatchMouseEvent(e, true);
    }

    public void mouseEntered(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    public void mouseExited(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    public void mouseDragged(MouseEvent e) {
        newpos.setLocation(e.getPoint());
        dx += newpos.x - oldpos.x;
        dy += newpos.y - oldpos.y;
        redispatchMouseEvent(e, false);
    }

    public void mouseMoved(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    private void redispatchMouseEvent(MouseEvent e, boolean repaint) {
        Point glassPanePoint = e.getPoint();
        Container container = contentPane;
        Point containerPoint = SwingUtilities.convertPoint(glassPane, glassPanePoint, contentPane);

        if (containerPoint.y >= 0) {
            Component component = SwingUtilities.getDeepestComponentAt(container, containerPoint.x, containerPoint.y);
            if ((component != null)) {
                Point componentPoint = SwingUtilities.convertPoint(glassPane, glassPanePoint, component);
                component.dispatchEvent(new MouseEvent(component, e.getID(), e.getWhen(), e.getModifiers(), componentPoint.x, componentPoint.y, e.getClickCount(), e.isPopupTrigger()));
            }
        }
    }
}
