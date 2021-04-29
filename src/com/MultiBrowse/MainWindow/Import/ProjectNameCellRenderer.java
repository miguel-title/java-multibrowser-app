/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Import;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author title
 */
public class ProjectNameCellRenderer extends DefaultTableCellRenderer  {
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ProjectNameComboItem) {
            ProjectNameComboItem ProfileTypes = (ProjectNameComboItem) value;
            setText(ProfileTypes.getDescription());
        }
         
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(Color.white);
        }
         
        return this;
    }
}
