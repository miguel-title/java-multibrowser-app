/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jxbrowser;

import com.MultiBrowse.customcontrol.ChildTabbedPanewithclosebutton;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.PrintCallback;
import com.teamdev.jxbrowser.navigation.event.FrameLoadFinished;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import com.teamdev.jxbrowser.zoom.ZoomLevel;
import com.teamdev.jxbrowser.zoom.ZoomLevels;
import com.teamdev.jxbrowser.zoom.event.ZoomLevelChanged;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author title
 */
public class PopupMenu extends javax.swing.JPanel {
        
    // Variables declaration - do not modify                     
    private javax.swing.JButton jBtn_ClearCache;
    private javax.swing.JButton jBtn_Print;
    private javax.swing.JButton jBtn_NewTab;
    private javax.swing.JButton jBtn_ZoomOut;
    private javax.swing.JButton jBtn_Zoomin;
    private javax.swing.JLabel jLbl_ZoomState;
    // End of variables declaration  
    
    private Browser m_browser;
    private BrowserView m_view;
    
    public PopupMenu(Browser browser, BrowserView view) 
    { 
        m_browser = browser;
        m_view = view;
        
        initComponents();
        initButtonSetting();
        
        Color borderColor = new Color(200,200,200);
        Border blackline = BorderFactory.createLineBorder(borderColor);
        setBorder(blackline);
    }
    
    private void initButtonSetting(){
        Color buttonColor = new Color(221,221,221);
                 
        jBtn_ClearCache.setBackground(buttonColor);
        jBtn_ClearCache.setOpaque(true);
        jBtn_ClearCache.setBorderPainted(false);
        jBtn_ClearCache.setFocusPainted(false);
        
        jBtn_Print.setBackground(buttonColor);
        jBtn_Print.setOpaque(true);
        jBtn_Print.setBorderPainted(false);
        jBtn_Print.setFocusPainted(false);
        
        jBtn_NewTab.setBackground(buttonColor);
        jBtn_NewTab.setOpaque(true);
        jBtn_NewTab.setBorderPainted(false);
        jBtn_NewTab.setFocusPainted(false);
        
        jBtn_ZoomOut.setBackground(buttonColor);
        jBtn_ZoomOut.setOpaque(true);
        jBtn_ZoomOut.setBorderPainted(false);
        jBtn_ZoomOut.setFocusPainted(false);
        
        jBtn_Zoomin.setBackground(buttonColor);
        jBtn_Zoomin.setOpaque(true);
        jBtn_Zoomin.setBorderPainted(false);
        jBtn_Zoomin.setFocusPainted(false);
        
        jLbl_ZoomState.setBackground(buttonColor);
        jLbl_ZoomState.setOpaque(true);
    }
    
    private void jbtn_NewTabActionPerformed(java.awt.event.ActionEvent evt){
        
        JXBrowser parentBrowser = (JXBrowser)getParent().getParent().getParent().getParent();

        Container contain = getParent().getParent().getParent();
        ChildTabbedPanewithclosebutton editorTabbedPane =(ChildTabbedPanewithclosebutton) contain;
        int nIndex = editorTabbedPane.indexOfComponent(getParent().getParent());
        int nTabCount = editorTabbedPane.getComponentCount();

        String strProxy = parentBrowser.m_strProxy;
        String strProxyPort = parentBrowser.m_strProxyPort;
        String strProxyUserName = parentBrowser.m_strProxyUserName;
        String strProxyPassword = parentBrowser.m_strProxyPassword;
        JXTabControl jxTabControl = parentBrowser.insertTab(nTabCount - 2, "", strProxy, strProxyPort, strProxyUserName, strProxyPassword, m_browser, 1);
        
        ((JXTabControl)getParent().getParent()).setMenuVisible(false);
    }
    
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents(){
        jBtn_NewTab = new javax.swing.JButton();
        jBtn_NewTab.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_NewTabActionPerformed(evt);
            }
        });
        
        jBtn_ClearCache = new javax.swing.JButton();
        jBtn_ClearCache.addActionListener(e ->
                    m_browser.engine().httpCache().clearDiskCache(() ->{
//                            String strUserDir = ((JXBrowser)getParent().getParent().getParent().getParent()).getUserDir();
//                            boolean isDelete = deleteDir(new File(strUserDir + "/Default/Cache"));
//                            if (isDelete)
                                JOptionPane.showMessageDialog(null, "Cache has been cleared");
//                            else
//                                JOptionPane.showMessageDialog(null, "Error");
                        }
                    ));
        
        jBtn_Print = new javax.swing.JButton();
        jBtn_Print.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {     
//                m_browser.mainFrame().ifPresent(mainFrame -> mainFrame.executeJavaScript("window.print()"));
                m_browser.mainFrame().ifPresent(mainFrame -> mainFrame.print());
            }
        });
        
        
        jBtn_Zoomin = new javax.swing.JButton();
        jBtn_Zoomin.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_browser.zoom().out();
            }
        });
        
        
        jLbl_ZoomState = new javax.swing.JLabel();
//        Border blackline = BorderFactory.createLineBorder(Color.black);
//        jLbl_ZoomState.setBorder(blackline);
        
        
        jLbl_ZoomState.setText(String.valueOf((int)(m_browser.zoom().level().value()) * 100) + "%");
        jLbl_ZoomState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ZoomLevels levels = m_browser.engine().zoomLevels();
        levels.on(ZoomLevelChanged.class, event ->
                jLbl_ZoomState.setText(String.valueOf((int)(event.level().value() * 100)) + "%"));
        
        jBtn_ZoomOut = new javax.swing.JButton();
        jBtn_ZoomOut.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_browser.zoom().in();
            }
        });
        
//        m_browser.navigation().on(FrameLoadFinished.class, event -> {
//            if (event.frame().isMain()) {
//                ZoomLevel level = m_browser.zoom().level().of(Integer.parseInt(jLbl_ZoomState.getText().replace("%", ""))/100);
//                m_browser.zoom().level(level);
//            }
//        });

//        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jBtn_NewTab.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_NewTab.setText("New Tab");

        jBtn_ClearCache.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_ClearCache.setText("Clear Browser Cache");
        
        jBtn_Print.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Print.setText("Print");

        jBtn_Zoomin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Zoomin.setText("-");

        jLbl_ZoomState.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jBtn_ZoomOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_ZoomOut.setText("+");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtn_ClearCache, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtn_NewTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtn_Zoomin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLbl_ZoomState, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_ZoomOut))
                    .addComponent(jBtn_Print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtn_NewTab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtn_ClearCache, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Zoomin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLbl_ZoomState, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_ZoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtn_Print, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(414, Short.MAX_VALUE))
        );
        
        
//        PopupFactory pf = new PopupFactory(); 
//         // create a popup 
//        p = pf.getPopup(this, p2, 180, 100); 
    }
}
