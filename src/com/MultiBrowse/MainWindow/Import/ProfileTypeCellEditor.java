/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Import;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author title
 */
public class ProfileTypeCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener  {
    private ProfileTypesComboItem ProfileType;
    private List<ProfileTypesComboItem> listProfileTypes;
     
    public ProfileTypeCellEditor(List<ProfileTypesComboItem> listProfileTypes) {
        this.listProfileTypes = listProfileTypes;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.ProfileType;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof ProfileTypesComboItem) {
            this.ProfileType = (ProfileTypesComboItem) value;
        }
         
        JComboBox<ProfileTypesComboItem> comboProfileTypes = new JComboBox<ProfileTypesComboItem>();
         
        int nIndex = 0;
        int nCurIndex = -1;
        for (ProfileTypesComboItem aProfileType : listProfileTypes) {
            comboProfileTypes.addItem(aProfileType);
            
            if (aProfileType.getId() == this.ProfileType.getId())
                nCurIndex = nIndex;
            
            nIndex ++;
        }
         
//        comboProfileTypes.setSelectedItem(ProfileType);
        comboProfileTypes.setSelectedIndex(nCurIndex);
        comboProfileTypes.addActionListener(this);
         
        if (isSelected) {
            comboProfileTypes.setBackground(Color.white);
        } else {
            comboProfileTypes.setBackground(Color.white);
        }
         
        return comboProfileTypes;
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox<ProfileTypesComboItem> comboProfileTypes = (JComboBox<ProfileTypesComboItem>) event.getSource();
        this.ProfileType = (ProfileTypesComboItem) comboProfileTypes.getSelectedItem();
    }
    
}
