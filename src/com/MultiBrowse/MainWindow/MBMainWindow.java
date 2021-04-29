/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow;

import com.javafxwebbrowser.application.JFXBrowser;
import com.jxbrowser.JXBrowser;
import com.jxbrowser.OffsetTabbedPaneUI;
import com.MultiBrowse.MainWindow.Actions.*;
import com.MultiBrowse.MainWindow.Help.*;
import com.MultiBrowse.MainWindow.Import.*;
import com.MultiBrowse.MainWindow.New.*;
import com.MultiBrowse.MainWindow.Settings.*;
import com.MultiBrowse.customcontrol.ChildTabbedPanewithclosebutton;
import com.MultiBrowse.customcontrol.ComboItem;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.customcontrol.TabbedPanewithclosebutton;
import com.MultiBrowse.customcontrol.dragdroptree;
import com.MultiBrowse.customcontrol.objProfile;
import com.MultiBrowse.customcontrol.objProject;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.os.Environment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.DesktopPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.h2.server.web.DbContents;
import org.h2.tools.Restore;

/**
 *
 * @author title
 */
public class MBMainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MBMainWindow
     */
    
    String m_strLicense = "";
    
    //Component releated with browser.
    JFXPanel javafxPanel;
    WebView webComponent;
    JPanel mainPanel;
    JTextField urlField;
    JButton goButton;
    
    ArrayList<Integer> lstTabInfo;
    ArrayList<JXBrowser> lstTabBrowserInfo;
    //

    public MBMainWindow(String strLicense) {
        javafxPanel = new JFXPanel();
        
        lstTabInfo = new ArrayList<Integer>();
        lstTabBrowserInfo = new ArrayList<JXBrowser>();
        
        initSettings(strLicense);
        initComponents();
        initTree();
        
        
        Color activeColor = new Color(238, 238, 238);
        
        UIManager.put("TabbedPane.selected", activeColor);
        
        initTabbedPane();
        jBtn_Min.setBorderPainted(false);
        jBtn_Min.setFocusPainted(false);
        jBtn_Min.setContentAreaFilled(false);
        
        nVisibleType = 0;
        
        setIconImage();
    }
    
    private void setIconImage()
    {
        ImageIcon img = new ImageIcon(getClass().getResource("/MBIcon.png"));
        setIconImage(img.getImage());
    }
    
    public MBMainWindow(){
        
    }
    
    
    OffsetTabbedPaneUI tabUI;
    
    private void initTabbedPane(){
//        jTab_Container = new TabbedPanewithclosebutton();
        jTab_Container = new ChildTabbedPanewithclosebutton();
        
        Color inactiveColor = new Color(206, 206, 206);
        jTab_Container.setBackground(inactiveColor);
        
//        tabUI = new OffsetTabbedPaneUI();
//        jTab_Container.setUI(tabUI);
        
        ContainerListener containerListener = new ContainerListener() {
            public void componentAdded(ContainerEvent e) {
//                System.out.print("Add TabControl\n");
            }

            public void componentRemoved(ContainerEvent e) {
//                System.out.print("Remove TabControl\n");
                int nRemovedIndex = jTab_Container.getRemovedIndex();
                lstTabInfo.remove(nRemovedIndex);
                
                Engine curEngine = lstTabBrowserInfo.get(nRemovedIndex).getEngine();
                if (Environment.isWindows()) {
                    new Thread(curEngine::close).start();
                } else {
                    curEngine.close();
                }
            }
        };
        jTab_Container.addContainerListener(containerListener);
        
        jPan_tab.setLayout(new GridLayout());
        jPan_tab.add(jTab_Container);
    }

    private void initSettings(String strLicense){
        m_strLicense = strLicense;
        setTitle("MultiBrowse V1.0.0.1");
        setMinimumSize(new Dimension(600, 400));
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPan_tree = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jBtn_Min = new javax.swing.JButton();
        jPan_tab = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMn_New = new javax.swing.JMenu();
        jMi_CtPj = new javax.swing.JMenuItem();
        jMi_CtPf = new javax.swing.JMenuItem();
        jMn_Import = new javax.swing.JMenu();
        jMi_IpAcc = new javax.swing.JMenuItem();
        jMi_IpBu = new javax.swing.JMenuItem();
        jMn_Actions = new javax.swing.JMenu();
        jMi_PxMan = new javax.swing.JMenuItem();
        jMi_EpMBBu = new javax.swing.JMenuItem();
        jMi_PfRp = new javax.swing.JMenuItem();
        jMi_EpAcc = new javax.swing.JMenuItem();
        jMn_Settings = new javax.swing.JMenu();
        jMi_PxSet = new javax.swing.JMenuItem();
        jMi_MBLs = new javax.swing.JMenuItem();
        jMi_PfT = new javax.swing.JMenuItem();
        jMn_Help = new javax.swing.JMenu();
        jMi_UG = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPan_tree.setMaximumSize(new java.awt.Dimension(32767, 200));
        jPan_tree.setMinimumSize(new java.awt.Dimension(100, 200));
        jPan_tree.setPreferredSize(new java.awt.Dimension(300, 200));

        javax.swing.GroupLayout jPan_treeLayout = new javax.swing.GroupLayout(jPan_tree);
        jPan_tree.setLayout(jPan_treeLayout);
        jPan_treeLayout.setHorizontalGroup(
            jPan_treeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPan_treeLayout.setVerticalGroup(
            jPan_treeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 556, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));

        jBtn_Min.setText("<<");
        jBtn_Min.setMargin(new java.awt.Insets(2, 0, 2, 0));
        jBtn_Min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_MinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBtn_Min, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtn_Min, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPan_tabLayout = new javax.swing.GroupLayout(jPan_tab);
        jPan_tab.setLayout(jPan_tabLayout);
        jPan_tabLayout.setHorizontalGroup(
            jPan_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );
        jPan_tabLayout.setVerticalGroup(
            jPan_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 556, Short.MAX_VALUE)
        );

        jMn_New.setText("New");

        jMi_CtPj.setText("Create Project");
        jMi_CtPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_CtPjActionPerformed(evt);
            }
        });
        jMn_New.add(jMi_CtPj);

        jMi_CtPf.setText("Create Profile");
        jMi_CtPf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_CtPfActionPerformed(evt);
            }
        });
        jMn_New.add(jMi_CtPf);

        jMenuBar1.add(jMn_New);

        jMn_Import.setText("Import");

        jMi_IpAcc.setText("Import Accounts");
        jMi_IpAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_IpAccActionPerformed(evt);
            }
        });
        jMn_Import.add(jMi_IpAcc);

        jMi_IpBu.setText("Import Backup");
        jMi_IpBu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_IpBuActionPerformed(evt);
            }
        });
        jMn_Import.add(jMi_IpBu);

        jMenuBar1.add(jMn_Import);

        jMn_Actions.setText("Actions");

        jMi_PxMan.setText("Proxy Management");
        jMi_PxMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_PxManActionPerformed(evt);
            }
        });
        jMn_Actions.add(jMi_PxMan);

        jMi_EpMBBu.setText("Export to MB Backup");
        jMi_EpMBBu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_EpMBBuActionPerformed(evt);
            }
        });
        jMn_Actions.add(jMi_EpMBBu);

        jMi_PfRp.setText("All Profiles Report");
        jMi_PfRp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_PfRpActionPerformed(evt);
            }
        });
        jMn_Actions.add(jMi_PfRp);

        jMi_EpAcc.setText("Export Accounts");
        jMi_EpAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_EpAccActionPerformed(evt);
            }
        });
        jMn_Actions.add(jMi_EpAcc);

        jMenuBar1.add(jMn_Actions);

        jMn_Settings.setText("Settings");

        jMi_PxSet.setText("Proxy Settings");
        jMi_PxSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_PxSetActionPerformed(evt);
            }
        });
        jMn_Settings.add(jMi_PxSet);

        jMi_MBLs.setText("MultiBrowse License");
        jMi_MBLs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_MBLsActionPerformed(evt);
            }
        });
        jMn_Settings.add(jMi_MBLs);

        jMi_PfT.setText("Profile Types");
        jMi_PfT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_PfTActionPerformed(evt);
            }
        });
        jMn_Settings.add(jMi_PfT);

        jMenuBar1.add(jMn_Settings);

        jMn_Help.setText("Help");

        jMi_UG.setText("User Guide");
        jMi_UG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMi_UGActionPerformed(evt);
            }
        });
        jMn_Help.add(jMi_UG);

        jMenuBar1.add(jMn_Help);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPan_tree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPan_tab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPan_tree, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPan_tab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMi_CtPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_CtPjActionPerformed
        // TODO add your handling code here:
        CreateProject jF_CreateProject = new CreateProject(this, true);
        jF_CreateProject.pack();
        jF_CreateProject.setVisible(true);
        
        // add a window listener
        jF_CreateProject.addWindowListener(new WindowAdapter()
        {
          public void windowClosed(WindowEvent e)
          {
            System.out.println("jdialog window closed");
            refreshTree();
          }

          public void windowClosing(WindowEvent e)
          {
            System.out.println("jdialog window closing");
          }
        });
    }//GEN-LAST:event_jMi_CtPjActionPerformed

    private static dragdroptree jTr_Projects;
    
    public static dragdroptree getTree(){
        return jTr_Projects;
    }
    
    public void refreshTree(){
        LocalDBConnect dbControl = new LocalDBConnect();
        ArrayList lstProjects = new ArrayList<objProject>();
        lstProjects = dbControl.getProject();
        jTr_Projects.setData(lstProjects);
        
        jTr_Projects.refresh();
    }
    
    private void jMi_UGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_UGActionPerformed
        // TODO add your handling code here:
        UserGuide jF_UserGuide = null;
        try {
            jF_UserGuide = new UserGuide(this, true);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MBMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        jF_UserGuide.show();
    }//GEN-LAST:event_jMi_UGActionPerformed

    private void jMi_CtPfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_CtPfActionPerformed
        // TODO add your handling code here:
        CreateProfile jF_CreateProfile = new CreateProfile(this, true);
        jF_CreateProfile.show();
