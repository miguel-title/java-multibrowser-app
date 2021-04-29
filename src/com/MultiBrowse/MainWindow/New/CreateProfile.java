/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.New;
import com.MultiBrowse.customcontrol.BoundsPopupMenuListener;
import com.MultiBrowse.customcontrol.ComboItem;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.customcontrol.TreeComboBox;
import com.MultiBrowse.customcontrol.objProfile;
import com.MultiBrowse.customcontrol.objProfiletype;
import com.MultiBrowse.customcontrol.objProject;
import com.MultiBrowse.customcontrol.objProxyman;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 *
 * @author title
 */
public class CreateProfile extends javax.swing.JDialog {

    /**
     * Creates new form CreateProfile
     */
    private int nFormType = 0;
    private int nPrjPrfID = 0;
    
    private boolean pwProperty = false;
    public CreateProfile(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initSettings();
        initProjectCombo();
        initProxyCombo();
        initProfileTypeCombo();
        
        setIconImage();
    }
    
    private void setIconImage()
    {
        ImageIcon img = new ImageIcon(getClass().getResource("/MBIcon.png"));
        setIconImage(img.getImage());
    }
    
    public CreateProfile(java.awt.Frame parent, boolean modal, int nProfileID){
        super(parent, modal);
        nFormType = 1;
        nPrjPrfID = nProfileID;
        initComponents();
        initSettings();
        ArrayList<objProxyman> lstProxies = initProxyCombo();
        ArrayList<objProfiletype> lstProfileTypes = initProfileTypeCombo();
        ArrayList<String> lstUserAgents = initUserAgent();
        
        //other Setting
        jPan_Prj.setVisible(false);
        jLabel3.setVisible(false);
        
        setResizable(false);
        
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 303, dim.height/2 - 330);
        LocalDBConnect dbControl = new LocalDBConnect();
        objProfile data = dbControl.getProfile(nProfileID);
        
        jTxt_Email.setText(data.email);
        jTxt_FName.setText(data.fname);
        jTxt_FullName.setText(data.fullname);
        jTxt_LName.setText(data.lname);
        jTxt_PW.setText(data.password);
        jTxt_Phone.setText(data.phonenumber);
        jTxt_RcEmail.setText(data.recoveryemail);
        jTxt_RcMobile.setText(data.recoverymobile);
        jTxt_PfName.setText(data.profilename);
        
        int nProfileTypeIndex = 0;
        for (int i = 0; i < lstProfileTypes.size(); i ++){
            if (lstProfileTypes.get(i).ID == data.profiletypeid){
                nProfileTypeIndex = i;
                break;
            }
        }
        jCmb_PfType.setSelectedIndex(nProfileTypeIndex);
        
        int nUserAgentIndex = 0;
        for (int i = 0; i < lstUserAgents.size(); i ++){
            if (lstUserAgents.get(i).toString().equals(data.useragent)){
                nUserAgentIndex = i;
                break;
            }
        }
        
        jCmb_useragent.setSelectedIndex(nUserAgentIndex + 1);
        
        int nProxyIndex = 0;
        for (int i = 0; i < lstProxies.size(); i ++){
            if (lstProxies.get(i).ID == data.proxyid){
                nProxyIndex = i;
                break;
            }
        }
        jCmb_Px.setSelectedIndex(nProxyIndex);
        
        jBtn_Create.setText("Save");
                
        setTitle("Edit Profile");
        
        setIconImage();
        
