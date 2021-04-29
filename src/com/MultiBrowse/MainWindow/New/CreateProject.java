/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.New;

import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.customcontrol.objProject;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.*;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import com.MultiBrowse.customcontrol.TreeComboBox;
/**
 *
 * @author title
 */
public class CreateProject extends javax.swing.JDialog {

    /**
     * Creates new form CreateProject
     */
    private int FormType = 0;
    private int nPrjID = 0;
    public CreateProject(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initSettings();
        initComponents();
//        initTextField();
        initLocations();
        
        setIconImage();
    }
    
    private void setIconImage()
    {
        ImageIcon img = new ImageIcon(getClass().getResource("/MBIcon.png"));
        setIconImage(img.getImage());
    }
    
    public CreateProject(java.awt.Frame parent, boolean modal, int nProjectID){
        super(parent, modal);
        FormType = 1;
        nPrjID = nProjectID;
        initSettings();
        initComponents();
        
        //other setting
        jLocation_pan.setVisible(false);
        jLabel2.setVisible(false);
                
        setResizable(false);
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 245, dim.height/2 - 87);
        
        LocalDBConnect dbControl = new LocalDBConnect();
        objProject data = dbControl.getProject(nProjectID);
        jTxt_ProName.setText(data.strName);
        
        JBtn_Create.setText("Save");
        setTitle("Edit Project");
        
        setIconImage();
        // 
    }
    
    public void initTextField(){
        jTxt_ProName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // ignore event
                }
            }
}       );
    }
    
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private javax.swing.JTree jTree;
    
        
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
    
    private void makeComboBox() {
        LocalDBConnect dbControl = new LocalDBConnect();
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
        

        jLocation_pan.setLayout(new BorderLayout());
        jLocation_pan.setPreferredSize(new Dimension(0, 30));
        jCmb_Location = new TreeComboBox(new DefaultTreeModel(hiddenRoot));
        jLocation_pan.add(jCmb_Location, BorderLayout.CENTER);
    }
    
    private void initLocations(){
        makeComboBox();              
    }
    
    private void initSettings(){
        setTitle("Create Project");
                
        setResizable(false);
        
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 301, dim.height/2 - 140);
    }

    public CreateProject() {
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxt_ProName = new javax.swing.JTextField();
        JBtn_Create = new javax.swing.JButton();
        jLocation_pan = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Project Name:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Location:");

        jTxt_ProName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxt_ProName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_ProNameActionPerformed(evt);
            }
        });

        JBtn_Create.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JBtn_Create.setText("Create");
        JBtn_Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBtn_CreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLocation_panLayout = new javax.swing.GroupLayout(jLocation_pan);
        jLocation_pan.setLayout(jLocation_panLayout);
        jLocation_panLayout.setHorizontalGroup(
            jLocation_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jLocation_panLayout.setVerticalGroup(
            jLocation_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxt_ProName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addComponent(jLocation_pan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JBtn_Create)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxt_ProName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLocation_pan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(JBtn_Create)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    
    private void JBtn_CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBtn_CreateActionPerformed
        // TODO add your handling code here:
        LocalDBConnect dbControl = new LocalDBConnect();
        String strProName = jTxt_ProName.getText();
        
        if (strProName.trim().equals("")){
            String infoMessage = "Input the Project Name";
            JOptionPane.showMessageDialog(null, infoMessage, "warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (FormType == 0){
            
            String parentClass = jCmb_Location.getProjectClass();
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

            objProject newPro = new objProject();
            newPro.Type = 0;
            newPro.strClass = strClass;
            newPro.strName = strProName;
            newPro.id = 0;

            dbControl.insertProject(newPro);
        }else{
            objProject updatePro = new objProject();
            updatePro.Type = 0;
            updatePro.strName = strProName;
            updatePro.id = nPrjID;
            updatePro.strClass = "";
            dbControl.updateProjectInfo(updatePro);
        }
        
        this.dispose();
    }//GEN-LAST:event_JBtn_CreateActionPerformed

    private void jTxt_ProNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxt_ProNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_ProNameActionPerformed

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
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CreateProject dialog = new CreateProject(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton JBtn_Create;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jLocation_pan;
    private javax.swing.JTextField jTxt_ProName;
    // End of variables declaration//GEN-END:variables
    private TreeComboBox jCmb_Location;
}
