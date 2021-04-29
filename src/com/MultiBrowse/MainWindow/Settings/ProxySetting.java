/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Settings;

import com.MultiBrowse.MainWindow.Actions.ExportAccs;
import com.MultiBrowse.customcontrol.CheckBoxHeader;
import com.MultiBrowse.customcontrol.EditableHeaderRenderer;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import com.MultiBrowse.customcontrol.Proxy;
import com.MultiBrowse.customcontrol.ProxyItem;
import com.sun.imageio.plugins.jpeg.JPEG;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
/**
 *
 * @author title
 */
public class ProxySetting extends javax.swing.JDialog {

    /**
     * Creates new form ProxySetting
     */
    public ProxySetting(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initSettings();
        
        initTable();
        lstDeletedRows = new ArrayList<Integer>();
        
        setIconImage();
    }
    
    private void setIconImage()
    {
        ImageIcon img = new ImageIcon(getClass().getResource("/MBIcon.png"));
        setIconImage(img.getImage());
    }
    
    JTable table;
    ProxySettingTableModel model;
    List<Integer> lstDeletedRows;
    
    private void initSettings(){
        setTitle("Proxy Settings");
        
        setPreferredSize(new Dimension(958, 610));
        
        setResizable(false);
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 479, dim.height/2 - 305);
        
