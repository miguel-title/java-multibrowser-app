/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jxbrowser;

import com.MultiBrowse.customcontrol.ChildTabbedPanewithclosebutton;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.customcontrol.objBookmarks;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.OpenPopupCallback;
import com.teamdev.jxbrowser.browser.callback.OpenPopupCallback.Response;
import com.teamdev.jxbrowser.browser.callback.PrintCallback;
import com.teamdev.jxbrowser.browser.callback.StartDownloadCallback;
import com.teamdev.jxbrowser.browser.callback.input.MoveMouseWheelCallback;
import com.teamdev.jxbrowser.browser.event.FaviconChanged;
import com.teamdev.jxbrowser.download.Download;
import com.teamdev.jxbrowser.download.DownloadTarget;
import com.teamdev.jxbrowser.download.event.DownloadCanceled;
import com.teamdev.jxbrowser.download.event.DownloadFinished;
import com.teamdev.jxbrowser.download.event.DownloadInterruptReason;
import com.teamdev.jxbrowser.download.event.DownloadInterrupted;
import com.teamdev.jxbrowser.download.event.DownloadPaused;
import com.teamdev.jxbrowser.download.event.DownloadUpdated;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.navigation.event.FrameLoadFinished;
import com.teamdev.jxbrowser.navigation.event.LoadFinished;
import com.teamdev.jxbrowser.navigation.event.LoadStarted;
import com.teamdev.jxbrowser.navigation.event.NavigationFinished;
import com.teamdev.jxbrowser.navigation.event.NavigationStarted;
import com.teamdev.jxbrowser.net.NetError;
import com.teamdev.jxbrowser.ui.Bitmap;
import com.teamdev.jxbrowser.ui.KeyModifiers;
import com.teamdev.jxbrowser.ui.event.MouseWheel;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import com.teamdev.jxbrowser.view.swing.graphics.BitmapImage;
import com.teamdev.jxbrowser.zoom.ZoomLevel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * Every per Tab
 */
public class JXTabControl extends JPanel {
    private Browser browser;
    FileDownloadBox downloadBox;
    private BrowserView view;
    String strUserDir;
    int m_nProfileId;
    Image favIcon;
    
    private static final String COMMIT_ACTION = "commit";
    LocalDBConnect dbCon = new LocalDBConnect();
    public JXTabControl(Engine engine, String UserDir, int nProfileId){
        strUserDir = UserDir;
        browser = engine.newBrowser();
        m_nProfileId = nProfileId;
        
        initComponents();
        
        initBrowser();
        initSearchBar();
        
        initBookmark();
        
        
    }
    
    private void initBookmark(){
        
//        Border blackline = BorderFactory.createLineBorder(Color.black);
//        jPan_bookmark.setBorder(blackline);
        
        ArrayList<objBookmarks> lstBookmarks = dbCon.getBookmarks(m_nProfileId);
        if (lstBookmarks.size() == 0)
            jPan_bookmark.hide();
        else
            jPan_bookmark.show();
        
        
        int nBookmarkSize = lstBookmarks.size();
        jPan_bookmark.removeAll();
        jPan_bookmark.setLayout(new FlowLayout(FlowLayout.LEFT));
                
        for (int i = 0; i < nBookmarkSize; i ++){
            String Path = lstBookmarks.get(i).strFavIconPath;
            Icon favIcon = new ImageIcon(Path);
            
            JButton button = new JButton(lstBookmarks.get(i).strBookmarkName, favIcon);
            
            button.setBorderPainted(false);
//            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        
            button.setPreferredSize(new Dimension(100, 20));
//            button.setToolTipText(lstBookmarks.get(i).strBookmarkName + "\n" + lstBookmarks.get(i).strBookmarkUrl);
            button.setToolTipText("<html>" + lstBookmarks.get(i).strBookmarkName + "<br>" + lstBookmarks.get(i).strBookmarkUrl + "</html>");                      
            
            jPan_bookmark.add(button);
            
            //set action listeners for buttons
            button.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jbtn_bookmarkActionPerformed(evt);
                }
            });

            // define a custom short action command for the button
            button.setActionCommand(Integer.toString(lstBookmarks.get(i).id));
            
            final JPopupMenu popup = new JPopupMenu();
            
            JMenuItem item = new JMenuItem("delete");
            item.setActionCommand(Integer.toString(lstBookmarks.get(i).id));
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    jdelete_actionPerformed(ev);
                }
            });
