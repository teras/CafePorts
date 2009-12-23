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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final static Color backgroundC = new Color(30, 30, 50, 240);
    private final static Color foregroundC = Color.white;
    private final static Color titleC = new Color(75, 75, 115, 240);
    /* */
    private final Window frame;
    private final JClearPanel viewport;
    private final JClearTitle title;
    private final JClearLabel version;
    private final JClearText description;
    private final JClearText long_description;
    private final JClearTools tools;
    /* */
    private int dx = 5;
    private int dy = 0;
    private Point oldpos = new Point();
    private Point newpos = new Point();

    /** Creates new form JPortInfo */
    public JPortInfo(Window parentframe) {
        title = new JClearTitle();
        frame = parentframe;
        tools = new JClearTools();
        JClearBottom bottom = new JClearBottom(8);

        getRootPane().putClientProperty("Window.style", "small");
        setBackground(transparent);
        setUndecorated(true);

        viewport = new JClearPanel();
        viewport.setLayout(new BoxLayout(viewport, BoxLayout.Y_AXIS));
        viewport.setBackground(backgroundC);

        title.setBackground(titleC);
        title.setForeground(foregroundC);
        title.addCloseListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        bottom.setBackground(backgroundC);
        tools.setBackground(backgroundC);

        viewport.add(title);
        version = initLabel("Version", 3);
        description = initTextField("", 1, 40, false, false);
        long_description = initTextField("", 4, 80, true, true);
        viewport.add(tools);
        viewport.add(bottom);

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
            title.setText("---");
            version.setText("---");
            description.setText("Not any package selected");
            long_description.setText("Please select a package to display information");
            tools.setVisible(false);
        } else {
            String nameT = info.getData("name");
            String versionT = info.getData("version");
            String descrT = info.getData("description");
            String ldescrT = info.getData("long_description");
            String hpT = info.getData("homepage");
            String emailT = info.getData("maintainers");
            StringBuffer buf = new StringBuffer();
            buf.append(nameT).append(' ').append(versionT).append('\n').append(descrT).append('\n').append(ldescrT).append('\n').append(hpT).append('\n').append(emailT);

            title.setText(nameT);
            version.setText(versionT);
            description.setText(descrT);
            long_description.setText(ldescrT);
            tools.setWeb(hpT);
            tools.setEMail(emailT);
            tools.setName(nameT);
            tools.setInfo(buf.toString());
            tools.setVisible(true);
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

        nameL.setForeground(foregroundC);
        nameL.setBackground(backgroundC);
        valueL.setForeground(foregroundC);
        valueL.setBackground(backgroundC);
        valueL.setFont(valueL.getFont().deriveFont(valueL.getFont().getStyle() | java.awt.Font.BOLD));

        if (name.equals(""))
            area.setLayout(new BorderLayout());
        else
            area.setLayout(new BorderLayout(TEXTGAP, 0));
        area.setBorder(new EmptyBorder(border, BORDEREDGE, border, BORDEREDGE));
        area.setBackground(backgroundC);
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

        nameL.setForeground(foregroundC);
        nameL.setBackground(backgroundC);
        nameL.setFont(nameL.getFont().deriveFont(nameL.getFont().getSize() - 1f));

        area.setLayout(new BorderLayout(TEXTGAP, 0));
        area.setBorder(new EmptyBorder(border, BORDEREDGE, border, BORDEREDGE));
        area.setBackground(backgroundC);
        area.setOpaque(false);

        valueT.setDisabledTextColor(foregroundC);
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
        try {
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
        } catch (Exception ex) {
        }
    }
}
