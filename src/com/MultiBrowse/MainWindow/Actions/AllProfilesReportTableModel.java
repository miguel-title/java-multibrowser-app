/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Actions;

import com.MultiBrowse.MainWindow.Settings.ProxySettingModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author title
 */
public class AllProfilesReportTableModel extends AbstractTableModel {
    private final ArrayList<AllProfilesReportModel> profileList;
     
    private final String[] columnNames = new String[] {
            "Profiles", "ProxyIps", "ProfileTypes", "Projects", "Select", "Edit Profiles", "Id"
    };
    
    final Class[] columnClass = new Class[] {String.class, String.class, String.class, String.class, Boolean.class, String.class, Integer.class};
 
    public AllProfilesReportTableModel(ArrayList<AllProfilesReportModel> profileList)
    {
        this.profileList = profileList;
    }
        
    public ArrayList<Integer> removeRow(){
        int nRowCount = profileList.size();
        ArrayList<Integer> lstDeletedRows = new ArrayList<Integer>();
        for (int rowIndex = nRowCount - 1; rowIndex >= 0; rowIndex --){
            AllProfilesReportModel oneRowData = profileList.get(rowIndex);
            boolean isSelect = oneRowData.getSelect();
            int nId = oneRowData.getId();
            if (isSelect){
                if (nId != 0)
                    lstDeletedRows.add(nId);
                profileList.remove(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
            }
        }
        
        return lstDeletedRows;
    }
    
    public ArrayList<AllProfilesReportModel> getTableData(){
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
        return profileList.size();
    }
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        AllProfilesReportModel row = profileList.get(rowIndex);
        
        if(0 == columnIndex) {
            return row.getProfileName();
        }
        else if(1 == columnIndex) {
            return row.getProxyIp();
        }
        else if(2 == columnIndex){
            return row.getProfileType();
        }
        else if(3 == columnIndex) {
            return row.getProjectName();
        }
        else if(4 == columnIndex) {
            return row.getSelect();
        }
        else if(5 == columnIndex){
            return "Edit Profile";
        }
        else if(6 == columnIndex) {
            return row.getId();
        }
        return null;
    }
    
    public boolean isCellEditable(int rowIndex, int colIndex){
        if (colIndex == 4)
            return true;
        else
            return false;
    }
    
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
       AllProfilesReportModel row = profileList.get(rowIndex);
      
       if(0 == columnIndex) {
           row.setProfileName((String) aValue);
       }
       else if(1 == columnIndex) {
           row.setProxyIp((String) aValue);
       }
       else if(2 == columnIndex){
           row.setProfileType((String) aValue);
       }
       else if(3 == columnIndex) {
           row.setProjectName((String) aValue);
       }
       else if(4 == columnIndex) {
           row.setSelect((boolean) aValue);
       }
       else if(6 == columnIndex) {
           row.setId((Integer) aValue);
       }
    }
    public boolean isSelected(){
        ArrayList<AllProfilesReportModel> lstResult = new ArrayList<AllProfilesReportModel>();
        
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
}
