/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.listeners;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author teras
 */
public class CombinedMouseListener implements MouseListener, MouseMotionListener {

    private Container parent;

    public CombinedMouseListener(Container parent, Component source) {
        this.parent = parent;
        source.addMouseListener(this);
        source.addMouseMotionListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        redispatchMouseEvent(e);
    }

    public void mousePressed(MouseEvent e) {
        redispatchMouseEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        redispatchMouseEvent(e);
    }

    public void mouseEntered(MouseEvent e) {
        redispatchMouseEvent(e);
    }

    public void mouseExited(MouseEvent e) {
        redispatchMouseEvent(e);
    }

    public void mouseDragged(MouseEvent e) {
        redispatchMouseEvent(e);
    }

    public void mouseMoved(MouseEvent e) {
        redispatchMouseEvent(e);
    }

    private void redispatchMouseEvent(MouseEvent e) {
        Point origpoint = e.getPoint();
        Component source = e.getComponent();

        Point containerPoint = SwingUtilities.convertPoint(source, origpoint, parent);
        containerPoint.y = 2;

        Component dest = SwingUtilities.getDeepestComponentAt(parent, containerPoint.x, containerPoint.y);
        if ((dest != null)) {
            Point componentPoint = SwingUtilities.convertPoint(parent, origpoint, dest);
            dest.dispatchEvent(new MouseEvent(dest, e.getID(), e.getWhen(), e.getModifiers(), componentPoint.x, componentPoint.y, e.getClickCount(), e.isPopupTrigger()));
        }
    }
}