//        jF_CreateProfile.setLocationRelativeTo(null);
                // add a window listener
        jF_CreateProfile.addWindowListener(new WindowAdapter()
        {
          public void windowClosed(WindowEvent e)
          {
            System.out.println("jdialog window closed");
            refreshTree();
          }

          public void windowClosing(WindowEvent e)
          {
            System.out.println("jdialog window closing");
          }
        });
    }//GEN-LAST:event_jMi_CtPfActionPerformed

    private void jMi_IpAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_IpAccActionPerformed
        // TODO add your handling code here:
        
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", "csv");
        fileChooser.setFileFilter(filter);

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = fileChooser.showOpenDialog(this);
        String strCsvFilePath = "";
        String strBaseDir = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            strCsvFilePath = selectedFile.getAbsolutePath();
            strBaseDir = fileChooser.getCurrentDirectory().toString();
        
            ArrayList<ImportAccountModel> profileList = new ArrayList<ImportAccountModel>();

            profileList = read_profilecsv(strCsvFilePath);

            ImportAccount jF_ImportAccount = new ImportAccount(this, true);
            jF_ImportAccount.setProfileList(profileList);
            jF_ImportAccount.show();
        }
    }//GEN-LAST:event_jMi_IpAccActionPerformed

    private ArrayList<ImportAccountModel> read_profilecsv(String strCsvFilePath){
        ArrayList<ImportAccountModel> profileList = new ArrayList<ImportAccountModel>();
        
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(strCsvFilePath));
            int LineIndex = 0;
            while ((line = br.readLine()) != null) {
                if (LineIndex == 0){
                    LineIndex ++;
                    continue;
                }
                LineIndex ++;
                // use comma as separator
                String[] profile = line.split(cvsSplitBy, -1);
                if (profile.length < 10){
                    return profileList;
                }
                LocalDBConnect dbCon = new LocalDBConnect();
                
                int nProfileTypeID = dbCon.getProfileTypeId(profile[0]);
                ProfileTypesComboItem ProfileTypeItem = new ProfileTypesComboItem(nProfileTypeID, profile[0]);
                
                int nProjectId = dbCon.getProjectId(profile[1]);
                ProjectNameComboItem ProjectNameItem = new ProjectNameComboItem(nProjectId, profile[1]);
                
                int nProxyId = dbCon.getproxyId(profile[2]);
                ProxyComboItem ProxyItem = new ProxyComboItem(nProxyId, profile[9]);
                
                ImportAccountModel newmodel = new ImportAccountModel(0, ProfileTypeItem, ProjectNameItem, profile[2], profile[3], profile[4], profile[5], profile[6], profile[7], profile[8], ProxyItem, false);
                profileList.add(newmodel);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return profileList;

    }
    
    private void jMi_IpBuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_IpBuActionPerformed
        // TODO add your handling code here:
//        ImportBackup jF_ImportBackup = new ImportBackup(this, true);
//        jF_ImportBackup.show();
        
        ImageIcon markIcon = new ImageIcon(getClass().getResource("/question_36.png"));
        int nResult = JOptionPane.showConfirmDialog (null, "If you Import a backup, this will overwrite your\n current project and profile data, are you sure you\n want to do this?", "Change to Import Data", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, markIcon);
        if (nResult == JOptionPane.YES_OPTION){
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP file", "zip");
            fileChooser.setFileFilter(filter);
            
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            
            int result = fileChooser.showOpenDialog(this);
            String strZipFilePath = "";
            String strBaseDir = "";
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                strZipFilePath = selectedFile.getAbsolutePath();
                strBaseDir = fileChooser.getCurrentDirectory().toString();
            }
            
            if (strZipFilePath.isEmpty())
                return;

//            deleteDB();
            
            LocalDBConnect dbControl = new LocalDBConnect();
            dbControl.dropAllObject();
            
            String strBackupFilePath = unZipPasswordProtectedFiles(strZipFilePath);
                       
            String[] args = {"-url", "jdbc:h2:db/mbdb", "-user", "mbuser", "-script", strBackupFilePath, "-options", "compression zip" };
            try {
                org.h2.tools.RunScript.main(args);
                
                ArrayList lstProjects = new ArrayList<objProject>();
                lstProjects = dbControl.getProject();
                MBMainWindow.getTree().setData(lstProjects);
                MBMainWindow.getTree().refresh();
                            
                File file = new File(strBackupFilePath);
                file.delete();
                
                JOptionPane.showMessageDialog(null, "Restore Successfully");
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, ex.toString());
            };
                
            
        }
        
    }//GEN-LAST:event_jMi_IpBuActionPerformed

    private void deleteDB(){
        File dbFile = new File(System.getProperty("user.dir") + "/db");
        deleteFolder(dbFile);
    }
    
    private void deleteFolder(File file){
      for (File subFile : file.listFiles()) {
         if(subFile.isDirectory()) {
            deleteFolder(subFile);
         } else {
            subFile.delete();
         }
      }
      file.delete();
   }
    
    private void jMi_PxManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_PxManActionPerformed
        // TODO add your handling code here:
        ProxyMng jF_ProxyMng = new ProxyMng(this, true);
        jF_ProxyMng.show();
    }//GEN-LAST:event_jMi_PxManActionPerformed

    private void jMi_EpMBBuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_EpMBBuActionPerformed
        // TODO add your handling code here:
