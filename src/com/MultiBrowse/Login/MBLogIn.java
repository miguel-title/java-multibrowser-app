/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.Login;

import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
import com.MultiBrowse.customcontrol.CustomTextField;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.MainWindow.MBMainWindow;

public class MBLogIn extends JFrame { 
    // JFrame 
    static JFrame jF_Main;     

    // JTextField 
    static CustomTextField jTxt_Username; 
  
    // JPasswodField 
    static JPasswordField jTxt_Password; 
  
    // JButton 
    static JButton jBtn_LogIn; 
  
    // label to display text 
    static JLabel jLbl_Username;
    static JLabel jLbl_Password;
    static JButton jBtn_PWEye;
  
    // default constructor 
    public MBLogIn() 
    {         
        initSetting();
        initComponent();
        
        setIconImage();
    } 
    
    private void setIconImage()
    {
        ImageIcon img = new ImageIcon(getClass().getResource("/MBIcon.png"));
        setIconImage(img.getImage());
    }
    
    private void initSetting(){
        setTitle("Log in to MultiBrowse V1.0.0.1");
//        setPreferredSize(new Dimension(372, 185));
        
        
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 186, dim.height/2 - 93);
        
    }
    
    private boolean pwProperty = false;
    private void jBtn_PWEye_ActionPerformed(java.awt.event.ActionEvent evt){
        // TODO add your handling code here:
        if (jTxt_Password.getText().isEmpty())
            return;
        if (pwProperty) {
            jTxt_Password.setEchoChar('\u25cf');
        } else {
            jTxt_Password.setEchoChar((char) 0);
        }
        pwProperty = !pwProperty;
    }
    
    private void initComponent(){
        // create a new frame to store text field and button 
        jF_Main = new JFrame(); 
    
        // create a new button 
        jBtn_LogIn = new JButton("LogIn"); 
        jBtn_LogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_LogIn_ActionPerformed(evt);
            }
        });
        
        jBtn_PWEye = new JButton("");
        jBtn_PWEye.setBorderPainted(false);
//        jBtn_SwitchPW.setFocusPainted(false);
        jBtn_PWEye.setContentAreaFilled(false);
        jBtn_PWEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eye.png")));
        jBtn_PWEye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_PWEye_ActionPerformed(evt);
            }
        });
        
        
        
        jBtn_PWEye.setPreferredSize(new Dimension(28, 28));
        
        // create username label
        jLbl_Username = new JLabel("UserName:");
        
        jLbl_Username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        // create password Label
        jLbl_Password = new JLabel("Password:");
        
        jLbl_Password.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        // create a object of JTextField with 16 columns and initial text 
        jTxt_Username = new CustomTextField(16); 
        jTxt_Username.setPlaceholder("example@address.com");
  
        // create a object of passwodField with 16 columns 
        jTxt_Password = new JPasswordField(16); 
  
        jTxt_Password.setEchoChar('\u25cf');
        // create an object of font type 
        Font fo = new Font("Serif", Font.PLAIN, 20); 
    
        // set the font of the textfield 
        jTxt_Username.setFont(fo); 
        jTxt_Username.setForeground(Color.BLACK);
        
        jTxt_Password.setFont(fo);
        jTxt_Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_Password_ActionPerformed(evt);
            }
        });
  
        // create a panel to add buttons and textfield 
        JPanel jP_Null = new JPanel();
        JPanel jP_User = new JPanel();
        JPanel jP_Password = new JPanel();
        JPanel jp_PasswordPart = new JPanel();
        JPanel jP_Submit = new JPanel();
        
        // add buttons and textfield to panel 
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        getContentPane().setLayout(new java.awt.GridLayout(4, 0, 0, 1));
//        getContentPane().add(jP_Null);
//        
//        jP_User.add(jLbl_Username);
//        jP_User.add(jTxt_Username);
//        getContentPane().add(jP_User);
//        
//        jp_PasswordPart.add(jLbl_Password);
//        jp_PasswordPart.add(jTxt_Password);
//        jp_PasswordPart.add(jBtn_PWEye);
//        
//        jP_Password.add(jp_PasswordPart);
//        
//        getContentPane().add(jP_Password);
//        
//        jP_Submit.add(jBtn_LogIn); 
//        getContentPane().add(jP_Submit);
        
        //        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtn_LogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLbl_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLbl_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxt_Password, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jTxt_Username))
                .addGap(2, 2, 2)
                .addComponent(jBtn_PWEye, javax.swing.GroupLayout.PREFERRED_SIZE, 28, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLbl_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLbl_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_PWEye, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jBtn_LogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        
        pack();
        
        setResizable(false);
    }
    
    public void jBtn_LogIn_ActionPerformed(java.awt.event.ActionEvent evt) {
                String s = evt.getActionCommand();
                if (s.equals("LogIn")) {   
                    String strUserName = jTxt_Username.getText();
                    String strPassword = jTxt_Password.getText();
                    
                    LocalDBConnect dbControl = new LocalDBConnect();
                    boolean isValidate = dbControl.validateUser(strUserName, strPassword);
                    String strLicense = "";
                    if (isValidate){
                        MBMainWindow  Window = new MBMainWindow(strLicense);
                        Window.show();
                        
                        setVisible(false);
                        dispose();
                    }else{
                        String infoMessage = "Your License Key is not currently active\n\nPlease contact the Digisuite support team if you are having a\ntechnical issue or please reactivate your subscription to\naccess the software!";
                        String titleBar = "Digisuite Multibrowse";
                        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
    
   public void jTxt_Password_ActionPerformed(java.awt.event.ActionEvent evt){
        String strUserName = jTxt_Username.getText();
        String strPassword = jTxt_Password.getText();

        LocalDBConnect dbControl = new LocalDBConnect();
        boolean isValidate = dbControl.validateUser(strUserName, strPassword);
        String strLicense = "";
        if (isValidate){
            MBMainWindow  Window = new MBMainWindow(strLicense);
            Window.show();

            setVisible(false);
            dispose();
        }else{
            String infoMessage = "Your License Key is not currently active\n\nPlease contact the Digisuite support team if you are having a\ntechnical issue or please reactivate your subscription to\naccess the software!";
            String titleBar = "Digisuite Multibrowse";
            JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.WARNING_MESSAGE);
        }
   }
}
