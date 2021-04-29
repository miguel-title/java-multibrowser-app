/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Settings;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author title
 */
public class ProfileTypesTableModel extends AbstractTableModel {
    private final List<ProfileTypesModel> typeList;
     
    private final String[] columnNames = new String[] {
            "Select", "TypeName", "id"
    };
    
    final Class[] columnClass = new Class[] {Boolean.class, String.class, Integer.class};
 
    public ProfileTypesTableModel(List<ProfileTypesModel> typeList)
    {
        this.typeList = typeList;
    }
    
    public List<ProfileTypesModel> getTableData(){
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
        ProfileTypesModel row = typeList.get(rowIndex);
        
        if(0 == columnIndex) {
            return row.getSelect();
        }
        else if(1 == columnIndex) {
            return row.getTypeName();
        }
        else if(2 == columnIndex) {
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
    
    public void addRow(Object[] rowData){
        ProfileTypesModel oneData = new ProfileTypesModel((Integer)rowData[2], (String)rowData[1], false);
        
        typeList.add(oneData);
        
        int row = typeList.indexOf(rowData);
        for(int column = 0; column < rowData.length; column++) {
            fireTableCellUpdated(row, column);
        }
        fireTableRowsInserted(row, row);
    }
    
    public List<Integer> removeRow(){
        int nRowCount = typeList.size();
        List<Integer> lstDeletedRows = new ArrayList<Integer>();
        for (int rowIndex = nRowCount - 1; rowIndex >= 0; rowIndex --){
            ProfileTypesModel oneRowData = typeList.get(rowIndex);
            boolean isSelect = oneRowData.getSelect();
            int nId = oneRowData.getId();
            if (isSelect){
                if (nId != 0)
                    lstDeletedRows.add(nId);
                typeList.remove(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
            }
        }
        
        return lstDeletedRows;
    }
    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
       ProfileTypesModel row = typeList.get(rowIndex);
      
       if(1 == columnIndex) {
           row.setTypeName((String) aValue);
       }
       else if(2 == columnIndex) {
           row.setId((Integer) aValue);
       }
       else if(0 == columnIndex) {
           row.setSelect((Boolean) aValue);
       }
    }
    
     public boolean isSelected(){
        ArrayList<ProfileTypesModel> lstResult = new ArrayList<ProfileTypesModel>();
        
        int nRowCount = typeList.size();
        for (int i = 0; i < nRowCount; i ++)
        {
            if ((boolean)getValueAt(i, 0) == true ){
                lstResult.add(typeList.get(i));
            }
        }        
        if (lstResult.size() > 0)
            return true;
        else
            return false;
    }
}
