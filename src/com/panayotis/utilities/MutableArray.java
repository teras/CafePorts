/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.utilities;

import java.util.ArrayList;

/**
 *
 * @author teras
 */
public class MutableArray<T> extends ArrayList<T> implements ImmutableList<T> {

    public T getItem(int i) {
        return get(i);
    }
}
