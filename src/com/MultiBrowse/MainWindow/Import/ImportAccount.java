/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Import;

import com.MultiBrowse.MainWindow.Actions.AllProfilesReportTableModel;
import com.MultiBrowse.MainWindow.MBMainWindow;
import com.MultiBrowse.MainWindow.Settings.ProxySettingModel;
import com.MultiBrowse.customcontrol.ComboItem;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.customcontrol.objProfile;
import com.MultiBrowse.customcontrol.objProfiletype;
import com.MultiBrowse.customcontrol.objProject;
import com.MultiBrowse.customcontrol.objProxyman;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
/**
 *
 * @author title
 */
public class ImportAccount extends javax.swing.JDialog {

    /**
     * Creates new form ImportAccount
     */
    public ImportAccount(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initSettings();
        
        initProfileTypes();
        initTable();
    }
    
    LocalDBConnect dbControl = new LocalDBConnect();
    
    JTable table;
    ImportAccountTableModel model;
    ArrayList<ImportAccountModel> m_profileList;
    
    public void setProfileList(ArrayList<ImportAccountModel> profileList){
        m_profileList = profileList;
        jPan_Table.removeAll();
        initTable();
    }
    
    private void initTable(){
        
        model = new ImportAccountTableModel(m_profileList);
        
        
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        
        //set profiletype combo
        ArrayList<ProfileTypesComboItem> listProfileTypes = new ArrayList<>();
        
        ArrayList<objProfiletype> lstProfileObj = dbControl.getProfileTypes(1);
        
        for (int i = 0; i < lstProfileObj.size(); i ++){
            listProfileTypes.add(new ProfileTypesComboItem(lstProfileObj.get(i).ID, lstProfileObj.get(i).ProfileType));
        }
        
        table.setDefaultRenderer(ProfileTypesComboItem.class, new ProfileTypeCellRenderer());
        table.setDefaultEditor(ProfileTypesComboItem.class, new ProfileTypeCellEditor(listProfileTypes));
        
        //set projectname combo
        
        ArrayList<ProjectNameComboItem> listProjectNames = new ArrayList<>();
        
        ArrayList<objProject> lstProjectObj = dbControl.getProject();
        
        for (int i = 0; i < lstProjectObj.size(); i ++){
            if (lstProjectObj.get(i).Type == 0)
                listProjectNames.add(new ProjectNameComboItem(lstProjectObj.get(i).id, lstProjectObj.get(i).strName));
        }
        
        table.setDefaultRenderer(ProjectNameComboItem.class, new ProjectNameCellRenderer());
        table.setDefaultEditor(ProjectNameComboItem.class, new ProjectNameCellEditor(listProjectNames));
        
        //set projectname combo
        
        ArrayList<ProxyComboItem> listProxyIps = new ArrayList<>();
        
        ArrayList<ProxySettingModel> lstProxies = dbControl.getProxySettings();
        
        for (int i = 0; i < lstProjectObj.size(); i ++){
                listProxyIps.add(new ProxyComboItem(lstProxies.get(i).getId(), lstProxies.get(i).getIPAddress()));
        }
        
        table.setDefaultRenderer(ProxyComboItem.class, new ProxyCellRenderer());
        table.setDefaultEditor(ProxyComboItem.class, new ProxyCellEditor(listProxyIps));
        
        
        //hide id column
        final TableColumn idColumn = table.getColumnModel().getColumn(11);
        table.getColumnModel().removeColumn(idColumn);
        
        //set table row height
        table.setRowHeight(30);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(145);
        table.getColumnModel().getColumn(1).setPreferredWidth(145);
        table.getColumnModel().getColumn(2).setPreferredWidth(145);
        
        table.getColumnModel().getColumn(9).setPreferredWidth(145);
        
        JScrollPane scrollPane = new JScrollPane(table);
        //add the table to the frame
        jPan_Table.setLayout(new GridLayout());
        jPan_Table.add(scrollPane);
    }   
    
    private void initProfileTypes(){
        ArrayList<objProfiletype> lstProfiles = new ArrayList<objProfiletype>();
        
        lstProfiles.addAll(dbControl.getProfileTypes(1));
        int nProfilesCount = lstProfiles.size();
        
        for (int profileIndex = 0; profileIndex < nProfilesCount; profileIndex ++){
            jCmb_ProfileType.addItem(new ComboItem( lstProfiles.get(profileIndex).ID, lstProfiles.get(profileIndex).ProfileType ));
        }
    }
    
