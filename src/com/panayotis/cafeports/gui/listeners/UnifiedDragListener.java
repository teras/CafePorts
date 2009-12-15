/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.listeners;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author teras
 */
public class UnifiedDragListener implements MouseListener, MouseMotionListener {

    private final Point oldpos = new Point();
    private final Point newpos = new Point();
    private final Window frame;
    private Component lastcomp;

    public UnifiedDragListener(Window frame) {
        this.frame = frame;
    }

    public void register(Component component) {
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
    }

    public void mousePressed(MouseEvent e) {
        lastcomp = e.getComponent();
        while (lastcomp.getParent() != null)
            lastcomp = lastcomp.getParent();
        oldpos.setLocation(e.getPoint());
        SwingUtilities.convertPointToScreen(oldpos, lastcomp);
        oldpos.x -= frame.getX();
        oldpos.y -= frame.getY();
    }

    public void mouseDragged(MouseEvent e) {
        if (lastcomp==null)
            return;
        newpos.setLocation(e.getPoint());
        SwingUtilities.convertPointToScreen(newpos, lastcomp);
        frame.setLocation(newpos.x - oldpos.x, newpos.y - oldpos.y);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }
}
