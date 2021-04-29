/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Actions;

import com.MultiBrowse.MainWindow.Settings.ProxySettingModel;
import com.MultiBrowse.customcontrol.CheckBoxHeader;
import com.MultiBrowse.customcontrol.ComboItem;
import com.MultiBrowse.customcontrol.EditableHeaderRenderer;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.customcontrol.objProxyman;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author title
 */
public class ProxyMng extends javax.swing.JDialog {

    /**
     * Creates new form ProxyMng
     */
    public ProxyMng(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initSettings();
        
        initTable();
        initFilterCombo();
        initBulkCombo();
        initTableHeader();
        
        setIconImage();
    }
    
    private void setIconImage()
    {
        ImageIcon img = new ImageIcon(getClass().getResource("/MBIcon.png"));
        setIconImage(img.getImage());
    }
    
    LocalDBConnect dbControl = new LocalDBConnect();
    
    private void initFilterCombo(){
        ArrayList<objProxyman> lstProxies = new ArrayList<objProxyman>();
        objProxyman objDef = new objProxyman();
        objDef.ID = -1;
        objDef.Order = -1;
        objDef.Proxy_Ip = "All Proxy IPs";
        objDef.Proxy_Password = "";
        objDef.Proxy_Port = "";
        objDef.Proxy_Username = "";
        lstProxies.add(objDef);
        
        lstProxies.addAll(dbControl.getProxies(1));
        int nProfilesCount = lstProxies.size();
        
        for (int profileIndex = 0; profileIndex < nProfilesCount; profileIndex ++){
            jCmb_ProxyIPs.addItem(new ComboItem( lstProxies.get(profileIndex).ID, lstProxies.get(profileIndex).Proxy_Ip ));
        }
    }
    
    private void initBulkCombo(){
             
        ArrayList<objProxyman> lstProxies = new ArrayList<objProxyman>();   
        lstProxies.addAll(dbControl.getProxies(1));
        int nProfilesCount = lstProxies.size();
        
        for (int profileIndex = 0; profileIndex < nProfilesCount; profileIndex ++){
            jCmb_BulkproxyIps.addItem(new ComboItem( lstProxies.get(profileIndex).ID, lstProxies.get(profileIndex).Proxy_Ip ));
        }
    }
    
