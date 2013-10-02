/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.map.GUIModel;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author dell
 */
public class ItemListCellRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        
        if (value instanceof JPanel) {
            Component component = (Component) value;
            component.setForeground(Color.white);
            component.setBackground(isSelected ? UIManager.getColor("Table.focusCellForeground") : Color.white);
            return component;
            
        } else {
            // TODO - I get one String here when the JList is first rendered; proper way to deal with this?
            //System.out.println("Got something besides a JPanel: " + value.getClass().getCanonicalName());
            return new JLabel("???");
        }
    }

}
