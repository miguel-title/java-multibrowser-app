/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Settings;

/**
 *
 * @author title
 */
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProxySettingTableModel  extends AbstractTableModel {
    
    private final List<ProxySettingModel> ProxySettingList;
     
    private final String[] columnNames = new String[] {
            "Status", "Proxy IP Address", "Port", "UserName", "Password", "Select", "id"
    };
    
    final Class[] columnClass = new Class[] {String.class, String.class, Integer.class, String.class, String.class, Boolean.class, Integer.class};
 
    public ProxySettingTableModel(List<ProxySettingModel> ProxySettingList)
    {
        this.ProxySettingList = ProxySettingList;
    }
    
    public List<ProxySettingModel> getTableData(){
        return ProxySettingList;
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
        return ProxySettingList.size();
    }
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ProxySettingModel row = ProxySettingList.get(rowIndex);
        
        if(0 == columnIndex) {
            return row.getStatus();
        }
        else if(1 == columnIndex) {
            return row.getIPAddress();
        }
        else if(2 == columnIndex) {
            return row.getPort();
        }
        else if(3 == columnIndex) {
            return row.getUserName();
        }
        else if(4 == columnIndex) {
            return row.getPassword();
        }
        else if(5 == columnIndex) {
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
    
    public void addRow(Object[] rowData){
        ProxySettingModel oneData = new ProxySettingModel((String)rowData[0], (String)rowData[1], (Integer)rowData[2], (String)rowData[3], (String)rowData[4], (boolean)rowData[5], (Integer)rowData[6]);
        
        ProxySettingList.add(oneData);
        
        int row = ProxySettingList.indexOf(rowData);
        for(int column = 0; column < rowData.length; column++) {
            fireTableCellUpdated(row, column);
        }
        fireTableRowsInserted(row, row);
    }
    
    public List<ProxySettingModel> getSelectedProxies(){
        List<ProxySettingModel> lstResult = new ArrayList<ProxySettingModel>();
        int nRowCount = ProxySettingList.size();
        for (int rowIndex = nRowCount - 1; rowIndex >= 0; rowIndex --){
            ProxySettingModel oneRowData = ProxySettingList.get(rowIndex);
            boolean isSelect = oneRowData.getSelect();
            oneRowData.setRowIndex(rowIndex);
            if (isSelect){
                lstResult.add(oneRowData);
            }
        }
        
        return lstResult;
    }
    
    public List<Integer> removeRow(){
        int nRowCount = ProxySettingList.size();
        List<Integer> lstDeletedRows = new ArrayList<Integer>();
        for (int rowIndex = nRowCount - 1; rowIndex >= 0; rowIndex --){
            ProxySettingModel oneRowData = ProxySettingList.get(rowIndex);
            boolean isSelect = oneRowData.getSelect();
            int nId = oneRowData.getId();
            if (isSelect){
                if (nId != 0)
                    lstDeletedRows.add(nId);
                ProxySettingList.remove(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
            }
        }
        
        return lstDeletedRows;
    }
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
       ProxySettingModel row = ProxySettingList.get(rowIndex);
      
       if(0 == columnIndex) {
           row.setStatus((String) aValue);
       }
       else if(1 == columnIndex) {
           row.setIPAddress((String) aValue);
       }
       else if(2 == columnIndex) {
           row.setPort((Integer) aValue);
       }
       else if(3 == columnIndex) {
           row.setUserName((String) aValue);
       }
       else if(4 == columnIndex) {
           row.setPassword((String) aValue);
       }
       else if(5 == columnIndex) {
           row.setSelect((boolean) aValue);
       }
       else if(6 == columnIndex) {
           row.setId((Integer) aValue);
       }
    }
    public boolean isSelected(){
        ArrayList<ProxySettingModel> lstResult = new ArrayList<ProxySettingModel>();
        
        int nRowCount = ProxySettingList.size();
        for (int i = 0; i < nRowCount; i ++)
        {
            if ((boolean)getValueAt(i, 5) == true ){
                lstResult.add(ProxySettingList.get(i));
            }
        }        
        if (lstResult.size() > 0)
            return true;
        else
            return false;
    }
}
