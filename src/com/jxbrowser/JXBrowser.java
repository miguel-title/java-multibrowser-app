/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jxbrowser;

import com.MultiBrowse.customcontrol.ChildTabbedPanewithclosebutton;
import com.MultiBrowse.customcontrol.ProxyAuthenticator;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.input.PressKeyCallback;
import com.teamdev.jxbrowser.callback.Callback;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN;
import com.teamdev.jxbrowser.net.callback.AuthenticateCallback;
import com.teamdev.jxbrowser.net.proxy.CustomProxyConfig;
import com.teamdev.jxbrowser.net.proxy.Proxy;
import com.teamdev.jxbrowser.net.proxy.SystemProxyConfig;
import com.teamdev.jxbrowser.plugin.Plugin;
import com.teamdev.jxbrowser.plugin.Plugins;
import com.teamdev.jxbrowser.zoom.ZoomLevel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * Every per profile
 */
public class JXBrowser extends JPanel {

    /**
     * Creates new form JXBrowser
     */
    String m_strProxy;
    String m_strProxyPort;
    String m_strProxyUserName;
    String m_strProxyPassword;
    int m_nProfileId;
    
    String m_strUserAgent;
    
    ChildTabbedPanewithclosebutton m_tabPane;
    
    public JXBrowser(String strProxy, String strProxyPort, String strProxyUserName, String strProxyPassword, int nProfileId, String strUserAgent) {
        m_strProxy = strProxy;
        m_strProxyPort = strProxyPort;
        m_strProxyUserName = strProxyUserName;
        m_strProxyPassword = strProxyPassword;
        m_nProfileId = nProfileId;
        
        m_strUserAgent = strUserAgent;
                
        
        Color activeColor = new Color(238, 238, 238);
        Color borderColor = new Color(206, 206, 206);
        
        UIManager.put("TabbedPane.selected", activeColor);
        UIManager.put("TabbedPane.contentAreaColor", borderColor);
        
        initBrowser();
                
    }
    
    
    private void setProxy(){
        if (m_strProxy.isEmpty())
            return;
        engine.network().set(AuthenticateCallback.class, (params, tell) -> tell.authenticate(m_strProxyUserName, m_strProxyPassword));
        
        String proxyRules = m_strProxy + ":" + m_strProxyPort;
        String exceptions = "<local>";  // bypass proxy server for local web pages
        proxy = engine.proxy();
        
        exceptions = "<local>";
        
        proxy.config(CustomProxyConfig.newInstance(proxyRules, exceptions));
    }

    Engine engine;
    Proxy proxy;
    OffsetTabbedPaneUI tabUI;
            
    public Engine getEngine(){
        return engine;
    }
    
    public String getUserDir(){
        String strUserDataDir = String.valueOf(m_nProfileId) + ":" + m_strProxy + ":" + m_strProxyPort + ":" + m_strProxyUserName + ":" + m_strProxyPassword;
                
        Encoder encoder = Base64.getUrlEncoder();
        String strProjectPath = System.getProperty("user.dir");
        String strCachePath = strProjectPath + "/User Data/";
        String encodedPath = encoder.encodeToString(strUserDataDir.getBytes());
        
        return strCachePath + encodedPath;
    }
    
