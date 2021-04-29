/*
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>. Also(warning!): 1)You are not allowed to sell this product to third party. 2)You can't change license and made it
 * like you are the owner,author etc. 3)All redistributions of source code files must contain all copyright notices that are currently in this file,
 * and this list of conditions without modification.
 */

package com.javafxwebbrowser.application;

import com.javafxwebbrowser.browser.WebBrowserController;
import com.javafxwebbrowser.tools.InfoTool;
import com.MultiBrowse.customcontrol.ProxyAuthenticator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.net.Authenticator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * From here you start the application
 * 
 * @author GOXR3PLUS STUDIO ( your bro! )
 *
 */
public class JFXBrowser extends JPanel {
	
	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	JFXPanel fxpanel = new JFXPanel();
        String m_strProxyIp;
        String m_strProxyPort;
        String m_strProxyUserName;
        String m_strProxyPW;
        public JFXBrowser(String strProxy, String strProxyPort, String strProxyUserName, String strProxyPassword){
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
            m_strProxyIp = strProxy;
            m_strProxyPort = strProxyPort;
            m_strProxyUserName = strProxyUserName;
            m_strProxyPW = strProxyPassword;
                    
            initAndShowGUI();
        }
        
        private void initAndShowGUI() {
        // This method is invoked on the EDT thread
            final JFXPanel fxPanel = new JFXPanel();
            setLayout(new BorderLayout());
            add(fxPanel, BorderLayout.CENTER);
        
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    initFX(fxPanel);
                }
           });
        }
        
         private void initFX(JFXPanel fxPanel) {
            // This method is invoked on the JavaFX thread
            Scene scene = createScene();
            fxPanel.setScene(scene);
        }
        
	public Scene createScene() {
		
		//Root
		BorderPane root = new BorderPane();
		root.setCenter(new WebBrowserController(m_strProxyIp, m_strProxyPort, m_strProxyUserName, m_strProxyPW));
                
		//Scene
		Scene scene = new Scene(root, getVisualScreenWidth()/1.2, getVisualScreenHeight()/1.2);
		
                
                return scene;
		
	}
	
	/**
	 * Gets the visual screen width.
	 *
	 * @return The screen <b>Width</b> based on the <b>visual bounds</b> of the Screen.These bounds account for objects in the native windowing system
	 *         such as task bars and menu bars. These bounds are contained by Screen.bounds.
	 */
	public static double getVisualScreenWidth() {
		return Screen.getPrimary().getVisualBounds().getWidth() - 300;
	}
	
	/**
	 * Gets the visual screen height.
	 *
	 * @return The screen <b>Height</b> based on the <b>visual bounds</b> of the Screen.These bounds account for objects in the native windowing system
	 *         such as task bars and menu bars. These bounds are contained by Screen.bounds.
	 */
	public static double getVisualScreenHeight() {
		return Screen.getPrimary().getVisualBounds().getHeight();
	}
	
}