//        ExportBackup jF_ExportBackup = new ExportBackup(this, true);
//        jF_ExportBackup.show();
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");   
        fileChooser.setFileFilter(new FileNameExtensionFilter("zip file","zip"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            
            fileChooser.setFileFilter(null);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            String strContentPath = fileToSave.getAbsolutePath() + "_.zip";
            String strFilePath = fileToSave.getAbsolutePath() + ".zip";
            
            String[] args = {"-url", "jdbc:h2:db/mbdb", "-user", "mbuser", "-script", strContentPath, "-options", "compression zip" };
            try {
                org.h2.tools.Script.main(args);
                
                compressFilesWithPwd(strFilePath, strContentPath);
                
                File myObj = new File(strContentPath);
                myObj.delete();
                
                JOptionPane.showMessageDialog(null, "Backup Successful\nBackup File Path:" + strContentPath, "Backup", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, ex.toString());
            };

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        
//        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yy");  
//        Date date = new Date();  
//        String strCurDate = formatter.format(date); 
    
//        LocalDBConnect dbCon = new LocalDBConnect();
//        
//        dbCon.backupDB(strFilePath);
        
    }//GEN-LAST:event_jMi_EpMBBuActionPerformed

      public void compressFilesWithPwd(String zipFilePath, String strContentPath) {
    // Zipped folder name
        try {
          ZipFile zipFile = new ZipFile(zipFilePath);
          ArrayList<File> filesToAdd = new ArrayList<>();
          // Add files which are to be compressed to the array list
          filesToAdd.add(new File(strContentPath));

          // Initiate Zip Parameters 
          ZipParameters parameters = new ZipParameters();
          // set compression method to deflate compression
          parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); 
          parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
          parameters.setEncryptFiles(true);
          parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
          parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
          // Setting password
          parameters.setPassword("test");	        
          zipFile.addFiles(filesToAdd, parameters);

        } catch (ZipException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }				
    }
      
       public String unZipPasswordProtectedFiles(String zipFilePath){
        // Get unzip file path by removing .zip from the zipped file name
        Path path = Paths.get(zipFilePath); 
  
        // call getFileName() and get FileName path object 
        Path fileName = path.getFileName(); 
        
        String strProjectPath = System.getProperty("user.dir");
        String strTmpPath = strProjectPath + "/tmp/";
        
        String unZipFilePath = strTmpPath + fileName.toString().substring(0, fileName.toString().lastIndexOf("."));	
        try {
          ZipFile zipFile = new ZipFile(zipFilePath);
          // provide password if encrypted
          if(zipFile.isEncrypted()){
            zipFile.setPassword("test");
          }
          
          File file = new File(unZipFilePath + "/" + fileName.toString().substring(0, fileName.toString().lastIndexOf(".")) + "_.zip");
            if (file.exists())
                file.delete();
            
          // unzip
          zipFile.extractAll(unZipFilePath);
        } catch (ZipException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        return unZipFilePath + "/" + fileName.toString().substring(0, fileName.toString().lastIndexOf(".")) + "_.zip";
    }
    
    private void jMi_PfRpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_PfRpActionPerformed
        // TODO add your handling code here:
        AllProfilesReport jF_ProfileReport = new AllProfilesReport(this, true);
        jF_ProfileReport.show();
    }//GEN-LAST:event_jMi_PfRpActionPerformed

    private void jMi_EpAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_EpAccActionPerformed
        // TODO add your handling code here:
        ExportAccs jF_ExportAccs = new ExportAccs(this, true);
        jF_ExportAccs.show();
    }//GEN-LAST:event_jMi_EpAccActionPerformed

    private void jMi_PxSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_PxSetActionPerformed
        // TODO add your handling code here:
        ProxySetting jF_ProxySettings = new ProxySetting(this, true);
        jF_ProxySettings.show();
        
        jF_ProxySettings.addWindowListener(new WindowAdapter() 
        {
          public void windowClosed(WindowEvent e)
          {
            jF_ProxySettings.saveData();
            System.out.println("jdialog window closed event received");
          }

          public void windowClosing(WindowEvent e)
          {
            System.out.println("jdialog window closing event received");
          }
        });
    }//GEN-LAST:event_jMi_PxSetActionPerformed

    private void jMi_MBLsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_MBLsActionPerformed
        // TODO add your handling code here:
        MBLicense jF_MBLicense = new MBLicense(this, true);
        jF_MBLicense.show();
    }//GEN-LAST:event_jMi_MBLsActionPerformed

    private void jMi_PfTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMi_PfTActionPerformed
        // TODO add your handling code here:
        ProfileTypes jF_ProfileTypes = new ProfileTypes(this, true);
        jF_ProfileTypes.show();
        
        jF_ProfileTypes.addWindowListener(new WindowAdapter() 
        {
          public void windowClosed(WindowEvent e)
          {
            jF_ProfileTypes.saveData();
            System.out.println("jdialog window closed event received");
          }

          public void windowClosing(WindowEvent e)
          {
            System.out.println("jdialog window closing event received");
          }
        });
    }//GEN-LAST:event_jMi_PfTActionPerformed

    private int nVisibleType;
    private void jBtn_MinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_MinActionPerformed
        // TODO add your handling code here:
        if (nVisibleType == 0){
            jPan_tree.setVisible(false);
            jBtn_Min.setText(">>");
            nVisibleType = 1;
        }else{
            jPan_tree.setVisible(true);
            nVisibleType = 0;
            jBtn_Min.setText("<<");
        }
        
    }//GEN-LAST:event_jBtn_MinActionPerformed

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
            java.util.logging.Logger.getLogger(MBMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MBMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MBMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MBMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MBMainWindow().setVisible(true);
            }
        });
    }
    
    private int nHeight;
    
    public void initTree(){
        LocalDBConnect dbControl = new LocalDBConnect();
        jTr_Projects = new dragdroptree();
        ArrayList lstProjects = new ArrayList<objProject>();
        lstProjects = dbControl.getProject();
        jTr_Projects.setData(lstProjects);

        jPan_tree.setLayout(new BorderLayout());
        try {
            jPan_tree.add(jTr_Projects.getContent(), BorderLayout.CENTER);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MBMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        java.awt.Frame parent = this;
        
        jTr_Projects.refresh();
        
        MouseListener ml;
        ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = jTr_Projects.getTree().getRowForLocation(e.getX(), e.getY());
                TreePath selPath = jTr_Projects.getTree().getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1) {
                        if (SwingUtilities.isRightMouseButton(e)) {

                            int row = jTr_Projects.getTree().getClosestRowForLocation(e.getX(), e.getY());
                            jTr_Projects.getTree().setSelectionRow(row);
                            TreePopup contextMenu = new TreePopup(jTr_Projects, selPath, parent);
                            if (row != 0)
                                contextMenu.show(e.getComponent(), e.getX(), e.getY());
                        }
                        mySingleClick(selRow, selPath);
                    }
                    else if(e.getClickCount() == 2) {
                        myDoubleClick(selRow, selPath);
                    }
                }
            }

            private void myDoubleClick(int selRow, TreePath selPath) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
                setCursor(Cursor.WAIT_CURSOR);
                DefaultMutableTreeNode target = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                String[] lstProjectData = target.toString().split(" ");
                
                System.out.print("\n---doubleClick:" + target.toString() + "\n");
                
                int nCount = lstProjectData.length;
                int nProfileId = Integer.parseInt(lstProjectData[nCount - 1]);
                int nType = Integer.parseInt(lstProjectData[nCount - 2]);
                if (nType == 1)
                    if (!lstTabInfo.contains(nProfileId))
                        showBrowser(nProfileId);
                    else
                        displayBrowser(nProfileId);
                
                setCursor(Cursor.DEFAULT_CURSOR);
            }

            private void mySingleClick(int selRow, TreePath selPath) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                DefaultMutableTreeNode target = (DefaultMutableTreeNode)selPath.getLastPathComponent();
