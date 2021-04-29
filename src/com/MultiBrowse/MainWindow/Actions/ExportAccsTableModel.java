/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Actions;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author title
 */
public class ExportAccsTableModel extends AbstractTableModel {
    private final List<ExportAccsModel> ProfileInfList;
     
    private final String[] columnNames = new String[] {
            "ProfileType", "Project Name", "Profile Name", "First Name", "Surname", "Email", "Password", "Phone", "Rec Email", "Proxy", "", "ID"
    };
    
    final Class[] columnClass = new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Boolean.class, Integer.class};
 
    public ExportAccsTableModel(ArrayList<ExportAccsModel> ProfileInfList)
    {
        this.ProfileInfList = ProfileInfList;
    }
    
    public List<ExportAccsModel> getTableData(){
        return ProfileInfList;
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
        return ProfileInfList.size();
    }
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ExportAccsModel row = ProfileInfList.get(rowIndex);
        
        if(0 == columnIndex) {
            return row.getProfileType();
        }
        if(1 == columnIndex) {
            return row.getProjectName();
        }
        else if(2 == columnIndex) {
            return row.getProfileName();
        }
        else if(3 == columnIndex){
            return row.getFirstName();
        }
        else if(4 == columnIndex) {
            return row.getSurName();
        }
        else if(5 == columnIndex) {
            return row.getEmail();
        }
        else if(6 == columnIndex){
            return row.getPasssword();
        }
        else if(7 == columnIndex) {
            return row.getPhone();
        }
        else if(8 == columnIndex) {
            return row.getRec_Email();
        }
        else if(9 == columnIndex){
            return row.getProxy();
        }
        else if(10 == columnIndex) {
            return row.getSelect();
        }
        else if(11 == columnIndex) {
            return row.getId();
        }
        return null;
    }
    
    public boolean isCellEditable(int rowIndex, int colIndex){
        if (colIndex == 10)
            return true;
        else
            return false;
    }
    
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
       ExportAccsModel row = ProfileInfList.get(rowIndex);
      
       if(0 == columnIndex) {
           row.setProfileType((String) aValue);
       }
       if(1 == columnIndex) {
           row.setProjectName((String) aValue);
       }
       else if(2 == columnIndex) {
           row.setProfileName((String) aValue);
       }
       else if(3 == columnIndex){
           row.setFirstName((String) aValue);
       }
       else if(4 == columnIndex) {
           row.setSurName((String) aValue);
       }
       else if(5 == columnIndex) {
           row.setEmail((String) aValue);
       }
       else if(6 == columnIndex){
           row.setPassword((String) aValue);
       }
       else if(7 == columnIndex) {
           row.setPhone((String) aValue);
       }
       else if(8 == columnIndex) {
           row.setRec_Email((String) aValue);
       }
       else if(9 == columnIndex){
           row.setProxy((String) aValue);
       }
       else if(10 == columnIndex) {
           row.setSelect((boolean) aValue);
       }
       else if(11 == columnIndex) {
           row.setId((Integer) aValue);
       }
    }
}