    private void initBrowser(){
        System.setProperty("jxbrowser.license.key", "1BNDHFSC1FVW471XYMWNJRCJXT5D4BII2BNWR31M7XYX82PT9M1M0SWLT4M0YKO0ZNU6W2");  
        
        //create user data with profile id + proxy + proxyport + proxyusername + proxypasswrod.
        String strUserDataDir = String.valueOf(m_nProfileId) + ":" + m_strProxy + ":" + m_strProxyPort + ":" + m_strProxyUserName + ":" + m_strProxyPassword;
                
        Encoder encoder = Base64.getUrlEncoder();
        String strProjectPath = System.getProperty("user.dir");
        String strCachePath = strProjectPath + "/User Data/";
        String encodedPath = encoder.encodeToString(strUserDataDir.getBytes());

        if (!m_strUserAgent.isEmpty())
            engine = Engine.newInstance(
                    EngineOptions.newBuilder(OFF_SCREEN)
                            .userDataDir(Paths.get(strCachePath + encodedPath))
                            .userAgent(m_strUserAgent)
                            .build());
        else
            engine = Engine.newInstance(
                    EngineOptions.newBuilder(OFF_SCREEN)
                            .userDataDir(Paths.get(strCachePath + encodedPath))
                            .build());
        
        
        setProxy();
        engine.zoomLevels().defaultLevel(ZoomLevel.P_100);
        
        Plugins plugin = engine.plugins();
        
        List<Plugin> lstPlugIns = plugin.list();
        int nPlugInCount = lstPlugIns.size();
        System.out.print("\nPlugInCount:" + nPlugInCount);
        for (int i = 0; i < nPlugInCount; i ++){
            String name = lstPlugIns.get(i).name();
            String description = lstPlugIns.get(i).description();
            String version = lstPlugIns.get(i).version();
            System.out.print("\nName:" + name + ", description:" + description + ", version:" + version);
        }
        
        JXTabControl jxTabControl = new JXTabControl(engine, getUserDir(), m_nProfileId);
        
        m_tabPane = new ChildTabbedPanewithclosebutton();
        
        Color inactiveColor = new Color(206, 206, 206);
        m_tabPane.setBackground(inactiveColor);
        
        m_tabPane.setBorder(null);
                
        tabUI = new OffsetTabbedPaneUI();
        m_tabPane.setUI(tabUI);
        
        m_tabPane.addTab("New Tab", jxTabControl);
        
        m_tabPane.addLastTab("+", null, null, "New Tab");
        
        
        m_tabPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                System.out.println("Tab: " + m_tabPane.getSelectedIndex());
                int nTabCount = m_tabPane.countComponents();    
                if (m_tabPane.getSelectedIndex() == nTabCount - 1){
                    m_tabPane.remove(nTabCount - 1);
                    JXTabControl jxNewTabControl = new JXTabControl(engine, getUserDir(), m_nProfileId);
                    m_tabPane.addTab("New Tab", null, jxNewTabControl, "");
                    m_tabPane.addLastTab("+", null, null, "New Tab");
                    m_tabPane.setSelectedIndex(m_tabPane.countComponents() - 2);
                }
                
            }
        });
        
            
        
        setLayout(new BorderLayout());
        add(m_tabPane);
        
    }
    
    
    public JXTabControl insertTab(int nIndex, String strUrl, String strProxy, String strProxyPort, String strProxyUserName, String strProxyPassword, Browser browser, int nType){
        if (nType == 0){
            if (!m_strUserAgent.isEmpty())
                engine = Engine.newInstance(
                        EngineOptions.newBuilder(OFF_SCREEN)
                                .userAgent(m_strUserAgent)
                                .build());
            else
                engine = Engine.newInstance(
                        EngineOptions.newBuilder(OFF_SCREEN)
                                .build());


            if (!strProxy.isEmpty()){
                engine.network().set(AuthenticateCallback.class, (params, tell) -> tell.authenticate(strProxyUserName, strProxyPassword));

                String proxyRules = strProxy + ":" + strProxyPort;
                String exceptions = "<local>";  // bypass proxy server for local web pages
                proxy = engine.proxy();

                exceptions = "<local>";

                proxy.config(CustomProxyConfig.newInstance(proxyRules, exceptions));
            }

            JXTabControl jxTabControl = new JXTabControl(engine, getUserDir(), m_nProfileId);
            m_tabPane.insertTab("Loading...", null, jxTabControl, "", nIndex + 1);
            m_tabPane.setSelectedIndex(nIndex + 1);

            return jxTabControl;
        }
        else{
            JXTabControl jxTabControl = new JXTabControl(browser.engine(), getUserDir(), m_nProfileId);
            m_tabPane.insertTab("Loading...", null, jxTabControl, "", nIndex + 1);
            m_tabPane.setSelectedIndex(nIndex + 1);

            return jxTabControl;
        }
    }
    
    public void setURL(String strURL, JXTabControl jxTabControl){
        jxTabControl.loadURL(strURL);
    }
       
    
}