//            popup.add(new JMenuItem(new AbstractAction(Integer.toString(lstBookmarks.get(i).id)) {
//                public void actionPerformed(ActionEvent e) {
//                    jdelete_actionPerformed(e);
//                }
//            }));
            popup.add(item);
            
            button.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    if (me.getButton() == MouseEvent.BUTTON3)
                        popup.show(me.getComponent(), me.getX(), me.getY());
                }
            }) ;
        }
        
        repaint();
        revalidate();
        
    }
    
    public void jdelete_actionPerformed(ActionEvent evt){
        String action = evt.getActionCommand();
        dbCon.deleteBookmark(action);
        
        initBookmark();
    }
    
    
    public void jbtn_bookmarkActionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        
        String url = dbCon.getBookmarkUrl(action);
        
        JXBrowser parentBrowser = (JXBrowser)getParent().getParent();

        Container contain = jLbl_state.getParent().getParent();
        ChildTabbedPanewithclosebutton editorTabbedPane =(ChildTabbedPanewithclosebutton) contain;
        int nIndex = editorTabbedPane.indexOfComponent(jLbl_state.getParent());

        String strProxy = parentBrowser.m_strProxy;
        String strProxyPort = parentBrowser.m_strProxyPort;
        String strProxyUserName = parentBrowser.m_strProxyUserName;
        String strProxyPassword = parentBrowser.m_strProxyPassword;
        JXTabControl jxTabControl = parentBrowser.insertTab(nIndex, "", strProxy, strProxyPort, strProxyUserName, strProxyPassword, browser, 1);
            
        parentBrowser.setURL(url, jxTabControl);
    }
    
    private void initSearchBar(){
        Map<String, String> histroyUrls = new Hashtable(); 
        
        histroyUrls = getHistoryUrls();
        
        setupAutoComplete(jTxt_search, histroyUrls);
    }
    
    public void setButtonDisable(){
        jbtn_back.setEnabled(false);
        jbtn_forward.setEnabled(false);
    }
    
    private void initBrowser(){
        pageRender("about:blank"); 
        
//        Border blackline = BorderFactory.createLineBorder(Color.black);
//        jpan_search.setBorder(blackline);
        
        SwingUtilities.invokeLater(() -> {
            view = BrowserView.newInstance(browser);
            view.dragAndDrop().enable();
            
            jPan_view.setLayout(new BorderLayout());
            jPan_view.add(view, BorderLayout.CENTER);
            
            initMenu();
            
//            Border blackline = BorderFactory.createLineBorder(Color.black);
//            jPan_view.setBorder(blackline);
        });
        
        setButtonDisable();
        
        browser.zoom().enable();
        
        browser.set(MoveMouseWheelCallback.class, new MoveMouseWheelCallback() {

            public MoveMouseWheelCallback.Response on(MoveMouseWheelCallback.Params params) {
                MouseWheel e = params.event();
                KeyModifiers key = e.keyModifiers();
                if (key.isControlDown()) {
                    double zoomValue = browser.zoom().level().value();
                    if (e.deltaY() < 0) {
                        zoomValue -= 0.1;
                        browser.zoom().level(browser.zoom().level().of(zoomValue));

                    } else {
                        zoomValue += 0.1;
                        browser.zoom().level(browser.zoom().level().of(zoomValue));
                    }
                } 
                return Response.proceed();
            }
        });
        
//        browser.set(PrintCallback.class, (params, tell) -> tell.showPrintPreview());
//        browser.set(PrintCallback.class, (params, tell) -> tell.cancel());

        browser.set(OpenPopupCallback.class, (params) -> {
            // Access the created popup.
            Browser popup = params.popupBrowser();
            JXBrowser parentBrowser = (JXBrowser)getParent().getParent();
            
            Container contain = jLbl_state.getParent().getParent();
            ChildTabbedPanewithclosebutton editorTabbedPane =(ChildTabbedPanewithclosebutton) contain;
            int nIndex = editorTabbedPane.indexOfComponent(jLbl_state.getParent());
            
            String strProxy = parentBrowser.m_strProxy;
            String strProxyPort = parentBrowser.m_strProxyPort;
            String strProxyUserName = parentBrowser.m_strProxyUserName;
            String strProxyPassword = parentBrowser.m_strProxyPassword;
            JXTabControl jxTabControl = parentBrowser.insertTab(nIndex, "", strProxy, strProxyPort, strProxyUserName, strProxyPassword, null, 0);
            
            popup.navigation().on(NavigationStarted.class, event -> {                                
                String strURL = popup.url();
                parentBrowser.setURL(strURL, jxTabControl);
                
            });
            
            return Response.proceed();
        });
        
        
        browser.on(FaviconChanged.class, event -> {
            Container contain = jLbl_state.getParent().getParent();
            ChildTabbedPanewithclosebutton editorTabbedPane =(ChildTabbedPanewithclosebutton) contain;
            if (editorTabbedPane!=null) {
                
                String strTabTitle = "New Tab";
                if (!browser.url().equals("about:blank"))
//                    strTabTitle = browser.url();
                    strTabTitle = browser.title();
//                editorTabbedPane.setTitleAt(editorTabbedPane.indexOfComponent(jLbl_state.getParent()), strTabTitle);
                int nIndex = editorTabbedPane.indexOfComponent(jLbl_state.getParent());
                
                
                Bitmap bitmap = event.favicon();
                
                Image bufferedImage =  BitmapImage.toToolkit(bitmap);
                Image newimg = bufferedImage.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
                Icon icon = new ImageIcon(newimg);
                favIcon = newimg;
                
                editorTabbedPane.setTitleAt(nIndex, strTabTitle, jLbl_state.getParent(), icon);
                editorTabbedPane.setToolTipTextAt(nIndex, strTabTitle);
            }
        });
        
//        browser.navigation().on(FrameLoadFinished.class, event -> {
//            if (event.frame().isMain()) {
//                browser.zoom().level(ZoomLevel.P_100);
//            }
//        });
        
        
        
        browser.navigation().on(NavigationFinished.class, event -> {
            boolean hasCommitted = event.hasCommitted();
            boolean isSameDocument = event.isSameDocument();
            boolean isErrorPage = event.isErrorPage();
            if (isErrorPage) {
                NetError error = event.error();
                System.out.println(error.toString());
            }
                        
            String strUrl = browser.url();
            if (browser.url().equals("about:blank"))
                jTxt_search.setText("");
            else
                jTxt_search.setText(browser.url());
            
            int nCurIndex = browser.navigation().currentEntryIndex();
            if (nCurIndex == 0)
                jbtn_back.setEnabled(false);
            else
                jbtn_back.setEnabled(true);
            
            int nAllIndexes = browser.navigation().entryCount();
            if (nCurIndex == nAllIndexes - 1)
                jbtn_forward.setEnabled(false);
            else
                jbtn_forward.setEnabled(true);
            
            Container contain = jLbl_state.getParent().getParent();
            ChildTabbedPanewithclosebutton editorTabbedPane =(ChildTabbedPanewithclosebutton) contain;
            if (editorTabbedPane!=null) {
                
                String strTabTitle = "New Tab";
                if (!browser.url().equals("about:blank")){
//                    strTabTitle = browser.url();
                    strTabTitle = browser.title();
                }
//                editorTabbedPane.setTitleAt(editorTabbedPane.indexOfComponent(jLbl_state.getParent()), strTabTitle);
                int nIndex = editorTabbedPane.indexOfComponent(jLbl_state.getParent());
                
                Bitmap bitmap = browser.favicon();
                
                Image bufferedImage =  BitmapImage.toToolkit(bitmap);
                Image newimg = bufferedImage.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
                Icon icon = new ImageIcon(newimg);
                
                editorTabbedPane.setTitleAt(nIndex, strTabTitle, jLbl_state.getParent(), icon);
                editorTabbedPane.setToolTipTextAt(nIndex, strTabTitle);
            }
            
        });
        
        
        
        browser.navigation().on(LoadStarted.class, event -> {
            jLbl_state.setText("Loading...");              
            jProgress_state.setIndeterminate(true);
        });
              
        
        browser.navigation().on(LoadFinished.class, event -> {        
            jLbl_state.setText("Done"); 
            jProgress_state.setIndeterminate(false);

            if (jTxt_search.getText().isEmpty())
                jTxt_search.requestFocus();
            
            
            try {
                if (!browser.title().equals("about:blank"))
                    writeToFile(browser.title() + "::" + browser.url());
            } catch (IOException ex) {
                Logger.getLogger(JXTabControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            initSearchBar();
            
        });   
        
        browser.set(StartDownloadCallback.class, (params, tell) -> {
            
            Download download = params.download();
            // Download target details.
            DownloadTarget downloadTarget = download.target();
            String strDownloadPath = createDownloadDir();
            // Tell the engine to download and save the file.
            tell.download(Paths.get(strDownloadPath + "/" + downloadTarget.suggestedFileName()));
        
            FileDownloadBox downloadBox = new FileDownloadBox(download, downloadTarget);
            downloadBox.setLocationRelativeTo(this);
            downloadBox.show();
       
        });
        
        jSeparator1.setVisible(false);
        
    }
    
    private Map<String, String> getBookmarkUrls(){
        Map<String, String> mpBookmark = new Hashtable(); 
        
        String strHistoryUrls = strUserDir + "/BookmarkUrls";
        File f = new File(strHistoryUrls);
        try{
            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            System.out.println("Reading file using Buffered Reader");

            while ((readLine = b.readLine()) != null) {
//                System.out.println(readLine);
                if (readLine.split("::").length > 1)
                    mpBookmark.put(readLine.split("::")[0], readLine.split("::")[1]);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return mpBookmark;
    }
    
    private Map<String, String> getHistoryUrls(){
        Map<String, String> mpHistory = new Hashtable(); 
        
        String strHistoryUrls = strUserDir + "/HistoryUrls";
        File f = new File(strHistoryUrls);
        try{
                        

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            System.out.println("Reading file using Buffered Reader");

            while ((readLine = b.readLine()) != null) {
//                System.out.println(readLine);
                if (readLine.split("::").length > 1)
                    mpHistory.put(readLine.split("::")[0], readLine.split("::")[1]);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return mpHistory;
    }
    
    
    private void writeToFile(String list) throws IOException{
///     
        
        JXBrowser parentBrowser = (JXBrowser)jLbl_state.getParent().getParent().getParent();
        String strUserDataDir = parentBrowser.getUserDir();
        String strHistoryUrls = strUserDataDir + "/HistoryUrls";
        
        File f = new File(strHistoryUrls);
        
//        System.out.println(f);
       
        if (list.contains("https://www.google.com/sorry/index"))
            return;
        FileWriter fw = new FileWriter(f,true);
//        System.out.println(fw);
        try{
            BufferedWriter bw = new BufferedWriter(fw);
//            System.out.println(bw);
            bw.write(list);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
          ///
    }
    
    private String createDownloadDir(){
        File theDir = new File("downloads");

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getName());
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            } 
            catch(SecurityException se){
                //handle it
            }        
            if(result) {    
                System.out.println("DIR created");  
            }
        }
        
        return theDir.getAbsolutePath();
    }
        
    
    public void loadURL(String strURL){
        if (strURL.equals("about:blank"))
            jTxt_search.setText("");
        else
            jTxt_search.setText(strURL);
        

//        Pattern basicPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
        Pattern basicPattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher basicMatch = basicPattern.matcher(strURL);
        boolean isBasicValidate = basicMatch.matches();
        if (isBasicValidate){            
            pageRender(strURL);
            return;
        }

        Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");  
        Matcher m = p.matcher(strURL); 
        boolean isValidate = m.matches();
        if(isValidate)
            pageRender(strURL);
        else
            pageRender("https://www.google.com.pk/search?q=" + jTxt_search.getText());

    }
    
    public void pageRender(String url) {        
        browser.navigation().loadUrl(url);
    }
    
    
    private void searchKeyPress(){
        String strUrl = jTxt_search.getText();
        Pattern basicPattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher basicMatch = basicPattern.matcher(strUrl);
        boolean isBasicValidate = basicMatch.matches();
        if (isBasicValidate){            
            pageRender(strUrl);
            return;
        }
            
        Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");  
        Matcher m = p.matcher(strUrl); 
        boolean isValidate = m.matches();
        
        if(isValidate)
            pageRender(strUrl);
        else
            pageRender("https://www.google.com.pk/search?q=" + jTxt_search.getText());
    }
    
    
    private void jTxt_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxt_searchKeyPressed
    // TODO add your handling code here:
    String strUrl = jTxt_search.getText();
    if (strUrl.isEmpty())
        return;
    int key = evt.getKeyCode();
    
//    Pattern basicPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
    Pattern basicPattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    Matcher basicMatch = basicPattern.matcher(strUrl);
    boolean isBasicValidate = basicMatch.matches();
    if (key == KeyEvent.VK_ENTER){
//        jPan_view.requestFocus();
        if (isBasicValidate){            
            pageRender(strUrl);
            return;
        }
    }
    
    Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");  
    Matcher m = p.matcher(strUrl); 
    boolean isValidate = m.matches();
    if (key == KeyEvent.VK_ENTER) {
        if(isValidate)
            pageRender(strUrl);
        else
            pageRender("https://www.google.com.pk/search?q=" + jTxt_search.getText());
        
        
    }

    }//GEN-LAST:event_jTxt_searchKeyPressed
        
        /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtn_back = new javax.swing.JButton();
        jbtn_forward = new javax.swing.JButton();
        jbtn_refresh = new javax.swing.JButton();
        jTxt_search = new BrowserSearchTextField(50);
//        jTxt_search = new JTextField();
        jTxt_search.setFocusTraversalKeysEnabled(false);
        
        
//        jTxt_search = new javax.swing.JTextField();
        jPan_view = new javax.swing.JPanel();
        jPan_bookmark = new javax.swing.JPanel();
        jProgress_state = new javax.swing.JProgressBar();
        jLbl_state = new javax.swing.JLabel();
        
        jpan_search = new javax.swing.JPanel();
//        {
//             @Override
//            protected void paintComponent(Graphics g) {
//               Dimension arcs = new Dimension(25,25); //Border corners arcs {width,height}, change this to whatever you want
//               int width = getWidth();
//               int height = getHeight();               
//               
//               // Prepare a red rectangle
//                BufferedImage bi = new BufferedImage(width-1, height-1, BufferedImage.TYPE_INT_ARGB);
//                Graphics2D gb = bi.createGraphics();
//                gb.setPaint(Color.WHITE);
//                gb.fillRect(0, 0, width-1, height-1);
//                gb.dispose();
//
//                // Set a rounded clipping region:
//                RoundRectangle2D r = new RoundRectangle2D.Float(0, 0, width-1, height-1, arcs.width, arcs.height);
//                g.setClip(r);
//                // Draw the rectangle (and see whether it has round corners)
//                g.drawImage(bi, 0, 0, null);
//
//            }
//        };
        
        jSeparator1 = new javax.swing.JSeparator();
        jlbl_navigation = new javax.swing.JLabel();
        jbtn_menu = new javax.swing.JButton();
        jbtn_favorite = new javax.swing.JButton();
        
        jbtn_searchIcon = new javax.swing.JButton();
        jbtn_searchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        
        jbtn_searchIcon.setMaximumSize(new java.awt.Dimension(30, 30));
        jbtn_searchIcon.setMinimumSize(new java.awt.Dimension(30, 30));
        jbtn_searchIcon.setPreferredSize(new java.awt.Dimension(30, 30));
        
        jbtn_searchIcon.setBorderPainted(false);
        jbtn_searchIcon.setFocusPainted(false);
        jbtn_searchIcon.setContentAreaFilled(false);    
                
        jbtn_menu.setBorderPainted(false);
        jbtn_menu.setFocusPainted(false);
        jbtn_menu.setContentAreaFilled(false);
        
        
        jbtn_favorite.setBorderPainted(false);
        jbtn_favorite.setFocusPainted(false);
        jbtn_favorite.setContentAreaFilled(false);

        jTxt_search.setPlaceHolder("Search Google or type a URL");
        jTxt_search.setForeground(Color.BLACK);
        Font font = new Font("TimesRoman", Font.PLAIN, 14);
        jTxt_search.setFont(font);
        
        
        jbtn_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu.png"))); // NOI18N
        jbtn_menu.setMaximumSize(new java.awt.Dimension(30, 30));
        jbtn_menu.setMinimumSize(new java.awt.Dimension(30, 30));
        jbtn_menu.setPreferredSize(new java.awt.Dimension(30, 30));
        jbtn_menu.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_menuActionPerformed(evt);
            }
        });
        
        
        jbtn_favorite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bookmark.png"))); // NOI18N
        jbtn_favorite.setMaximumSize(new java.awt.Dimension(30, 30));
        jbtn_favorite.setMinimumSize(new java.awt.Dimension(30, 30));
        jbtn_favorite.setPreferredSize(new java.awt.Dimension(30, 30));
        jbtn_favorite.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_favoriteActionPerformed(evt);
            }
        });
        
        
       
        
        jbtn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backword.png"))); // NOI18N
        jbtn_back.setMaximumSize(new java.awt.Dimension(30, 30));
        jbtn_back.setMinimumSize(new java.awt.Dimension(30, 30));
        jbtn_back.setPreferredSize(new java.awt.Dimension(30, 30));
        jbtn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_backActionPerformed(evt);
            }
        });
        

        jbtn_forward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forward.png"))); // NOI18N
        jbtn_forward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_forwardActionPerformed(evt);
            }
        });

        jbtn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/refresh.png"))); // NOI18N
        jbtn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_refreshActionPerformed(evt);
            }
        });

        jTxt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
