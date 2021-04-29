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
public class ProjectNameCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener  {
    private ProjectNameComboItem ProjectName;
    private List<ProjectNameComboItem> listProjectNames;
     
    public ProjectNameCellEditor(List<ProjectNameComboItem> listProfileTypes) {
        this.listProjectNames = listProfileTypes;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.ProjectName;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof ProjectNameComboItem) {
            this.ProjectName = (ProjectNameComboItem) value;
        }
         
        JComboBox<ProjectNameComboItem> comboProjectName = new JComboBox<ProjectNameComboItem>();
         
        int nIndex = 0;
        int nCurIndex = -1;
        for (ProjectNameComboItem aProjectName : listProjectNames) {
            comboProjectName.addItem(aProjectName);
            
            if (aProjectName.getId() == this.ProjectName.getId())
                nCurIndex = nIndex;
            
            nIndex ++;
        }
         
//        comboProfileTypes.setSelectedItem(ProfileType);
        comboProjectName.setSelectedIndex(nCurIndex);
        comboProjectName.addActionListener(this);
         
        if (isSelected) {
            comboProjectName.setBackground(Color.white);
        } else {
            comboProjectName.setBackground(Color.white);
        }
         
        return comboProjectName;
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox<ProjectNameComboItem> comboProfileTypes = (JComboBox<ProjectNameComboItem>) event.getSource();
        this.ProjectName = (ProjectNameComboItem) comboProfileTypes.getSelectedItem();
    }
    
}
