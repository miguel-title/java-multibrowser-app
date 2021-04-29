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
public class ProxyMngTableModel extends AbstractTableModel {
    private final List<ProxyMngModel> ProfileList;
     
    private final String[] columnNames = new String[] {
            "Profile Name", "Proxy IP Address", "Port", "UserName", "Password", "", "Id"
    };
    
    final Class[] columnClass = new Class[] {String.class, String.class, String.class, String.class, String.class, Boolean.class, Integer.class};
 
    public ProxyMngTableModel(List<ProxyMngModel> ProfileList)
    {
        this.ProfileList = ProfileList;
    }
    
    public List<ProxyMngModel> getTableData(){
        return ProfileList;
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
        return ProfileList.size();
    }
 
    public ArrayList<ProxyMngModel> getSelectedTableData(){
        ArrayList<ProxyMngModel> lstResult = new ArrayList<ProxyMngModel>();
        
        int nRowCount = ProfileList.size();
        for (int i = 0; i < nRowCount; i ++)
        {
            if ((boolean)getValueAt(i, 5) == true ){
                lstResult.add(ProfileList.get(i));
            }
        }        
        return lstResult;
    }
    
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ProxyMngModel row = ProfileList.get(rowIndex);
        
        if(0 == columnIndex) {
            return row.getProfileName();
        }
        else if(1 == columnIndex) {
            return row.getProxyIPAddress();
        }
        else if(2 == columnIndex){
            return row.getPort();
        }
        else if(3 == columnIndex) {
            return row.getUserName();
        }
        else if(4 == columnIndex) {
            return row.getPassword();
        }
        else if(5 == columnIndex){
            return row.getSelect();
        }
        else if(6 == columnIndex) {
            return row.getId();
        }
        return null;
    }
    
    public boolean isCellEditable(int rowIndex, int colIndex){
        if (colIndex == 5)
            return true;
        else
            return false;
    }
    
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        ProxyMngModel row = ProfileList.get(rowIndex);
      
        if(0 == columnIndex) {
            row.setProfileName((String) aValue);
        }
        else if(1 == columnIndex){
            row.setProxyIPAddress((String) aValue);
        }
        else if(2 == columnIndex) {
            row.setPort((String) aValue);
        }
        else if(3 == columnIndex) {
            row.setUserName((String) aValue);
        }
        else if(4 == columnIndex){
            row.setPassword((String) aValue);
        }
        else if(5 == columnIndex) {
            row.setSelect((Boolean) aValue);
        }
        else if(6 == columnIndex) {
            row.setId((Integer) aValue);
        }
    }
}
