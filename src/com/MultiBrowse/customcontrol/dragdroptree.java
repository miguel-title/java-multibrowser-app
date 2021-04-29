/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.customcontrol;

import com.MultiBrowse.customcontrol.LocalDBConnect;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.tree.*;



/**
 *
 * @author title
 */
public class dragdroptree {
    public dragdroptree(){
        
    }
    
    private ArrayList<objProject> m_lstData;
    private LocalDBConnect dbcontrol = new LocalDBConnect();
    
    public void setData(ArrayList<objProject> lstData){
        m_lstData = new ArrayList<objProject>();
        m_lstData = lstData;
    }
        
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    
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
     
    private JTree tree;
    
    public JTree getTree(){
        return tree;
    }
    
    public JScrollPane getContent() throws MalformedURLException {
        tree = new JTree();
        
        
        tree.setDragEnabled(true);
        tree.setDropMode(DropMode.ON_OR_INSERT);
        tree.setTransferHandler(new TreeTransferHandler());
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
//        expandTree(tree);
        resetTreeData();
        
        tree.setRowHeight(25);
        
        //Setting tree node ICon
        
        CustomIconRenderer customIconRenderer = new CustomIconRenderer();
        tree.setCellRenderer(customIconRenderer);
        
        return new JScrollPane(tree);
    }
    
    public void refresh(){
        resetTreeData();
        ((DefaultTreeModel)tree.getModel()).reload();
        expandTree(tree);
    }
    
    private void resetTreeData(){
        DefaultMutableTreeNode[] node = new DefaultMutableTreeNode[m_lstData.size()];
        for (int i = 0; i < m_lstData.size(); i++) {
//            System.out.print("XX:" + m_lstData.get(i).strName + " " + Integer.toString(m_lstData.get(i).Type) + " " + Integer.toString(m_lstData.get(i).id));
            node[i] = new DefaultMutableTreeNode(m_lstData.get(i).strName + " " + Integer.toString(m_lstData.get(i).Type) + " " + Integer.toString(m_lstData.get(i).id));
        }
           
        rootNode = node[0];   //Set the root node

        //Cycle through the table above and assign nodes to nodes
        for (int i = 0; i < m_lstData.size(); i++) {
            for (int j = i + 1; j < m_lstData.size(); j++) {
                if (isParent(m_lstData.get(i).strClass, m_lstData.get(j).strClass)){
//                    System.out.println(m_lstData.get(i).strName + " is parent of " + m_lstData.get(j).strName);
                    
                    int height = m_lstData.get(j).Type == 0 ? 20 : 60;
                    node[i].add(node[j]);
                }
            }
        }
        
        
        //Creating the tree model. setting the root node.
        treeModel = new DefaultTreeModel(rootNode);
        //Setting the tree model to the JTree
        tree.setModel(treeModel);
    }

    private void expandTree(JTree tree) {
        DefaultMutableTreeNode root =
            (DefaultMutableTreeNode)tree.getModel().getRoot();
        Enumeration e = root.breadthFirstEnumeration();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)e.nextElement();
            if(node.isLeaf()) continue;
            int row = tree.getRowForPath(new TreePath(node.getPath()));
            tree.expandRow(row);
        }
    }

}

class TreeTransferHandler extends TransferHandler {
    DataFlavor nodesFlavor;
    DataFlavor[] flavors = new DataFlavor[1];
    DefaultMutableTreeNode[] nodesToRemove;

    public TreeTransferHandler() {
        try {
            String mimeType = DataFlavor.javaJVMLocalObjectMimeType +
                              ";class=\"" +
                javax.swing.tree.DefaultMutableTreeNode[].class.getName() +
                              "\"";
            nodesFlavor = new DataFlavor(mimeType);
            flavors[0] = nodesFlavor;
        } catch(ClassNotFoundException e) {
            System.out.println("ClassNotFound: " + e.getMessage());
        }
    }

    public boolean canImport(TransferHandler.TransferSupport support) {
        if(!support.isDrop()) {
            return false;
        }
        support.setShowDropLocation(true);
        if(!support.isDataFlavorSupported(nodesFlavor)) {
            return false;
        }
        // Do not allow a drop on the drag source selections.
        JTree.DropLocation dl =
                (JTree.DropLocation)support.getDropLocation();
        JTree tree = (JTree)support.getComponent();
        int dropRow = tree.getRowForPath(dl.getPath());
        
        int[] selRows = tree.getSelectionRows();
        for(int i = 0; i < selRows.length; i++) {
            if(selRows[i] == dropRow) {
                return false;
            }
        }
        
        // Do not allow a non-leaf node to be copied to a level
        // which is less than its source level.
        TreePath dest = dl.getPath();
        DefaultMutableTreeNode target =
            (DefaultMutableTreeNode)dest.getLastPathComponent();
        
        //Dont Insert to Profile
        String strTgInfo = target.toString();
        int nLen = strTgInfo.split(" ").length;
        String strType = strTgInfo.split(" ")[nLen - 2];
        if (strType.equals("1")){
            return false;
        }
        
        
        // Do not allow MOVE-action drops if a non-leaf node is
        // selected unless all of its children are also selected.
        int action = support.getDropAction();
        if(action == MOVE) {
            return haveCompleteNode(tree);
        }
               
                
        TreePath path = tree.getPathForRow(selRows[0]);
        DefaultMutableTreeNode firstNode =
            (DefaultMutableTreeNode)path.getLastPathComponent();
        if(firstNode.getChildCount() > 0 &&
               target.getLevel() < firstNode.getLevel()) {
            return false;
        }
        return true;
    }