    JTable table;
    ProxyMngTableModel model;
    
    
    class MyItemListener implements ItemListener
    {
      public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (source instanceof AbstractButton == false) return;
        boolean checked = e.getStateChange() == ItemEvent.SELECTED;
        for(int x = 0, y = table.getRowCount(); x < y; x++)
        {
          table.setValueAt(new Boolean(checked),x,5);
        }
        model.fireTableDataChanged();
      }
    }

    private void initTableHeader(){
        table.getTableHeader().setReorderingAllowed(false);
        
        TableColumn col = table.getColumnModel().getColumn(5);
//        JPanel panel = new JPanel();
//        panel.setLayout(new FlowLayout());
//        JCheckBox checkbox = new JCheckBox("Select");
//        checkbox.addItemListener(new java.awt.event.ItemListener() {
//             public void itemStateChanged(java.awt.event.ItemEvent evt) {
//                 // TODO add your handling code here:
//                boolean selected = checkbox.isSelected();
//                List<ProxyMngModel> lstTableData = model.getTableData();
//                int nRowCount = lstTableData.size();
//
//                for (int i = 0; i < nRowCount; i ++)
//                    model.setValueAt(!selected, i, 5); 
//                
//                checkbox.setSelected(selected);
//                
//               model.fireTableDataChanged();
//             }
//         });
        CheckBoxHeader check = new CheckBoxHeader(new MyItemListener());
//        panel.add(check);
        col.setHeaderRenderer(check);
//        col.setHeaderRenderer(new EditableHeaderRenderer( new JCheckBox("CheckBox")));
      
    }
    
    private void initTable(){
        //actual data for the table in a 2d array
        
        ArrayList<ProxyMngModel> profileList;
        
        profileList = dbControl.getProfileList(0);
        //create table model with data
        
        model = new ProxyMngTableModel(profileList);
        
        
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        //hide id column
        final TableColumn idColumn = table.getColumnModel().getColumn(6);
        table.getColumnModel().removeColumn(idColumn);
        
        //set table row height
        table.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(table);
        //add the table to the frame
        jPan_Table.setLayout(new GridLayout());
        jPan_Table.add(scrollPane);
        
//        table.getTableHeader().setPreferredSize(new Dimension(0,40));
        
    }
    
    private void initSettings(){
        setTitle("Proxy Management");
        
//        setPreferredSize(new Dimension(904, 546));
        
        setResizable(false);
        
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 452, dim.height/2 - 273);
    }

    public ProxyMng() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPan_Table = new javax.swing.JPanel();
        jBtn_bulk = new javax.swing.JButton();
        jCmb_BulkproxyIps = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jCmb_ProxyIPs = new javax.swing.JComboBox();
        jBtn_Fill = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPan_TableLayout = new javax.swing.GroupLayout(jPan_Table);
        jPan_Table.setLayout(jPan_TableLayout);
        jPan_TableLayout.setHorizontalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPan_TableLayout.setVerticalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
        );

        jBtn_bulk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_bulk.setText("Bulk Apply Proxy to Selected Profiles");
        jBtn_bulk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_bulkActionPerformed(evt);
            }
        });

        jCmb_BulkproxyIps.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Filter by Proxy:");

        jCmb_ProxyIPs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jCmb_ProxyIPs.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmb_ProxyIPsItemStateChanged(evt);
            }
        });

        jBtn_Fill.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Fill.setText("Fill selected profiles with random Proxies");
        jBtn_Fill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_FillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPan_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCmb_ProxyIPs, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCmb_BulkproxyIps, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_bulk, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addComponent(jBtn_Fill, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCmb_ProxyIPs, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(jPan_Table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmb_BulkproxyIps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_bulk)
                    .addComponent(jBtn_Fill))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCmb_ProxyIPsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmb_ProxyIPsItemStateChanged
        // TODO add your handling code here:
        
        int nProxyID = ((ComboItem)(jCmb_ProxyIPs.getSelectedItem())).getId();
        ArrayList<ProxyMngModel> searchData = dbControl.getProfileList(nProxyID);
        model = new ProxyMngTableModel(searchData);
        
        table.setModel(model);
        final TableColumn idColumn = table.getColumnModel().getColumn(6);
        table.getColumnModel().removeColumn(idColumn);
        
        initTableHeader();
    }//GEN-LAST:event_jCmb_ProxyIPsItemStateChanged

    private void jBtn_bulkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_bulkActionPerformed
        // TODO add your handling code here:
        int nCurProxyId = ((ComboItem)jCmb_BulkproxyIps.getSelectedItem()).getId();
        
        ArrayList<ProxyMngModel> lstTableData = model.getSelectedTableData();
        int nSelCnt = lstTableData.size();
        for (int i = 0; i < nSelCnt; i ++){
            ProxyMngModel aData = lstTableData.get(i);
            int nProfileInfoId = dbControl.getProfileInfoID(aData.getId());
            dbControl.updateProxyInfo(nCurProxyId, nProfileInfoId);
        }
        
        refreshTable();
    }//GEN-LAST:event_jBtn_bulkActionPerformed

    private void jBtn_FillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_FillActionPerformed
        // TODO add your handling code here:
        ArrayList<ProxyMngModel> lstTableData = model.getSelectedTableData();
        int nSelCnt = lstTableData.size();
        
        ArrayList<objProxyman> lstProxies = new ArrayList<objProxyman>();   
        lstProxies.addAll(dbControl.getProxies(1));
        int nProfilesCount = lstProxies.size();
        
        for (int i = 0; i < nSelCnt; i ++){
            int nRndProfilesIndex = getRandomInt(nProfilesCount - 1);
            int nRndCurProxyId = lstProxies.get(nRndProfilesIndex).ID;
            
            ProxyMngModel aData = lstTableData.get(i);
            int nProfileInfoId = dbControl.getProfileInfoID(aData.getId());
            dbControl.updateProxyInfo(nRndCurProxyId, nProfileInfoId);
        }
        
        refreshTable();
    }//GEN-LAST:event_jBtn_FillActionPerformed

    public int getRandomInt(int nMax){
        int nResult = 0;
        
        Random rand = new Random();
        nResult = rand.nextInt(nMax);
        return nResult;
    }
    
    private void refreshTable(){
        ArrayList<ProxyMngModel> profileList;
        
        profileList = dbControl.getProfileList(0);
        //create table model with data
        
        model = new ProxyMngTableModel(profileList);
        
        table.setModel(model);
        
        //hide id column
        final TableColumn idColumn = table.getColumnModel().getColumn(6);
        table.getColumnModel().removeColumn(idColumn);
        
        initTableHeader();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProxyMng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProxyMng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProxyMng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProxyMng.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProxyMng dialog = new ProxyMng(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Fill;
    private javax.swing.JButton jBtn_bulk;
    private javax.swing.JComboBox jCmb_BulkproxyIps;
    private javax.swing.JComboBox jCmb_ProxyIPs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPan_Table;
    // End of variables declaration//GEN-END:variables
}
