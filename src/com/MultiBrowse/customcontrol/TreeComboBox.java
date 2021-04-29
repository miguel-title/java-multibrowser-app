/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.customcontrol;

import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

/**
 *
 * @author title
 */

public class TreeComboBox extends JComboBox<DefaultMutableTreeNode> {
    
  private String strText;
  private String strClass;
  
  public TreeComboBox(TreeModel model) {
    super();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
    DefaultComboBoxModel<DefaultMutableTreeNode> m = new DefaultComboBoxModel<>();
    Collections.list((Enumeration<?>) root.preorderEnumeration()).stream()
        .filter(DefaultMutableTreeNode.class::isInstance)
        .map(DefaultMutableTreeNode.class::cast)
        .filter(n -> !n.isRoot())
        .forEach(m::addElement);
    setFont(new java.awt.Font("Tahoma", 0, 12));
    setModel(m);
  }

  public String getText(){
      return strText;
  }
  
  public String getProjectClass(){
      return strClass;
  }
  
  @Override
  public void updateUI() {
    super.updateUI();
    JTree dummyTree = new JTree();
//    TreeCellRenderer renderer = dummyTree.getCellRenderer();
    
    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) dummyTree.getCellRenderer();
    
    Icon projectIcon = new ImageIcon(getClass().getResource("/project_open.png"));
    
    ListCellRenderer<? super DefaultMutableTreeNode> r = getRenderer();
    setRenderer((list, value, index, isSelected, cellHasFocus) -> {
      Component c = r.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      if (value == null) {
        return c;
      }
      if (index < 0) {
        String strDisplayTxt = Arrays.stream(value.getPath())
            .filter(DefaultMutableTreeNode.class::isInstance)
            .map(DefaultMutableTreeNode.class::cast)
            .filter(n -> !n.isRoot())
            .map(Objects::toString)
            .collect(Collectors.joining(" / "));
        
        //Get Selected Location Class Info
        String[] lstTxt = strDisplayTxt.split("/");
        int nLength = lstTxt.length;
        String endItem = lstTxt[nLength - 1];
        
        String[] lstLocationInfo = endItem.split(" ");
        int nInfoLength = lstLocationInfo.length;
        strClass = lstLocationInfo[nInfoLength - 1];
        
        //Modify Project Name
        String[] lstProjects = strDisplayTxt.split("/");
        int nProjectLength = lstProjects.length;
        String strText = "";
        for (int i = 0; i < nProjectLength; i ++){
            String [] lstOneText = lstProjects[i].split(" ");
            int nOneLength = lstOneText.length;
            String strOneText = "";
            for (int j = 0; j < nOneLength - 1; j ++){
                strOneText += lstOneText[j] + " ";
            }
            
            strText += strOneText + " / ";
        }
        
        ((JLabel) c).setText(strText);
        ((JLabel) c).setFont(new java.awt.Font("Tahoma", 0, 12));
        return c;
      } else {
        boolean leaf = value.isLeaf();
        
        Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();
        String s = nodeObj.toString();
        String [] lstOneText = s.split(" ");
        int nOneLength = lstOneText.length;
        String strOneText = "";
        for (int j = 0; j < nOneLength - 1; j ++){
            strOneText += lstOneText[j] + " ";
        }
        
        JLabel l = (JLabel) renderer.getTreeCellRendererComponent(
            dummyTree, value, isSelected, true, leaf, index, false);
        renderer.setIcon(projectIcon);
        int indent = Math.max(0, value.getLevel() - 1) * 16;
        l.setBorder(BorderFactory.createEmptyBorder(1, indent + 1, 1, 1));
        l.setFont(new java.awt.Font("Tahoma", 0, 12));
        l.setText(strOneText);
        return l;
      }
    });
  } 
  
}