    private boolean haveCompleteNode(JTree tree) {
        int[] selRows = tree.getSelectionRows();
        TreePath path = tree.getPathForRow(selRows[0]);
        DefaultMutableTreeNode first =
            (DefaultMutableTreeNode)path.getLastPathComponent();
        int childCount = first.getChildCount();
        // first has children and no children are selected.
//        if(childCount > 0 && selRows.length == 1)
//            return false;
        // first may have children.
        for(int i = 1; i < selRows.length; i++) {
            path = tree.getPathForRow(selRows[i]);
            DefaultMutableTreeNode next =
                (DefaultMutableTreeNode)path.getLastPathComponent();
            if(first.isNodeChild(next)) {
                // Found a child of first.
                if(childCount > selRows.length-1) {
                    // Not all children of first are selected.
                    return false;
                }
            }
        }
        return true;
    }
    
    public DefaultMutableTreeNode cloneNode(DefaultMutableTreeNode node){
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(node.getUserObject());
        for(int iChildren=node.getChildCount(), i=0;i<iChildren; i++){
                newNode.add((DefaultMutableTreeNode)cloneNode((DefaultMutableTreeNode)node.getChildAt(i) ) );
        }
        return newNode;
    }
    
    protected Transferable createTransferable(JComponent c) {
          JTree tree = (JTree) c;
        TreePath[] paths = tree.getSelectionPaths();
        if (paths != null) {
          List<DefaultMutableTreeNode> copies = new ArrayList<>();
          List<DefaultMutableTreeNode> toRemove = new ArrayList<>();
          DefaultMutableTreeNode node = 
              (DefaultMutableTreeNode) paths[0].getLastPathComponent();
          DefaultMutableTreeNode copy = cloneNode(node);
          copies.add(copy);
          toRemove.add(node);
          
          for (int i = 1; i < paths.length; i++) {
            DefaultMutableTreeNode next = 
                (DefaultMutableTreeNode) paths[i].getLastPathComponent();
            System.out.print("\nadf:" + next.toString());
            // Do not allow higher level nodes to be added to list.
            if (next.getLevel() < node.getLevel()) {
              break;
            } else if (next.getLevel() > node.getLevel()) {  // child node
              copy.add(copy(next));
              // node already contains child
            } else {                                        // sibling
              copies.add(copy(next));
              toRemove.add(next);
            }
          }
          DefaultMutableTreeNode[] nodes = 
              copies.toArray(new DefaultMutableTreeNode[copies.size()]);
          nodesToRemove = 
              toRemove.toArray(new DefaultMutableTreeNode[toRemove.size()]);
          return new NodesTransferable(nodes);
        }
        return null;
    }
    
    
    private List<DefaultMutableTreeNode> getChildrens(TreeNode parentnode){
        List<DefaultMutableTreeNode> lstResult = new ArrayList<DefaultMutableTreeNode>();
        int nNodeCount = parentnode.getChildCount();
        int nNodeIndex = 0;
        while (nNodeCount > 0){
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) parentnode.getChildAt(nNodeIndex);
            lstTmpNodes.add(childNode);
            
            getChildrens(childNode);
            
            nNodeCount --;
            nNodeIndex ++;
        }
        return lstResult;
    }

    /** Defensive copy used in createTransferable. */
    private DefaultMutableTreeNode copy(TreeNode node) {
        return new DefaultMutableTreeNode(node);
    }

    protected void exportDone(JComponent source, Transferable data, int action) {
        if((action & MOVE) == MOVE) {
            JTree tree = (JTree)source;
            DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
            // Remove nodes saved in nodesToRemove in createTransferable.
            for(int i = 0; i < nodesToRemove.length; i++) {
                model.removeNodeFromParent(nodesToRemove[i]);
            }
            
            //Get Update Nodes in Tree
            lstNodes.clear();
            Object parentNode = model.getRoot();
            getUpdateTreeNodes(model,parentNode);
            System.out.print("\nNodeAllSize:" + lstNodes.size());
            System.out.print("\n" + lstNodes);
            
            LocalDBConnect dbcontrol = new LocalDBConnect();
            dbcontrol.updateProjectData(lstNodes);
            //
            model.reload();
            expandTree(tree);
        }
    }
    
    private void expandTree(JTree tree) {
        DefaultMutableTreeNode root =
            (DefaultMutableTreeNode)tree.getModel().getRoot();
        Enumeration e = root.breadthFirstEnumeration();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)e.nextElement();
            if(node.isLeaf()) continue;
            int row = tree.getRowForPath(new TreePath(node.getPath()));
            tree.expandRow(row);
        }
    }

    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    public boolean importData(TransferHandler.TransferSupport support) {
        if(!canImport(support)) {
            return false;
        }
        // Extract transfer data.
        DefaultMutableTreeNode[] nodes = null;
        try {
            Transferable t = support.getTransferable();
            nodes = (DefaultMutableTreeNode[])t.getTransferData(nodesFlavor);
        } catch(UnsupportedFlavorException ufe) {
            System.out.println("UnsupportedFlavor: " + ufe.getMessage());
        } catch(java.io.IOException ioe) {
            System.out.println("I/O error: " + ioe.getMessage());
        }
        // Get drop location info.
        JTree.DropLocation dl =
                (JTree.DropLocation)support.getDropLocation();
        int childIndex = dl.getChildIndex();
        TreePath dest = dl.getPath();
//        System.out.print("\nDropLocation:" + dest);
        DefaultMutableTreeNode parent =
            (DefaultMutableTreeNode)dest.getLastPathComponent();
        JTree tree = (JTree)support.getComponent();
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        // Configure for drop mode.
        int index = childIndex;    // DropMode.INSERT
        if(childIndex == -1) {     // DropMode.ON
            index = parent.getChildCount();
        }
        // Add data to model.
        for(int i = 0; i < nodes.length; i++) {
            model.insertNodeInto(nodes[i], parent, index++);
        }

        
        return true;
    }
    
    List lstNodes = new ArrayList();
    List<DefaultMutableTreeNode> lstTmpNodes = new ArrayList<DefaultMutableTreeNode>();
    
    public void getUpdateTreeNodes(DefaultTreeModel model, Object parentNode)
    {
        int nNodeCount = model.getChildCount(parentNode);
        int nNodeIndex = 0;
        while (nNodeCount > 0){
            //Get Child Node Info
            Object value = model.getChild(parentNode, nNodeIndex);
            Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();
            String[] lstChildInf = nodeObj.toString().split(" ");
            String strChildId = lstChildInf[lstChildInf.length - 1];
            //Get Parent Node Info
            Object prObj = ((DefaultMutableTreeNode) parentNode).getUserObject();
            String[] lstInf = prObj.toString().split(" ");
            String strParentId = lstInf[lstInf.length - 1];;
            //
            lstNodes.add(strChildId + " " + strParentId);
            
            getUpdateTreeNodes(model, value);
            
            nNodeCount --;
            nNodeIndex ++;
        }
        
    }
            
    public String toString() {
        return getClass().getName();
    }

    public class NodesTransferable implements Transferable {
        DefaultMutableTreeNode[] nodes;

        public NodesTransferable(DefaultMutableTreeNode[] nodes) {
            this.nodes = nodes;
         }

        public Object getTransferData(DataFlavor flavor)
                                 throws UnsupportedFlavorException {
            if(!isDataFlavorSupported(flavor))
                throw new UnsupportedFlavorException(flavor);
            return nodes;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return nodesFlavor.equals(flavor);
        }
    }
}

class CustomIconRenderer extends DefaultTreeCellRenderer {

    /**
     *
     */
    ImageIcon projectopenIcon;
    ImageIcon projectcloseIcon;
    ImageIcon profileIcon;

    public CustomIconRenderer() throws MalformedURLException {
        projectopenIcon = new ImageIcon(getClass().getResource("/project_open.png"));
        projectcloseIcon = new ImageIcon(getClass().getResource("/project_close.png"));
        profileIcon = new ImageIcon(getClass().getResource("/profile.png"));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);

        Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();
        String s = nodeObj.toString();
//        System.out.print("---output:" + s + "\n");
        int nSize = s.split(" ").length;
        if (s.split(" ")[nSize - 2].equals("0")) {
            setLeafIcon(projectcloseIcon);
            setOpenIcon(projectopenIcon);
            setClosedIcon(projectcloseIcon);
        }else{
            setIcon(profileIcon);
        }
        String strNodeText = "";
        for (int i = 0; i < nSize - 2; i ++){
            strNodeText += s.split(" ")[i] + " ";
        }
        setText(strNodeText.trim());
        return this;
    }
}
