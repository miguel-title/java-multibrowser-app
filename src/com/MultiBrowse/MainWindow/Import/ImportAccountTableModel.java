/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Import;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author title
 */
public class ImportAccountTableModel extends AbstractTableModel {
   private final ArrayList<ImportAccountModel> profileList;
     
    private final String[] columnNames = new String[] {
            "Profile Type", "Project Name", "Profile Name", "First Name", "SurName", "Email", "Password", "Phone", "Rec Email", "Proxy", "Select", "Id"
    };
    
    final Class[] columnClass = new Class[] {ProfileTypesComboItem.class, ProjectNameComboItem.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, ProxyComboItem.class, Boolean.class, Integer.class};
 
    public ImportAccountTableModel(ArrayList<ImportAccountModel> profileList)
    {
        this.profileList = profileList;
    }
      
    
    public ArrayList<ImportAccountModel> getTableData(){
        return profileList; 
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
        if (profileList == null)
            return 0;
        return profileList.size();
    }
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ImportAccountModel row = profileList.get(rowIndex);
        
        if(0 == columnIndex) {
            return row.getProfileType();
        }
        else if(1 == columnIndex) {
            return row.getProjectName();
        }
        else if(2 == columnIndex){
            return row.getProfileName();
        }
        else if(3 == columnIndex) {
            return row.getFirstName();
        }
        else if(4 == columnIndex) {
            return row.getSurName();
        }
        else if(5 == columnIndex){
            return row.getEmail();
        }
        else if(6 == columnIndex) {
            return row.getPassword();
        }
        else if(7 == columnIndex) {
            return row.getPhone();
        }
        else if(8 == columnIndex) {
            return row.getRecEmail();
        }
        else if(9 == columnIndex) {
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
//        if (colIndex == 11)
            return true;
//        else
//            return false;
    }
    
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
       ImportAccountModel row = profileList.get(rowIndex);
      
       if(0 == columnIndex) {
           row.setProfileType((ProfileTypesComboItem) aValue);
       }
       else if(1 == columnIndex) {
           row.setProjectName((ProjectNameComboItem) aValue);
       }
       else if(2 == columnIndex){
           row.setProfileName((String) aValue);
       }
       else if(3 == columnIndex) {
           row.setFirstName((String) aValue);
       }
       else if(4 == columnIndex) {
           row.setSurName((String) aValue);
       }
       else if(5 == columnIndex) {
           row.setEmail((String) aValue);
       }
       else if(6 == columnIndex) {
           row.setPassword((String) aValue);
       }
       else if(7 == columnIndex) {
           row.setPhone((String) aValue);
       }
       else if(8 == columnIndex) {
           row.setRecEmail((String) aValue);
       }
       else if(9 == columnIndex) {
           row.setProxy((ProxyComboItem) aValue);
       }
       else if(10 == columnIndex) {
           row.setSelect((boolean) aValue);
       }
       else if(11 == columnIndex) {
           row.setId((Integer) aValue);
       }
    }

    public boolean isSelected(){
        ArrayList<ImportAccountModel> lstResult = new ArrayList<ImportAccountModel>();
        
        int nRowCount = profileList.size();
        for (int i = 0; i < nRowCount; i ++)
        {
            if ((boolean)getValueAt(i, 4) == true ){
                lstResult.add(profileList.get(i));
            }
        }        
        if (lstResult.size() > 0)
            return true;
        else
            return false;
    } 
    
    public ArrayList<Integer> getSelectedRowList(){
        ArrayList<Integer> lstSelRows = new ArrayList<>();
        
        int nRowCount = profileList.size();
        for (int i = 0; i < nRowCount; i ++)
        {
            if ((boolean)getValueAt(i, 10) == true ){
                lstSelRows.add(i);
            }
        }        
        return lstSelRows;
    }
}