    private void initSettings(){
        setTitle("Import Account");
        
        setPreferredSize(new Dimension(1200, 600));
        
        
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 600, dim.height/2 - 300);
    }
    
    public ImportAccount() {
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
        jBtn_ChangePrifileType = new javax.swing.JButton();
        jCmb_ProfileType = new javax.swing.JComboBox();
        jBtn_Apply = new javax.swing.JButton();
        jBtn_Save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPan_TableLayout = new javax.swing.GroupLayout(jPan_Table);
        jPan_Table.setLayout(jPan_TableLayout);
        jPan_TableLayout.setHorizontalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPan_TableLayout.setVerticalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 542, Short.MAX_VALUE)
        );

        jBtn_ChangePrifileType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_ChangePrifileType.setText("Change Profile type for Selected to");
        jBtn_ChangePrifileType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ChangePrifileTypeActionPerformed(evt);
            }
        });

        jCmb_ProfileType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jBtn_Apply.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Apply.setText("Apply random Proxies to Selected");
        jBtn_Apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ApplyActionPerformed(evt);
            }
        });

        jBtn_Save.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Save.setText("Save");
        jBtn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_SaveActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCmb_ProfileType, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtn_ChangePrifileType, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 369, Short.MAX_VALUE)
                        .addComponent(jBtn_Apply, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPan_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_ChangePrifileType, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCmb_ProfileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_Apply)
                    .addComponent(jBtn_Save))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_ChangePrifileTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ChangePrifileTypeActionPerformed
        // TODO add your handling code here:
        
        ArrayList<Integer> lstTableRows = model.getSelectedRowList();
        int nSelCnt = lstTableRows.size();
        
        int nProfileTypeId = ((ComboItem)(jCmb_ProfileType.getSelectedItem())).getId();
        String strProfileTypeName = ((ComboItem)(jCmb_ProfileType.getSelectedItem())).getDescription();
        
        for (int i = 0; i < nSelCnt; i ++){            
            table.setValueAt(new ProfileTypesComboItem(nProfileTypeId, strProfileTypeName), lstTableRows.get(i), 0);
        }
        
        model.fireTableDataChanged();
    }//GEN-LAST:event_jBtn_ChangePrifileTypeActionPerformed

    public int getRandomInt(int nMax){
        int nResult = 0;
        
        Random rand = new Random();
        nResult = rand.nextInt(nMax);
        return nResult;
    }
    
    private void jBtn_ApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ApplyActionPerformed
        // TODO add your handling code here:
        ArrayList<Integer> lstTableRows = model.getSelectedRowList();
        int nSelCnt = lstTableRows.size();
        
        ArrayList<objProxyman> lstProxies = new ArrayList<objProxyman>();   
        lstProxies.addAll(dbControl.getProxies(1));
        int nProfilesCount = lstProxies.size();
        
        for (int i = 0; i < nSelCnt; i ++){
            int nRndProfilesIndex = getRandomInt(nProfilesCount - 1);
            int nRndCurProxyId = lstProxies.get(nRndProfilesIndex).ID;
            String strRndCurProxyIp = lstProxies.get(nRndProfilesIndex).Proxy_Ip;
            
            table.setValueAt(new ProxyComboItem(nRndCurProxyId, strRndCurProxyIp), lstTableRows.get(i), 9);
        }
        
        model.fireTableDataChanged();
    }//GEN-LAST:event_jBtn_ApplyActionPerformed

    
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
    
     public String getNextClass(String strClass){
        String strNextClass = "";
        String strNext = "0000";
        
        String[] lstSplit = strClass.split("\\.");
        int nlen = lstSplit.length;
        String strLast = lstSplit[nlen - 1];
        
        int nLast = Integer.parseInt(strLast);
        int nNext = nLast + 1;
        
        strNext = Integer.toString(nNext);
        int nStrLen = strNext.length();
        
        String  item = "0";
        String repeatItem = new String(new char[4 - nStrLen]).replace("\0", item);
        strNext = repeatItem + strNext;
        
        lstSplit[nlen - 1] = strNext;
        
        
        for (String s : lstSplit)
        {
            strNextClass += s + ".";
        }
        
        return strNextClass;
    }
     
     
    private void jBtn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SaveActionPerformed
        // TODO add your handling code here:
        LocalDBConnect dbControl = new LocalDBConnect();
        
        ArrayList<ImportAccountModel> lstTableData = model.getTableData();
        int nRowCount = lstTableData.size();
        
        for (int dataIndex = 0; dataIndex <  nRowCount; dataIndex ++){
            
            String profilename = lstTableData.get(dataIndex).getProfileName();
            //for Profile_Info table
            String parentClass = "";
            parentClass = dbControl.getProject(((ProjectNameComboItem)(model.getValueAt(dataIndex, 1))).getId()).strClass;
            int proxyid = 0;
            proxyid = ((ProxyComboItem)(model.getValueAt(dataIndex, 9))).getId();

            int profiletypeid = 0;
            profiletypeid = ((ProfileTypesComboItem)(model.getValueAt(dataIndex, 0))).getId();

            String fname = lstTableData.get(dataIndex).getFirstName();
            String lname = lstTableData.get(dataIndex).getSurName();
            String fullname = fname + " " + lname;
            String email = lstTableData.get(dataIndex).getEmail();
            String password = lstTableData.get(dataIndex).getPassword();
            String phonenumber = lstTableData.get(dataIndex).getPassword();
            String recoveryemail = lstTableData.get(dataIndex).getRecEmail();
            String recoverymobile = "";


            if (profilename.trim().equals("")){
                String infoMessage = "Input the Profile Name";
                JOptionPane.showMessageDialog(null, infoMessage, "warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ArrayList<objProject> lstProjects = dbControl.getProject();
            int nLength = lstProjects.size();

            String strFinClass = "";
            for (int i = 0; i < nLength; i ++){
                objProject objPro = lstProjects.get(i);
                String strCurrentClass = objPro.strClass;
                String strSaveClass = parentClass;
                if (isParent(strSaveClass, strCurrentClass)){
                    strFinClass = strCurrentClass;
                }
            }


            String strClass = "";
            if (strFinClass.equals("")){
                strClass = parentClass + "0000.";
            }else{
                strClass = getNextClass(strFinClass);
            }

            objProject newProject = new objProject();
            newProject.id = 0;
            newProject.Type = 1;
            newProject.strClass = strClass;
            newProject.strName = profilename;

            int nPrjproID = 0;
            dbControl.insertProject(newProject);
            int nProjectID = dbControl.getProjectID(parentClass);
            nPrjproID = dbControl.getProjectID(strClass);
    //        int nPrjproID = 0;
    //        nPrjproID = dbControl.getProjectID(newProject);

            objProfile newProfile = new objProfile();
            newProfile.id = 0;
            newProfile.proxyid = proxyid;
            newProfile.profiletypeid = profiletypeid;
            newProfile.fname = fname;
            newProfile.lname = lname;
            newProfile.fullname = fullname;
            newProfile.email = email;
            newProfile.password = password;
            newProfile.phonenumber = phonenumber;
            newProfile.recoveryemail = recoveryemail;
            newProfile.recoverymobile = recoverymobile;
            newProfile.profilename = "";
            newProfile.prjprfid = nPrjproID;
            dbControl.insertProfile(newProfile);
        }
        
        JOptionPane.showMessageDialog(null, "Save successfully.");
        
        //refresh table
        ArrayList lstProjects = new ArrayList<objProject>();
        lstProjects = dbControl.getProject();
        MBMainWindow.getTree().setData(lstProjects);

        MBMainWindow.getTree().refresh();
        //
        dispose();
    }//GEN-LAST:event_jBtn_SaveActionPerformed

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
            java.util.logging.Logger.getLogger(ImportAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImportAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImportAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImportAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImportAccount dialog = new ImportAccount(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jBtn_Apply;
    private javax.swing.JButton jBtn_ChangePrifileType;
    private javax.swing.JButton jBtn_Save;
    private javax.swing.JComboBox jCmb_ProfileType;
    private javax.swing.JPanel jPan_Table;
    // End of variables declaration//GEN-END:variables
}
