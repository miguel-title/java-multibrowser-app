/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Actions;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author title
 */
public class ProfileTReportTableModel extends AbstractTableModel {
    private final List<ProfileTReportModel> typeList;
     
    private final String[] columnNames = new String[] {
            "Select", "TypeName", "Profiles", "id"
    };
    
    final Class[] columnClass = new Class[] {Boolean.class, String.class, String.class, Integer.class};
 
    public ProfileTReportTableModel(List<ProfileTReportModel> typeList)
    {
        this.typeList = typeList;
    }
    
    public List<ProfileTReportModel> getTableData(){
        return typeList;
    }
     
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
    
 
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }
 
    public int getColumnCount()
    {
        return columnNames.length;
    }
 
    public int getRowCount()
    {
        return typeList.size();
    }
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ProfileTReportModel row = typeList.get(rowIndex);
        
        if(0 == columnIndex) {
            return row.getSelect();
        }
        else if(1 == columnIndex) {
            return row.getTypeName();
        }
        else if(2 == columnIndex){
            return row.getProfiles();
        }
        else if(3 == columnIndex) {
            return row.getId();
        }
        return null;
    }
    
    public boolean isCellEditable(int rowIndex, int colIndex){
        if (colIndex == 0)
            return true;
        else
            return false;
    }
    
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
       ProfileTReportModel row = typeList.get(rowIndex);
      
       if(0 == columnIndex) {
           row.setSelect((Boolean) aValue);
       }
       else if(1 == columnIndex) {
           row.setTypeName((String) aValue);
       }
       else if(2 == columnIndex){
           row.setProfiles((String) aValue);
       }
       else if(3 == columnIndex) {
           row.setId((Integer) aValue);
       }
    }
}