//                jTxt_searchKeyPressed(evt);
            }
        });       
        
        jPan_bookmark.setPreferredSize(new java.awt.Dimension(2, 30));
        jPan_bookmark.setMinimumSize(new java.awt.Dimension(2, 30));
        
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        
        BorderLayout jPan_searchLayout = new BorderLayout();
        jpan_search.setLayout(jPan_searchLayout);
        
        jpan_search.setBackground(Color.white);
        jpan_search.add(jbtn_searchIcon, BorderLayout.WEST);
        jpan_search.add(jTxt_search, BorderLayout.CENTER);
        jpan_search.add(jbtn_favorite, BorderLayout.EAST);
        
        
        jTxt_search.setBorder(null);
        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn_forward, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpan_search)
                        .addComponent(jbtn_menu))
                    .addComponent(jPan_bookmark, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPan_view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProgress_state, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLbl_state, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlbl_navigation, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtn_forward, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpan_search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtn_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPan_bookmark, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPan_view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jProgress_state, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLbl_state, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jlbl_navigation, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        
        
        
    }// </editor-fold>//GEN-END:initComponents
        
    private void initMenu(){
        m_settingMenu = new PopupMenu(browser, view);   
        jPan_view.add(m_settingMenu, BorderLayout.EAST);
        m_settingMenu.setVisible(false);
    }
    
    private boolean isMenuVisible = false;
    
    public void setMenuVisible(boolean isVisible){
        m_settingMenu.setVisible(isVisible);
        isMenuVisible = isVisible;
    }
    
    private void jbtn_favoriteActionPerformed(java.awt.event.ActionEvent evt){
        if (browser.url().equals("about:blank"))
            return;
        
        JXBrowser parentBrowser = (JXBrowser)jLbl_state.getParent().getParent().getParent();
        String strUserDataDir = parentBrowser.getUserDir();
        String strBookmarkUrls = strUserDataDir + "/BookmarkUrls";
        
        EditBookmark editWindow = new EditBookmark(null, true, browser.title(), browser.url(), strBookmarkUrls, m_nProfileId, favIcon, strUserDir);
        
        editWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        initBookmark();
                    }
                });
        
        editWindow.setLocationRelativeTo(this);
        editWindow.show();
    }
    
    private void jbtn_menuActionPerformed(java.awt.event.ActionEvent evt){

        // if selected print selected in console 
       
            // Get the event source
            Component b=(Component)evt.getSource();

            // Get the location of the point 'on the screen'
            Point p=b.getLocationOnScreen();

            // Show the JPopupMenu via program

            // Parameter desc
            // ----------------
            // this - represents current frame
            // 0,0 is the co ordinate where the popup
            // is shown
//            m_settingMenu.show();
//            m_settingMenu.setSize(new Dimension(280, 500));
            // Now set the location of the JPopupMenu
            // This location is relative to the screen
//            m_settingMenu.setLocation(p.x - m_settingMenu.getWidth() + b.getWidth() + 10, p.y + b.getHeight());
            
            
//            PopupFactory pf = new PopupFactory(); 
//            Popup popup = pf.getPopup(this, m_settingMenu, p.x - m_settingMenu.getWidth() + b.getWidth() + 10, p.y + b.getHeight());
//            popup.show();
            isMenuVisible = !isMenuVisible;
            m_settingMenu.setVisible(isMenuVisible);
    }
    
    private void jbtn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_backActionPerformed
        // TODO add your handling code here:
        
        if (browser.navigation().canGoBack())
            browser.navigation().goBack();
        int nCurIndex = browser.navigation().currentEntryIndex();
        if (nCurIndex == 0)
            jbtn_back.setEnabled(false);
        else
            jbtn_back.setEnabled(true);
    }//GEN-LAST:event_jbtn_backActionPerformed

    private void jbtn_forwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_forwardActionPerformed
        // TODO add your handling code here:
        
        if (browser.navigation().canGoForward())
            browser.navigation().goForward();
        int nCurIndex = browser.navigation().currentEntryIndex();
        int nAllIndexes = browser.navigation().entryCount();
        if (nCurIndex == nAllIndexes - 1)
            jbtn_forward.setEnabled(false);
        else
            jbtn_forward.setEnabled(true);
    }//GEN-LAST:event_jbtn_forwardActionPerformed

    private void jbtn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_refreshActionPerformed
        // TODO add your handling code here:
        browser.navigation().reload();
    }//GEN-LAST:event_jbtn_refreshActionPerformed
    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLbl_state;
    private javax.swing.JPanel jPan_view;
    private javax.swing.JPanel jPan_bookmark;
    private javax.swing.JProgressBar jProgress_state;
    private javax.swing.JSeparator jSeparator1;
    private BrowserSearchTextField jTxt_search;
