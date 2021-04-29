/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Actions;

import com.MultiBrowse.MainWindow.MBMainWindow;
import com.MultiBrowse.MainWindow.New.CreateProfile;
import com.MultiBrowse.customcontrol.ComboItem;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.customcontrol.TreeComboBox;
import com.MultiBrowse.customcontrol.objProfiletype;
import com.MultiBrowse.customcontrol.objProject;
import com.MultiBrowse.customcontrol.objProxyman;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 *
 * @author title
 */
public class AllProfilesReport extends javax.swing.JDialog {

    /**
     * Creates new form AllProfilesReport
     */
    public AllProfilesReport(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initSettings();
        
        initTable();
        initProfiles();
        initProxies();
        initProfileTypes();
        initProjects();
        
        setIconImage();
    }
    
    private void setIconImage()
    {
        ImageIcon img = new ImageIcon(getClass().getResource("/MBIcon.png"));
        setIconImage(img.getImage());
    }
    
    LocalDBConnect dbControl = new LocalDBConnect();
    private void initProfiles(){
        ArrayList<objProject> lstProfiles = new ArrayList<objProject>();
        objProject objDef = new objProject();
        objDef.id = -1;
        objDef.Type = 1;
        objDef.strClass = "0000.";
        objDef.strName = "All Profiles";
        lstProfiles.add(objDef);
        
        lstProfiles.addAll(dbControl.getProfileInfo());
        int nProfilesCount = lstProfiles.size();
        
        for (int profileIndex = 0; profileIndex < nProfilesCount; profileIndex ++){
            jCmb_Profile.addItem(new ComboItem( lstProfiles.get(profileIndex).id, lstProfiles.get(profileIndex).strName ));
        }
        
    }
    