        resize(606, 646);
        
    }
    
    private ArrayList<objProxyman> initProxyCombo(){
        LocalDBConnect dbControl = new LocalDBConnect();
        ArrayList<objProxyman> lstResult = dbControl.getProxies(0);
        
        for (int i = 0; i < lstResult.size(); i ++){
            jCmb_Px.addItem(new ComboItem( lstResult.get(i).ID, lstResult.get(i).Proxy_Ip ));
        }
        
        jCmb_Px.addActionListener(e -> {
            JComboBox c = (JComboBox) e.getSource();
            ComboItem item = (ComboItem) c.getSelectedItem();
//            System.out.println(item.getId() + " : " + item.getDescription());
        });
        
        return lstResult;
    }
    
    private ArrayList<objProfiletype> initProfileTypeCombo(){
        LocalDBConnect dbControl = new LocalDBConnect();
        ArrayList<objProfiletype> lstResult = dbControl.getProfileTypes(0);
        for (int i = 0; i < lstResult.size(); i ++){
            jCmb_PfType.addItem(new ComboItem( lstResult.get(i).ID, lstResult.get(i).ProfileType ));
        }
        
        jCmb_PfType.addActionListener(e -> {
            JComboBox c = (JComboBox) e.getSource();
            ComboItem item = (ComboItem) c.getSelectedItem();
//            System.out.println(item.getId() + " : " + item.getDescription());
        });
        
        return lstResult;
    }
    
    private void initProjectCombo(){
        makeComboBox();     
    }
    
    private void initSettings(){
        jBtn_SwitchPW.setBorderPainted(false);
//        jBtn_SwitchPW.setFocusPainted(false);
        jBtn_SwitchPW.setContentAreaFilled(false);
            
        setTitle("Create Profile");
                        
        setResizable(false);
        
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 303, dim.height/2 - 330);
        
        
        jTxt_PW.setEchoChar('\u25cf');
            
        initUserAgent();
    }

    private ArrayList<String> initUserAgent(){
        ArrayList<String> lstUserAgent = new ArrayList<String>();
        
        lstUserAgent = readUserAgentFile();
        
        jCmb_useragent.removeAllItems();
        jCmb_useragent.addItem(new ComboItem(0, "default"));
        for (int i = 0; i < lstUserAgent.size(); i ++){
            jCmb_useragent.addItem(new ComboItem( i + 1, lstUserAgent.get(i)));
        }
        
        
        jCmb_useragent.addPopupMenuListener(new BoundsPopupMenuListener(true, false));
        
        return lstUserAgent;
    }
    
    private ArrayList<String> readUserAgentFile(){
        ArrayList<String> lstResult = new ArrayList<>();
                
        String strUserAgent = "useragent";
        File f = new File(strUserAgent);
        try{
            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            System.out.println("Reading file using Buffered Reader");

            while ((readLine = b.readLine()) != null) {
//                System.out.println(readLine);
                lstResult.add(readLine);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return lstResult;
    }
    
    public CreateProfile() {
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

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTxt_PfName = new javax.swing.JTextField();
        jCmb_Px = new javax.swing.JComboBox();
        jCmb_PfType = new javax.swing.JComboBox();
        jTxt_FName = new javax.swing.JTextField();
        jTxt_LName = new javax.swing.JTextField();
        jTxt_FullName = new javax.swing.JTextField();
        jTxt_Email = new javax.swing.JTextField();
        jTxt_Phone = new javax.swing.JTextField();
        jTxt_RcEmail = new javax.swing.JTextField();
        jTxt_RcMobile = new javax.swing.JTextField();
        jBtn_Create = new javax.swing.JButton();
        jTxt_PW = new javax.swing.JPasswordField();
        jPan_Prj = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCmb_useragent = new javax.swing.JComboBox();
        jBtn_SwitchPW = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Profile Name:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Project:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Proxy:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Profile Type:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("First Name:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Last Name:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Full Name:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Email:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Password:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Phone Number:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Recovery Email:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Recovery Mobile:");

        jTxt_PfName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxt_PfName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_PfNameActionPerformed(evt);
            }
        });

        jCmb_Px.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jCmb_PfType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_FName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_LName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_FullName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_Email.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_Phone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_RcEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_RcMobile.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jBtn_Create.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Create.setText("Create");
        jBtn_Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_CreateActionPerformed(evt);
            }
        });

        jTxt_PW.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxt_PW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_PWActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPan_PrjLayout = new javax.swing.GroupLayout(jPan_Prj);
        jPan_Prj.setLayout(jPan_PrjLayout);
        jPan_PrjLayout.setHorizontalGroup(
            jPan_PrjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPan_PrjLayout.setVerticalGroup(
            jPan_PrjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("User-Agent:");

        jCmb_useragent.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jBtn_SwitchPW.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eye.png"))); // NOI18N
        jBtn_SwitchPW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_SwitchPWActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxt_FName)
                    .addComponent(jTxt_LName)
                    .addComponent(jTxt_FullName)
                    .addComponent(jTxt_Email)
                    .addComponent(jTxt_Phone)
                    .addComponent(jTxt_RcEmail)
                    .addComponent(jTxt_RcMobile)
                    .addComponent(jPan_Prj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCmb_Px, 0, 466, Short.MAX_VALUE)
                    .addComponent(jCmb_PfType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTxt_PW)
                        .addGap(2, 2, 2)
                        .addComponent(jBtn_SwitchPW, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCmb_useragent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxt_PfName))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtn_Create, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxt_PfName, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jPan_Prj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCmb_Px, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCmb_PfType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCmb_useragent, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTxt_FName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTxt_LName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTxt_FullName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTxt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTxt_PW, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_SwitchPW, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTxt_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTxt_RcEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTxt_RcMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jBtn_Create, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxt_PfNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxt_PfNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_PfNameActionPerformed

    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private javax.swing.JTree jTree;
    
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
        
        
        jPan_Prj.setLayout(new BorderLayout());
        jPan_Prj.setPreferredSize(new Dimension(0, 30));
        jCmb_Project = new TreeComboBox(new DefaultTreeModel(hiddenRoot));
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
     
    private void jBtn_CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_CreateActionPerformed
        // TODO add your handling code here:
        LocalDBConnect dbControl = new LocalDBConnect();
        //for Prj_Prf table
        String profilename = jTxt_PfName.getText();
        //for Profile_Info table
        String parentClass = "";
        if (nFormType == 0)
            parentClass = jCmb_Project.getProjectClass();
        int proxyid = 0;
        int nPxSelectedIndex = jCmb_Px.getSelectedIndex();
        if (nPxSelectedIndex == -1){
            proxyid = 0;
        }else{
            proxyid = ((ComboItem)jCmb_Px.getSelectedItem()).getId();
        }
        
        int profiletypeid = 0;
        int nPfTypeSelectedIndex = jCmb_PfType.getSelectedIndex();
        if (nPfTypeSelectedIndex == -1){
            profiletypeid = 0;
        }else{
            profiletypeid = ((ComboItem)jCmb_PfType.getSelectedItem()).getId();
        }
        
        String fname = jTxt_FName.getText();
        String lname = jTxt_LName.getText();
        String fullname = jTxt_FullName.getText();
        String email = jTxt_Email.getText();
        String password = jTxt_PW.getText();
        String phonenumber = jTxt_Phone.getText();
        String recoveryemail = jTxt_RcEmail.getText();
        String recoverymobile = jTxt_RcMobile.getText();
        
        String useragent = "";
        if (jCmb_useragent.getSelectedIndex() != 0)
            useragent = jCmb_useragent.getSelectedItem().toString();
        
        
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
        if (nFormType == 0){
            dbControl.insertProject(newProject);
            int nProjectID = dbControl.getProjectID(parentClass);
            nPrjproID = dbControl.getProjectID(strClass);
        }
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
        newProfile.useragent = useragent;
        if (nFormType == 0){
            newProfile.profilename = "";
            newProfile.prjprfid = nPrjproID;
            dbControl.insertProfile(newProfile);
        }else{
            newProfile.profilename = jTxt_PfName.getText();
            newProfile.prjprfid = nPrjPrfID;
            
            dbControl.updateProfile(newProfile);
        }
        
        this.dispose();
        
    }//GEN-LAST:event_jBtn_CreateActionPerformed

    private void jTxt_PWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxt_PWActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_PWActionPerformed

    private void jBtn_SwitchPWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SwitchPWActionPerformed
        // TODO add your handling code here:
        if (jTxt_PW.getText().isEmpty())
            return;
            
        if (pwProperty) {
            jTxt_PW.setEchoChar('\u25cf');
        } else {
            jTxt_PW.setEchoChar((char) 0);
        }
        pwProperty = !pwProperty;
    }//GEN-LAST:event_jBtn_SwitchPWActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Create;
    private javax.swing.JButton jBtn_SwitchPW;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jCmb_PfType;
    private javax.swing.JComboBox jCmb_Px;
    private javax.swing.JComboBox jCmb_useragent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPan_Prj;
    private javax.swing.JTextField jTxt_Email;
    private javax.swing.JTextField jTxt_FName;
    private javax.swing.JTextField jTxt_FullName;
    private javax.swing.JTextField jTxt_LName;
    private javax.swing.JPasswordField jTxt_PW;
    private javax.swing.JTextField jTxt_PfName;
    private javax.swing.JTextField jTxt_Phone;
    private javax.swing.JTextField jTxt_RcEmail;
    private javax.swing.JTextField jTxt_RcMobile;
    // End of variables declaration//GEN-END:variables

    private TreeComboBox jCmb_Project;
}

class ItemRenderer extends BasicComboBoxRenderer {
  @Override
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);
    if (value != null) {
      ComboItem item = (ComboItem) value;
      setText(item.getDescription().toUpperCase());
    }
    if (index == -1) {
      ComboItem item = (ComboItem) value;
      setText("" + item.getId());
    }
    return this;
  }
}