//    private javax.swing.JTextField jTxt_search;
    private javax.swing.JButton jbtn_back;
    private javax.swing.JButton jbtn_forward;
    private javax.swing.JButton jbtn_refresh;
    private javax.swing.JLabel jlbl_navigation;
    
    private javax.swing.JPanel jpan_search;
    // End of variables declaration//GEN-END:variables
    
    private javax.swing.JButton jbtn_menu;
    private javax.swing.JButton jbtn_favorite;
    private javax.swing.JButton jbtn_searchIcon;
    
    private PopupMenu m_settingMenu;
    
    
    
    private static boolean isAdjusting(JComboBox cbInput) {
        if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
            return (Boolean) cbInput.getClientProperty("is_adjusting");
        }
        return false;
    }

    private static void setAdjusting(JComboBox cbInput, boolean adjusting) {
        cbInput.putClientProperty("is_adjusting", adjusting);
    }

    public void setupAutoComplete(final JTextField txtInput, final Map<String, String> items) {
        final DefaultComboBoxModel model = new DefaultComboBoxModel();
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        
        final JComboBox cbInput = new JComboBox(model) {
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 0);
            }
        };
        setAdjusting(cbInput, false);
        Set set2 = items.entrySet();
        Iterator iterator2 = set2.iterator();
        while(iterator2.hasNext()) {
            Map.Entry mentry2 = (Map.Entry)iterator2.next();
            model.addElement(mentry2.getKey());
         }
        cbInput.setSelectedItem(null);
        cbInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAdjusting(cbInput)) {
                    if (cbInput.getSelectedItem() != null) {
                        String strSearchText = (String) items.get(cbInput.getSelectedItem().toString());
                        txtInput.setText((String) items.get(cbInput.getSelectedItem().toString()));
                        System.out.print("\nadfadsf::::::" + strSearchText);
                    }
                }
            }
        });

        txtInput.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                setAdjusting(cbInput, true);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (cbInput.isPopupVisible()) {
                        e.setKeyCode(KeyEvent.VK_ENTER);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.setSource(cbInput);
                    cbInput.dispatchEvent(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (cbInput.getSelectedItem() != null){
                            String s = items.get(cbInput.getSelectedItem().toString());
                            System.out.print("enter:" + s);
                            txtInput.setText(s);
                            
                        }
                        
                        searchKeyPress();
                        cbInput.setPopupVisible(false);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cbInput.setPopupVisible(false);
                }
                setAdjusting(cbInput, false);
            }
        });
        txtInput.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateList();
            }

            public void removeUpdate(DocumentEvent e) {
                updateList();
            }

            public void changedUpdate(DocumentEvent e) {
                updateList();
            }

            private void updateList() {
                setAdjusting(cbInput, true);
                model.removeAllElements();
                String input = txtInput.getText();
                
                Set set2 = items.entrySet();
                Iterator iterator2 = set2.iterator();
                if (!input.isEmpty()) {
                    while(iterator2.hasNext()) {
                        Map.Entry mentry2 = (Map.Entry)iterator2.next();
                        if (mentry2.getKey().toString().toLowerCase().contains(input.toLowerCase())){
                            model.addElement(mentry2.getKey());
                        }
                     }
                }
                cbInput.setPopupVisible(model.getSize() > 0);
                setAdjusting(cbInput, false);
            }
        });
        txtInput.setLayout(new BorderLayout());
        txtInput.add(cbInput, BorderLayout.SOUTH);
    }
}