        jBtn_Save.setVisible(false);
    }
    
    class MyItemListener implements ItemListener
    {
      public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (source instanceof AbstractButton == false) return;
        boolean checked = e.getStateChange() == ItemEvent.SELECTED;
        for(int x = 0, y = table.getRowCount(); x < y; x++)
        {
          table.setValueAt(new Boolean(checked),x,5);
        }
        model.fireTableDataChanged();
      }
    }
    
    private void initTableHeader(boolean isHide){
        table.getTableHeader().setReorderingAllowed(false);
        
        TableColumn col = table.getColumnModel().getColumn(5);

        CheckBoxHeader check = new CheckBoxHeader(new ProxySetting.MyItemListener());
        col.setHeaderRenderer(check);
        
//         table.getTableHeader().setReorderingAllowed(false);
//        
//        TableColumn col = table.getColumnModel().getColumn(5);
//        JPanel panel = new JPanel();
//        panel.setLayout(new FlowLayout());
//        JCheckBox checkbox = new JCheckBox("Select");
//        checkbox.addItemListener(new java.awt.event.ItemListener() {
//             public void itemStateChanged(java.awt.event.ItemEvent evt) {
//                 // TODO add your handling code here:
//                boolean selected = checkbox.isSelected();
//                List<ProxySettingModel> lstTableData = model.getTableData();
//                int nRowCount = lstTableData.size();
//
//                for (int i = 0; i < nRowCount; i ++)
//                    model.setValueAt(!selected, i, 5); 
//                
//               model.fireTableDataChanged();
//             }
//         });
//        panel.add(checkbox);
//        col.setHeaderRenderer(new EditableHeaderRenderer( panel ));
       
        
        //Set the color to alternative row of table
//        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
//        if (defaults.get("Table.alternateRowColor") == null)
//            defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
        
        //hide id column
        if (!isHide){
            final TableColumn idColumn = table.getColumnModel().getColumn(6);
            table.getColumnModel().removeColumn(idColumn);
        
        
            //set table row height
            table.setRowHeight(30);

            JScrollPane scrollPane = new JScrollPane(table);
            //add the table to the frame
            jPan_Table.setLayout(new GridLayout());
            jPan_Table.add(scrollPane);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );

            table.getColumnModel().getColumn(0).setPreferredWidth(30);
        }
    }
    
    private void initTable(){
         
        //actual data for the table in a 2d array
        LocalDBConnect dbControl = new LocalDBConnect();
        
        List<ProxySettingModel> ProxySettingList;
        
        ProxySettingList = dbControl.getProxySettings();
        //create table model with data
        
        model = new ProxySettingTableModel(ProxySettingList);
        
        
        table = new JTable(model){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                Object value = getModel().getValueAt(row, col);
                if (value.equals("BAD")) {
                    comp.setBackground(Color.red);
                } else if (value.equals("OK")) {
                    comp.setBackground(Color.green);
                } else {
                    comp.setBackground(Color.white);
                }
                return comp;
            }
        };
        
        
        initTableHeader(false);
        
    }

    public ProxySetting() {
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

        jPan_Table = new javax.swing.JPanel();
        jBtn_Add = new javax.swing.JButton();
        jBtn_Save = new javax.swing.JButton();
        jBtn_Import = new javax.swing.JButton();
        jBtn_Delete = new javax.swing.JButton();
        jBtn_Test = new javax.swing.JButton();
        jTxt_UserName = new javax.swing.JTextField();
        jTxt_Password = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSpin_Port = new javax.swing.JTextField();
        jTxt_IPAddress = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(958, 610));
        setResizable(false);

        javax.swing.GroupLayout jPan_TableLayout = new javax.swing.GroupLayout(jPan_Table);
        jPan_Table.setLayout(jPan_TableLayout);
        jPan_TableLayout.setHorizontalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPan_TableLayout.setVerticalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );

        jBtn_Add.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Add.setText("Add");
        jBtn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_AddActionPerformed(evt);
            }
        });

        jBtn_Save.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Save.setText("Save");
        jBtn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_SaveActionPerformed(evt);
            }
        });

        jBtn_Import.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Import.setText("Import Proxies");
        jBtn_Import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ImportActionPerformed(evt);
            }
        });

        jBtn_Delete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Delete.setText("Delete");
        jBtn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_DeleteActionPerformed(evt);
            }
        });

        jBtn_Test.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Test.setText("Test Proxies");
        jBtn_Test.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_TestActionPerformed(evt);
            }
        });

        jTxt_UserName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_Password.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("IP Address:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Port:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("UserName:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Password:");

        jSpin_Port.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        try {
            jTxt_IPAddress.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("***.***.***.***")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPan_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jBtn_Add)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
                .addComponent(jBtn_Import)
                .addGap(18, 18, 18)
                .addComponent(jBtn_Delete)
                .addGap(18, 18, 18)
                .addComponent(jBtn_Save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtn_Test)
                .addGap(27, 27, 27))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSpin_Port, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(jTxt_IPAddress))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxt_Password, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(jTxt_UserName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPan_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxt_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTxt_IPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpin_Port, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Add)
                    .addComponent(jBtn_Save)
                    .addComponent(jBtn_Import)
                    .addComponent(jBtn_Delete)
                    .addComponent(jBtn_Test))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    Object[] row = new Object[7];
    private void jBtn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_AddActionPerformed
        // TODO add your handling code here:
        row[0] = "";
        try{
            row[2] = Integer.parseInt(jSpin_Port.getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Please input the number into port input field.");
            return;
        }
        
        String IPAddress = jTxt_IPAddress.getText();
        if (IPAddress.replace(".", "").replace(" ", "").isEmpty()){
            JOptionPane.showMessageDialog(null, "Please input the IP Address.");
            return;
        }
        
        
        String[] lstIP = IPAddress.split("\\.");
        for (int i = 0; i < lstIP.length; i ++){
            if (lstIP[i].trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please input the IP Address correctly.");
                return;
            }
        }
        
        String strIPAddress= "";
        for(int i = 0; i < lstIP.length; i ++){
            if (!strIPAddress.isEmpty())
                strIPAddress += "." + lstIP[i].trim();
            else
                strIPAddress += lstIP[i].trim();
        }
        
        
        row[1] = strIPAddress;
        row[3] = jTxt_UserName.getText();
        row[4] = jTxt_Password.getText();
        row[5] = false;
        row[6] = 0;

        
        
        List<ProxySettingModel> ProxySettingList = model.getTableData();
        
        
        for (int i = 0; i < ProxySettingList.size(); i ++){
            if (ProxySettingList.get(i).getIPAddress().equals(strIPAddress)){
                JOptionPane.showMessageDialog(null, "Don't allow duplicate IP Address.");
                return;
            }
        }
        // add row to the model
        if (!jTxt_IPAddress.getText().isEmpty() || !jTxt_UserName.getText().isEmpty() || !strIPAddress.isEmpty()){
            model.addRow(row);
            
            
            final TableColumn idColumn = table.getColumnModel().getColumn(6);
            table.getColumnModel().removeColumn(idColumn);
            
            
            table.getColumnModel().getColumn(0).setPreferredWidth(30);
            
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
            
            
            initTableHeader(true);
        }
    }//GEN-LAST:event_jBtn_AddActionPerformed

    private void jBtn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_DeleteActionPerformed
        // TODO add your handling code here:
        if (!model.isSelected())
            return;
        
        ImageIcon markIcon = new ImageIcon(getClass().getResource("/question_36.png"));
        int nResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this proxy(s)?", "Delete  Proxies", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, markIcon);
        if (nResult != JOptionPane.YES_OPTION)
            return;
        
        List<Integer> lstDeleted = model.removeRow();
        lstDeletedRows.addAll(lstDeleted);
    }//GEN-LAST:event_jBtn_DeleteActionPerformed

    public void saveData(){
        LocalDBConnect dbControl = new LocalDBConnect();
        List<ProxySettingModel> lstTableData = model.getTableData();
        
        int nRowCount = lstTableData.size();
        for (int i = 0; i < nRowCount; i ++){
            ProxySettingModel oneRowData = lstTableData.get(i);
            
            int nId = oneRowData.getId();
//            oneRowData.setOrderId(i);
            if (nId == 0){
                dbControl.InsertProxySetting(oneRowData);
            }
            else
            {
                dbControl.UpdateProxySetting(oneRowData);
            }
        }
        
        for (int i = 0; i < lstDeletedRows.size(); i ++){
            dbControl.DeleteProxySetting(lstDeletedRows.get(i));
        }
        
        lstDeletedRows.clear();
    }
    private void jBtn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SaveActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_jBtn_SaveActionPerformed

    private void jBtn_ImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ImportActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter csvfilter = new FileNameExtensionFilter("CSV file", "csv");
        FileNameExtensionFilter txtfilter = new FileNameExtensionFilter("TXT file", "txt");
        
        fileChooser.setFileFilter(csvfilter);
        fileChooser.setFileFilter(txtfilter);
        
        int result = fileChooser.showOpenDialog(this);
        String strCsvFilePath = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            strCsvFilePath = selectedFile.getAbsolutePath();
        }
        
        CSVReader(strCsvFilePath);
        
    }//GEN-LAST:event_jBtn_ImportActionPerformed

    private void jBtn_TestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_TestActionPerformed
        // TODO add your handling code here:
        List<ProxySettingModel> lstProxies = model.getSelectedProxies();
        int nSelectedProxyCount = lstProxies.size();
        
        for (int i = 0; i < nSelectedProxyCount; i ++){
            String strProxyToken = lstProxies.get(i).getIPAddress() + ":" + lstProxies.get(i).getPort();
//            ProxyItem item = new ProxyItem(strProxyToken);
//            boolean b = item.isWorking();
            
            Proxy item = new Proxy(lstProxies.get(i).getIPAddress(), lstProxies.get(i).getPort());
            boolean b = false;
            try {
                b = item.check();
            } catch (IOException ex) {
                Logger.getLogger(ProxySetting.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (b){
                model.setValueAt("OK", lstProxies.get(i).getRowIndex(), 0);
                
            }else{
                model.setValueAt("BAD", lstProxies.get(i).getRowIndex(), 0);
            }
            
            model.fireTableCellUpdated(lstProxies.get(i).getRowIndex(), 0);
        }
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
    }//GEN-LAST:event_jBtn_TestActionPerformed

    private void TXTReader(String strFilePath)
    {
        String delimiter = ":";
        try {
            File file = new File(strFilePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            int nIndex = 0;
            while((line = br.readLine()) != null) {
                if (nIndex == 0){
                    nIndex ++;
                    continue;
                }
                boolean isDuplicate = false;
                List<ProxySettingModel> ProxySettingList = model.getTableData();
                
                
                tempArr = line.split(delimiter);
        
                
                if (tempArr.length < 4){
                    TXTReader(strFilePath);
                    return;
                }
                
                for (int i = 0; i < ProxySettingList.size(); i ++){
                    if (ProxySettingList.get(i).getIPAddress().equals(tempArr[0])){
                        isDuplicate = true;
                        break;
                    }
                }
                
                if (isDuplicate)
                    continue;
                
                nIndex ++;
                    
                Object[] row = new Object[7];
                row[0] = "";
                row[1] = tempArr[0];
                row[2] = Integer.parseInt(tempArr[1].trim());
                row[3] = tempArr[2];
                row[4] = tempArr[3];
                row[5] = false;
                row[6] = 0;

                model.addRow(row);
                final TableColumn idColumn = table.getColumnModel().getColumn(6);
                table.getColumnModel().removeColumn(idColumn);
//            for(String tempStr : tempArr) {
//                System.out.print(tempStr + " ");
//            }
//            System.out.println();
            }
            
            initTableHeader(true);
            br.close();
         
         } catch(IOException ioe) {
            ioe.printStackTrace();
         }
    }    
    
    private void CSVReader(String strFilePath){
        String delimiter = ",";
        try {
            File file = new File(strFilePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            int nIndex = 0;
            while((line = br.readLine()) != null) {
                if (nIndex == 0){
                    nIndex ++;
                    continue;
                }
                boolean isDuplicate = false;
                List<ProxySettingModel> ProxySettingList = model.getTableData();
                
                tempArr = line.split(delimiter);
                
                if (tempArr.length < 4){
                    TXTReader(strFilePath);
                    return;
                }
        
                for (int i = 0; i < ProxySettingList.size(); i ++){
                    if (ProxySettingList.get(i).getIPAddress().equals(tempArr[0])){
                        isDuplicate = true;
                        break;
                    }
                }
                
                if (isDuplicate)
                    continue;
                
                nIndex ++;
                    
                Object[] row = new Object[7];
                row[0] = "";
                row[1] = tempArr[0];
                row[2] = Integer.parseInt(tempArr[1].trim());
                row[3] = tempArr[2];
                row[4] = tempArr[3];
                row[5] = false;
                row[6] = 0;

                model.addRow(row);
                final TableColumn idColumn = table.getColumnModel().getColumn(6);
                table.getColumnModel().removeColumn(idColumn);
//            for(String tempStr : tempArr) {
//                System.out.print(tempStr + " ");
//            }
//            System.out.println();
            }
            
            initTableHeader(true);
            br.close();
         
         } catch(IOException ioe) {
            ioe.printStackTrace();
            TXTReader(strFilePath);
         }
    }
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
            java.util.logging.Logger.getLogger(ProxySetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProxySetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProxySetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProxySetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProxySetting dialog = new ProxySetting(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Add;
    private javax.swing.JButton jBtn_Delete;
    private javax.swing.JButton jBtn_Import;
    private javax.swing.JButton jBtn_Save;
    private javax.swing.JButton jBtn_Test;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPan_Table;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jSpin_Port;
    private javax.swing.JFormattedTextField jTxt_IPAddress;
    private javax.swing.JTextField jTxt_Password;
    private javax.swing.JTextField jTxt_UserName;
    // End of variables declaration//GEN-END:variables
}
