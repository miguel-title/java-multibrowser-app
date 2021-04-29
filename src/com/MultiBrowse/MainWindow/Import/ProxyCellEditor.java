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
public class ProxyCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener  {
    private ProxyComboItem ProxyIp;
    private List<ProxyComboItem> listProxyIps;
     
    public ProxyCellEditor(List<ProxyComboItem> listProxyIps) {
        this.listProxyIps = listProxyIps;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.ProxyIp;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof ProxyComboItem) {
            this.ProxyIp = (ProxyComboItem) value;
        }
         
        JComboBox<ProxyComboItem> comboProjectName = new JComboBox<ProxyComboItem>();
         
        int nIndex = 0;
        int nCurIndex = -1;
        for (ProxyComboItem aProjectName : listProxyIps) {
            comboProjectName.addItem(aProjectName);
            
            if (aProjectName.getId() == this.ProxyIp.getId())
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
        JComboBox<ProxyComboItem> comboProfileTypes = (JComboBox<ProxyComboItem>) event.getSource();
        this.ProxyIp = (ProxyComboItem) comboProfileTypes.getSelectedItem();
    }
}
