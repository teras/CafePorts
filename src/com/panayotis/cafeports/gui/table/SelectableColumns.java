/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.cafeports.gui.table;

import com.panayotis.utilities.Closure;
import java.awt.event.MouseEvent;

/**
 *
 * @author teras
 */
public interface SelectableColumns {

    public void selectVisibleColumns(MouseEvent event, Closure call_me_back);
}