    private void initProxies(){
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
            jCmb_Proxy.addItem(new ComboItem( lstProxies.get(profileIndex).ID, lstProxies.get(profileIndex).Proxy_Ip ));
        }
    }
    
    private void initProfileTypes(){
        ArrayList<objProfiletype> lstProfiles = new ArrayList<objProfiletype>();
        objProfiletype objDef = new objProfiletype();
        objDef.ID = -1;
        objDef.Order = -1;
        objDef.ProfileType = "All Profile Types";
        lstProfiles.add(objDef);
        
        lstProfiles.addAll(dbControl.getProfileTypes(1));
        int nProfilesCount = lstProfiles.size();
        
        for (int profileIndex = 0; profileIndex < nProfilesCount; profileIndex ++){
            jCmb_ProfileType.addItem(new ComboItem( lstProfiles.get(profileIndex).ID, lstProfiles.get(profileIndex).ProfileType ));
        }
    }
    
    
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private javax.swing.JTree jTree;
    
    private void initProjects(){
        ArrayList<objProject> LocationInfo = dbControl.getProjectInfo();
        
        
        DefaultMutableTreeNode[] node = new DefaultMutableTreeNode[LocationInfo.size()];
        for (int i = 0; i < LocationInfo.size(); i++) {
            node[i] = new DefaultMutableTreeNode(LocationInfo.get(i).strName + " " + LocationInfo.get(i).strClass);
        }

        rootNode = node[0];   //Set the root node

        //Cycle through the table above and assign nodes to nodes
        for (int i = 0; i < LocationInfo.size(); i++) {
            for (int j = i + 1; j < LocationInfo.size(); j++) {
                if (isParent(LocationInfo.get(i).strClass, LocationInfo.get(j).strClass)){
//                    System.out.println(LocationInfo.get(i).strName + " is parent of " + LocationInfo.get(j).strName);
                    
                    node[i].add(node[j]);
                }
            }
        }
        
        DefaultMutableTreeNode hiddenRoot = new DefaultMutableTreeNode("Root");
        hiddenRoot.add(rootNode);
        

        jPan_Prj.setLayout(new BorderLayout());
        jPan_Prj.setPreferredSize(new Dimension(230, 38));
        jCmb_Project = new TreeComboBox(new DefaultTreeModel(hiddenRoot));
        jCmb_Project.addItemListener(new java.awt.event.ItemListener(){
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmb_ProjectItemStateChanged(evt);
            }
        });
        
        jPan_Prj.add(jCmb_Project, BorderLayout.CENTER);
    }
    
    public boolean isParent(String strParentClass, String strChildClass){
        String[] lstChildPart = strChildClass.split("\\.");
        String[] lstParentPart = strParentClass.split("\\.");
        if (lstParentPart.length + 1 != lstChildPart.length)
            return false;
        
        int nParentPartCnt = lstParentPart.length;
        for (int i = 0; i < nParentPartCnt; i ++){
            String strChildPart = lstChildPart[i];
            String strParentPart = lstParentPart[i];
            if (strChildPart.equals(strParentPart))
                continue;
            else
                return false;
        }
        
        return true;
    }
    
    JTable table;
    AllProfilesReportTableModel model;
    
    
    private void initTable(){
        LocalDBConnect dbControl = new LocalDBConnect();
        
        ArrayList<AllProfilesReportModel> profilesList;
        
        profilesList = dbControl.getProfiles(0, 0, 0, "");
        //create table model with data
        
        model = new AllProfilesReportTableModel(profilesList);
        
        
        table = new JTable(model){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex,
                    int columnIndex) {
                JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);  

                if(columnIndex == 5) {
                    component.setBackground(new Color(107, 235, 52));
                } 

                return component;
            }
        };
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                Object nSelProfileId = model.getValueAt(row, 6);
                if (col == 5) {
                    int nPrjPrfId = dbControl.getPrjPrfID((int)nSelProfileId);
                    CreateProfile profileForm = new CreateProfile(null, true, (int)nPrjPrfId);
                    
                    profileForm.addWindowListener(new WindowAdapter()
                    {
                        public void windowClosed(WindowEvent e)
                        {
                            searchTable();
                            
                            ArrayList lstProjects = new ArrayList<objProject>();
                            lstProjects = dbControl.getProject();
                            MBMainWindow.getTree().setData(lstProjects);

                            MBMainWindow.getTree().refresh();
                        }

                        public void windowClosing(WindowEvent e)
                        {
                        System.out.println("jdialog window closing");
                        }
                    });

//                        profileForm.pack();
                        profileForm.setVisible(true);
                    }
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        //hide id column
        final TableColumn idColumn = table.getColumnModel().getColumn(6);
        table.getColumnModel().removeColumn(idColumn);
        
        //set table row height
        table.setRowHeight(30);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(145);
        table.getColumnModel().getColumn(1).setPreferredWidth(145);
        table.getColumnModel().getColumn(2).setPreferredWidth(145);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        
        JScrollPane scrollPane = new JScrollPane(table);
        //add the table to the frame
        jPan_Table.setLayout(new GridLayout());
        jPan_Table.add(scrollPane);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
    }
    
    private void initSettings(){
        setTitle("All Profiles Report");
        
        setPreferredSize(new Dimension(866, 575));
        
        setResizable(false);
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 433, dim.height/2 - 287);
        
        
    }

    public AllProfilesReport() {
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

        jCmb_Profile = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jCmb_Proxy = new javax.swing.JComboBox();
        jCmb_ProfileType = new javax.swing.JComboBox();
        jChk_Select = new javax.swing.JCheckBox();
        jPan_Table = new javax.swing.JPanel();
        jBtn_Delete = new javax.swing.JButton();
        jPan_Prj = new javax.swing.JPanel();
        jBtn_Export = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jCmb_Profile.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jCmb_Profile.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmb_ProfileItemStateChanged(evt);
            }
        });

        jCmb_Proxy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jCmb_Proxy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmb_ProxyItemStateChanged(evt);
            }
        });

        jCmb_ProfileType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jCmb_ProfileType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmb_ProfileTypeItemStateChanged(evt);
            }
        });

        jChk_Select.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jChk_Select.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jChk_SelectItemStateChanged(evt);
            }
        });
        jChk_Select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_SelectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPan_TableLayout = new javax.swing.GroupLayout(jPan_Table);
        jPan_Table.setLayout(jPan_TableLayout);
        jPan_TableLayout.setHorizontalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPan_TableLayout.setVerticalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );

        jBtn_Delete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Delete.setText("Delete Selected Profile(s)");
        jBtn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_DeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPan_PrjLayout = new javax.swing.GroupLayout(jPan_Prj);
        jPan_Prj.setLayout(jPan_PrjLayout);
        jPan_PrjLayout.setHorizontalGroup(
            jPan_PrjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPan_PrjLayout.setVerticalGroup(
            jPan_PrjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jBtn_Export.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Export.setText("Export Selected to PDF");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jBtn_Export)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtn_Delete)
                .addGap(166, 166, 166))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPan_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jCmb_Profile, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jCmb_Proxy, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jCmb_ProfileType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPan_Prj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jChk_Select)
                        .addGap(111, 111, 111))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPan_Prj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCmb_Profile)
                    .addComponent(jCmb_ProfileType)
                    .addComponent(jCmb_Proxy)
                    .addComponent(jChk_Select, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPan_Table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtn_Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jBtn_Export, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jChk_SelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_SelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jChk_SelectActionPerformed

    
    private void jCmb_ProfileItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmb_ProfileItemStateChanged
        // TODO add your handling code here:
        nProfileID = ((ComboItem)(jCmb_Profile.getSelectedItem())).getId();
        searchTable();
        
    }//GEN-LAST:event_jCmb_ProfileItemStateChanged

    private int nProfileID;
    private int nProxyID;
    private int nProfileTypeID;
    private String strProjectClass = "";
    
    
    private void jCmb_ProxyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmb_ProxyItemStateChanged
        // TODO add your handling code here:
        nProxyID = ((ComboItem)(jCmb_Proxy.getSelectedItem())).getId();
        searchTable();
    }//GEN-LAST:event_jCmb_ProxyItemStateChanged

    private void jCmb_ProfileTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmb_ProfileTypeItemStateChanged
        // TODO add your handling code here:
        nProfileTypeID = ((ComboItem)(jCmb_ProfileType.getSelectedItem())).getId();
        searchTable();
    }//GEN-LAST:event_jCmb_ProfileTypeItemStateChanged

    private void jBtn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_DeleteActionPerformed
        // TODO add your handling code here:
        
        if (!model.isSelected())
            return;
        
        ImageIcon markIcon = new ImageIcon(getClass().getResource("/question_36.png"));
        int nResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this profile(s)?", "Delete Profiles", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, markIcon);
        if (nResult != JOptionPane.YES_OPTION)
            return;
        
        ArrayList<Integer> lstDeleted = model.removeRow();
        
        for (int i = 0; i < lstDeleted.size(); i ++){
            dbControl.DeleteProfile(lstDeleted.get(i));
        }
        
        
        ArrayList lstProjects = new ArrayList<objProject>();
        lstProjects = dbControl.getProject();
        
        //refresh Tree data
        MBMainWindow.getTree().setData(lstProjects);
        MBMainWindow.getTree().refresh();
                  
    }//GEN-LAST:event_jBtn_DeleteActionPerformed

    private void jChk_SelectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jChk_SelectItemStateChanged
        // TODO add your handling code here:
        boolean selected = jChk_Select.isSelected();
        ArrayList<AllProfilesReportModel> lstTableData = model.getTableData();
        int nRowCount = lstTableData.size();

        for (int i = 0; i < nRowCount; i ++)
            model.setValueAt(selected, i, 4); 

       model.fireTableDataChanged();
    }//GEN-LAST:event_jChk_SelectItemStateChanged

    private void jCmb_ProjectItemStateChanged(java.awt.event.ItemEvent evt){
        strProjectClass = jCmb_Project.getModel().getSelectedItem().toString().split(" ")[1];
        searchTable();
    }
    
    private void searchTable(){
        ArrayList<AllProfilesReportModel> searchData = dbControl.getProfiles(nProfileID, nProxyID, nProfileTypeID, strProjectClass);
        model = new AllProfilesReportTableModel(searchData);
        
        table.setModel(model);
        final TableColumn idColumn = table.getColumnModel().getColumn(6);
        table.getColumnModel().removeColumn(idColumn);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(145);
        table.getColumnModel().getColumn(1).setPreferredWidth(145);
        table.getColumnModel().getColumn(2).setPreferredWidth(145);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
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
            java.util.logging.Logger.getLogger(AllProfilesReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllProfilesReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllProfilesReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllProfilesReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AllProfilesReport dialog = new AllProfilesReport(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jBtn_Delete;
    private javax.swing.JButton jBtn_Export;
    private javax.swing.JCheckBox jChk_Select;
    private javax.swing.JComboBox jCmb_Profile;
    private javax.swing.JComboBox jCmb_ProfileType;
    private javax.swing.JComboBox jCmb_Proxy;
    private javax.swing.JPanel jPan_Prj;
    private javax.swing.JPanel jPan_Table;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
    private TreeComboBox jCmb_Project;
}