//                System.out.print("\n---singleClick:" + target.toString());
            }
        };
        jTr_Projects.getTree().addMouseListener(ml);
        
//        this.addWindowListener(new WindowAdapter() {
//            // Invoked when a window has been opened.
//            public void windowActivated(WindowEvent e) {
//                System.out.println("Window Opened Event");
//                
//                Rectangle r = getBounds();
//                nHeight = r.height;
//                System.out.print(nHeight);
//                
//            }
//        });
    }
        
    public void displayBrowser(int nProfileId){
        int nIndex = lstTabInfo.indexOf(nProfileId);
        jTab_Container.setSelectedIndex(nIndex);
    }
    
    
    public void showBrowser(int nProfileId){
        LocalDBConnect dbCon = new LocalDBConnect();
        objProfile profileInfo = dbCon.getProfile(nProfileId);
        
        //Child panel design
        JPanel childPanel = new javax.swing.JPanel();
        JButton foxButton = new javax.swing.JButton();
        JButton chromeButton = new javax.swing.JButton();
        JSeparator separator = new javax.swing.JSeparator();
        JPanel browserPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        foxButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        foxButton.setText("Firefox");

        chromeButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chromeButton.setText("Chrome");

//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(browserPanel);
        browserPanel.setLayout(new BorderLayout());

//        browserPanel.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 0, Short.MAX_VALUE)
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 0, Short.MAX_VALUE)
//        );

        javax.swing.GroupLayout jchildpanelLayout = new javax.swing.GroupLayout(childPanel);
        childPanel.setLayout(jchildpanelLayout);
        jchildpanelLayout.setHorizontalGroup(
            jchildpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jchildpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jchildpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jchildpanelLayout.createSequentialGroup()
                        .addComponent(foxButton)
                        .addGap(20, 20, 20)
                        .addComponent(chromeButton)
                        .addGap(0, 536, Short.MAX_VALUE))
                    .addGroup(jchildpanelLayout.createSequentialGroup()
                        .addGroup(jchildpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(separator)
                            .addComponent(browserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jchildpanelLayout.setVerticalGroup(
            jchildpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jchildpanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jchildpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foxButton)
                    .addComponent(chromeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        int nBrowserType = dbCon.getDefaultBrowser();//0:Chrome, 1:Firefox
        
        int proxyid = profileInfo.proxyid;
        String strProxy = dbCon.getProxy(proxyid);
        String strProxyPort = dbCon.getProxyPort(proxyid);
        String strProxyUserName = dbCon.getProxyUserName(proxyid);
        String strProxyPassword = dbCon.getProxyPassword(proxyid);
        String strUserAgent = profileInfo.useragent;
    
        //jfx browser
//        JFXBrowser browser = new JFXBrowser(strProxy, strProxyPort, strProxyUserName, strProxyPassword);
//        browserPanel.add(browser, BorderLayout.CENTER);
        
        //jxbrowser
//        System.setProperty("jxbrowser.license.key", "1BNDHFSC1FVW471XYMWNJRCJXT5D4BII2BNWR31M7XYX82PT9M1M0SWLT4M0YKO0ZNU6W2");  
//        System.setProperty("jxbrowser.license.key", "1BNDHFSC1FW6FL0YHDPTMPQ82UAK0HMELYNLWAWIXA0MCXHJDQEKLFELZ94N8GIAYZ2D2Z");  
        JXBrowser browser = null;
        try{
            browser = new JXBrowser(strProxy, strProxyPort, strProxyUserName, strProxyPassword, profileInfo.id, strUserAgent);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Browser License is not valid", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        
        if (browser == null)
            return;
        
        browserPanel.add(browser, BorderLayout.CENTER);
        lstTabBrowserInfo.add(browser);
        
                       
        revalidate();
        repaint();
        //
        foxButton.setVisible(false);
        chromeButton.setVisible(false);
        separator.setVisible(false);
        //
        
        lstTabInfo.add(nProfileId);
        
        jTab_Container.addTab(profileInfo.profilename, childPanel);
        
        int nIndexCount = jTab_Container.getTabCount();
        jTab_Container.setSelectedIndex(nIndexCount - 1);
        
    }
    
    
    private Font fontTotale = new Font("Verdana", Font.BOLD, 12);
    
//    public void constructTree(ArrayList<objProject> lstData){
//        dragdroptree jTr_Projects = new dragdroptree();
//        jTr_Projects.setData(lstData);
//        
//        
//        jPan_tree.setLayout(new BorderLayout());
//        jPan_tree.add(jTr_Projects.getContent(), BorderLayout.CENTER);
//        
//        jPan_tree.setPreferredSize(new Dimension(300, nHeight));
//    }

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Min;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMi_CtPf;
    private javax.swing.JMenuItem jMi_CtPj;
    private javax.swing.JMenuItem jMi_EpAcc;
    private javax.swing.JMenuItem jMi_EpMBBu;
    private javax.swing.JMenuItem jMi_IpAcc;
    private javax.swing.JMenuItem jMi_IpBu;
    private javax.swing.JMenuItem jMi_MBLs;
    private javax.swing.JMenuItem jMi_PfRp;
    private javax.swing.JMenuItem jMi_PfT;
    private javax.swing.JMenuItem jMi_PxMan;
    private javax.swing.JMenuItem jMi_PxSet;
    private javax.swing.JMenuItem jMi_UG;
    private javax.swing.JMenu jMn_Actions;
    private javax.swing.JMenu jMn_Help;
    private javax.swing.JMenu jMn_Import;
    private javax.swing.JMenu jMn_New;
    private javax.swing.JMenu jMn_Settings;
    private javax.swing.JPanel jPan_tab;
    private javax.swing.JPanel jPan_tree;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

//    private TabbedPanewithclosebutton jTab_Container;
    private ChildTabbedPanewithclosebutton jTab_Container;
}

class TreePopup extends JPopupMenu {
   private LocalDBConnect dbControl = new LocalDBConnect();
   private List<DefaultMutableTreeNode> lstDeletedNodes = new ArrayList<DefaultMutableTreeNode>();
   
   public TreePopup(dragdroptree customtree, TreePath selPath, java.awt.Frame parent) {
      JTree tree = customtree.getTree();
      JMenuItem Edit = new JMenuItem("Edit");
      JMenuItem Delete = new JMenuItem("Delete");
      Edit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            DefaultMutableTreeNode target = (DefaultMutableTreeNode)selPath.getLastPathComponent();
            int nNodeType = Integer.parseInt(target.toString().split(" ")[target.toString().split(" ").length - 2]);
            int nID = Integer.parseInt(target.toString().split(" ")[target.toString().split(" ").length - 1]);
            if (nNodeType == 0){
                CreateProject crtPrjForm = new CreateProject(parent, true, nID);
                                
                crtPrjForm.pack();
                crtPrjForm.setVisible(true);

                // add a window listener
                crtPrjForm.addWindowListener(new WindowAdapter()
                {
                  public void windowClosed(WindowEvent e)
                  {
                    System.out.println("jdialog window closed");
                    ArrayList lstProjects = new ArrayList<objProject>();
                    lstProjects = dbControl.getProject();
                    customtree.setData(lstProjects);

                    customtree.refresh();
                  }

                  public void windowClosing(WindowEvent e)
                  {
                    System.out.println("jdialog window closing");
                  }
                });
            }else{
                CreateProfile crtPrfForm = new CreateProfile(parent, true, nID);
                
//                crtPrfForm.pack();
                crtPrfForm.setVisible(true);
//                crtPrfForm.setLocationRelativeTo(null);

                // add a window listener
                crtPrfForm.addWindowListener(new WindowAdapter()
                {
                  public void windowClosed(WindowEvent e)
                  {
                    System.out.println("jdialog window closed");
                    ArrayList lstProjects = new ArrayList<objProject>();
                    lstProjects = dbControl.getProject();
                    customtree.setData(lstProjects);

                    customtree.refresh();
                  }

                  public void windowClosing(WindowEvent e)
                  {
                    System.out.println("jdialog window closing");
                  }
                });
                
            }
            
            System.out.println("\nEdit child:" + target.toString());
         }
      });
      Delete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            
            DefaultMutableTreeNode target = (DefaultMutableTreeNode)selPath.getLastPathComponent();
            int nID = Integer.parseInt(target.toString().split(" ")[target.toString().split(" ").length - 1]);
            int nType = Integer.parseInt(target.toString().split(" ")[target.toString().split(" ").length - 2]);
            
            String strMessage = "Are you sure to Delete?"; 
            String strTitle = "Delete";
            if (nType == 0){
                strMessage = "Are you sure you want to delete this project?";
                strTitle = "Delete Project";
            }else{
                strMessage = "Are you sure you want to delete this profile?"; 
                strTitle = "Delete Profile";
            }
            
            ImageIcon markIcon = new ImageIcon(getClass().getResource("/question_36.png"));
            int nResult = JOptionPane.showConfirmDialog(null, strMessage, strTitle, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, markIcon);
            if (nResult != JOptionPane.YES_OPTION)
                return;
            
            lstDeletedNodes.clear();
            lstDeletedNodes.add(target);            
            getChildrens(target);
            
            //Delete Nodes in View
            DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
            // Remove nodes saved in nodesToRemove in createTransferable.
            for(int i = 0; i < lstDeletedNodes.size(); i++) {
                model.removeNodeFromParent(lstDeletedNodes.get(i));
            }
            //Delete Nodes in DB
            List<String> lstIDs = new ArrayList<String>();
            for (int i = 0; i < lstDeletedNodes.size(); i ++){
                DefaultMutableTreeNode node = lstDeletedNodes.get(i);
                String strNode = node.toString();
                String strNodeID = strNode.split(" ")[strNode.split(" ").length - 1];
                lstIDs.add(strNodeID);
            }
            dbControl.removeNodes(lstIDs);
            
            
            ArrayList lstProjects = new ArrayList<objProject>();
            lstProjects = dbControl.getProject();
            customtree.setData(lstProjects);
            customtree.refresh();
            
            System.out.println("\nDelete child:" + target.toString());
         }
      });
      add(Edit);
      add(new JSeparator());
      add(Delete);
   }
   
    public void getChildrens(TreeNode parentnode){
        int nNodeCount = parentnode.getChildCount();
        int nNodeIndex = 0;
        while (nNodeCount > 0){
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) parentnode.getChildAt(nNodeIndex);
            lstDeletedNodes.add(childNode);
            
            getChildrens(childNode);
            
            nNodeCount --;
            nNodeIndex ++;
        }
    }
}