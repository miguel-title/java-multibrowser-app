/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.customcontrol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 * @author title
 */
public class ChildTabbedPanewithclosebutton extends JTabbedPane {
    private int m_nRemoveIndex;
    public ChildTabbedPanewithclosebutton() {
        super();
    }
    
    public int getRemovedIndex(){
        return m_nRemoveIndex;
    }

    /* Override Addtab in order to add the close Button everytime */
    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        int count = this.getTabCount() - 1;
        CloseButtonTab tab = new CloseButtonTab(component, title, icon, false);
        setTabComponentAt(count, tab);
    }
    
    public void addLastTab(String title, Icon icon, Component component, String tip){
        super.addTab(title, icon, component, tip);
        
        int count = this.getTabCount() - 1;
        setTabComponentAt(count, new CloseButtonTab(component, "+", icon, true));
    }
   
    public void setTitleAt(int index, String strTitle, Component component, Icon icon){
        super.setTitleAt(index, strTitle);
        setTabComponentAt(index, new CloseButtonTab(component, strTitle, icon, false));
    }
    
    @Override
    public void addTab(String title, Icon icon, Component component) {
        addTab(title, icon, component, null);
    }

    @Override
    public void addTab(String title, Component component) {
        addTab(title, null, component);
    }

    /* addTabNoExit */
    public void addTabNoExit(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
    }

    public void addTabNoExit(String title, Icon icon, Component component) {
        addTabNoExit(title, icon, component, null);
    }

    public void addTabNoExit(String title, Component component) {
        addTabNoExit(title, null, component);
    }

    /* Button */
    public class CloseButtonTab extends JPanel {
        private Component tab;
        public CloseButtonTab(final Component tab, String title, Icon icon, boolean isLastTab) {
            this.tab = tab;
            setOpaque(false);
//            FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
            
            BorderLayout borderLayout = new BorderLayout();
            setLayout(borderLayout);
            if (!isLastTab){
                JLabel jLabel = new JLabel(title);
                jLabel.setIcon(icon);

    //            Border blackline = BorderFactory.createLineBorder(Color.black);
    //            jLabel.setBorder(blackline);

                add(jLabel, BorderLayout.CENTER);
                JButton button = new JButton("✕");

                button.setHorizontalAlignment(SwingConstants.RIGHT);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);


                button.setMargin(new Insets(0, 0, 0, 0));
                button.addMouseListener(new CloseListener(tab));
                add(button, BorderLayout.EAST);
                
                setPreferredSize(new Dimension(150, 28)); 
                
            }
            if (isLastTab){
                JLabel jLabel = new JLabel(title);
                jLabel.setHorizontalAlignment(SwingConstants.CENTER);
                
                jLabel.setFont(new java.awt.Font("Tahoma", Font.BOLD, 18)); // NOI18N
                
                add(jLabel, BorderLayout.CENTER);
                
                setPreferredSize(new Dimension(28, 28));

            }
        }
        
        
        
    }
    /* ClickListener */
    public class CloseListener implements MouseListener
    {
        private Component tab;
        public CloseListener(Component tab){
            this.tab=tab;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();

                JTabbedPane tabbedPane = (JTabbedPane) clickedButton.getParent().getParent().getParent();
                
                int nTabCount = tabbedPane.countComponents();
                if (nTabCount == 1)
                    return;
                        
                int nCurIndex = tabbedPane.getSelectedIndex();
                m_nRemoveIndex = tabbedPane.indexOfComponent(tab);
                tabbedPane.remove(tab);
                
                if (nCurIndex == m_nRemoveIndex)
                    if (m_nRemoveIndex > tabbedPane.countComponents() - 2)
                        tabbedPane.setSelectedIndex(tabbedPane.countComponents() - 2);
                    else
                        tabbedPane.setSelectedIndex(m_nRemoveIndex);
                
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();
//                clickedButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();
//                clickedButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,3));
            }
        }
    }
}
