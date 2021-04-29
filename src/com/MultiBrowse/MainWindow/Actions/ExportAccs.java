/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Actions;

import com.MultiBrowse.customcontrol.CheckBoxHeader;
import com.MultiBrowse.customcontrol.EditableHeaderRenderer;
import com.MultiBrowse.customcontrol.LocalDBConnect;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import static org.h2.util.StringUtils.isNullOrEmpty;
/**
 *
 * @author title
 */
public class ExportAccs extends javax.swing.JDialog {

    /**
     * Creates new form ExportAccs
     */
    public ExportAccs(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initSettings();
        
        initTable();
    }

    JTable table;
    ExportAccsTableModel model;
    
    private void initTable(){
        LocalDBConnect dbControl = new LocalDBConnect();
        
        ArrayList<ExportAccsModel> profileInfoList;
        
        profileInfoList = dbControl.getProfiles();
        //create table model with data
        
        model = new ExportAccsTableModel(profileInfoList);
        
        
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        //hide id column
        final TableColumn idColumn = table.getColumnModel().getColumn(11);
        table.getColumnModel().removeColumn(idColumn);
        
        //set table row height
        table.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(table);
        //add the table to the frame
        jPan_Table.setLayout(new GridLayout());
        jPan_Table.add(scrollPane);
        
        initTableHeader();
    }
    
    private void initTableHeader(){
//        table.getTableHeader().setReorderingAllowed(false);
//        
//        TableColumn col = table.getColumnModel().getColumn(10);
//        JPanel panel = new JPanel();
//        panel.setLayout(new FlowLayout());
//        JCheckBox checkbox = new JCheckBox("Select");
//        checkbox.addItemListener(new java.awt.event.ItemListener() {
//             public void itemStateChanged(java.awt.event.ItemEvent evt) {
//                 // TODO add your handling code here:
//                boolean selected = checkbox.isSelected();
//                List<ExportAccsModel> lstTableData = model.getTableData();
//                int nRowCount = lstTableData.size();
//
//                for (int i = 0; i < nRowCount; i ++)
//                    model.setValueAt(!selected, i, 10); 
//                
//               model.fireTableDataChanged();
//             }
//         });
//        panel.add(checkbox);
//        col.setHeaderRenderer(new EditableHeaderRenderer( panel ));
      
        table.getTableHeader().setReorderingAllowed(false);
        
        TableColumn col = table.getColumnModel().getColumn(10);

        CheckBoxHeader check = new CheckBoxHeader(new MyItemListener());
        col.setHeaderRenderer(check);
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
    
    private void initSettings(){
        setTitle("Export Accounts");
        
        setPreferredSize(new Dimension(998, 552));
        
        
        //create window in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - 449, dim.height/2 - 276);
    }
    
    public ExportAccs() {
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
        jBtn_Export = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPan_TableLayout = new javax.swing.GroupLayout(jPan_Table);
        jPan_Table.setLayout(jPan_TableLayout);
        jPan_TableLayout.setHorizontalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPan_TableLayout.setVerticalGroup(
            jPan_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        jBtn_Export.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Export.setText("Export Selected to CSV");
        jBtn_Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPan_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(398, 398, 398)
                .addComponent(jBtn_Export, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(362, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPan_Table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                .addComponent(jBtn_Export, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ExportActionPerformed
        // TODO add your handling code here:
        // csv name be with dd_mm_yy_hh_mm_ss format.
        
        boolean isSelectRow = false;
        for(int i=0; i< model.getRowCount(); i++) 
        {
            if (isSelectRow)
                break;
            for(int j=0; j < model.getColumnCount() - 2; j++) 
            {
                boolean isSelect = (boolean)model.getValueAt(i, 10);
                if (!isSelect)
                    break;
                isSelectRow = true;
            }
        }
        
        if(!isSelectRow){
            JOptionPane.showMessageDialog(null, "Please select the row(s).");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");   
        fileChooser.setFileFilter(new FileNameExtensionFilter("csv file","csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            
            fileChooser.setFileFilter(null);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            String strFilePath = fileToSave.getAbsolutePath() + ".csv";
            
        
            try 
            {
                File archivo = new File(strFilePath);
                FileWriter excel = new FileWriter(archivo);
                 for(int i = 0; i < model.getColumnCount() - 2; i++)
                 {
                    excel.write(model.getColumnName(i) + ",");
                 }
                 excel.write("\n");
                 int nSelectRowCount = 0;
                 for(int i=0; i< model.getRowCount(); i++) 
                 {
                    for(int j=0; j < model.getColumnCount() - 2; j++) 
                    {
                        boolean isSelect = (boolean)model.getValueAt(i, 10);
                        if (!isSelect)
                            break;
                        nSelectRowCount ++;
                        String data = (String)model.getValueAt(i, j);
                        if(isNullOrEmpty(data))
                        {
                            data="";
                        }
                        excel.write(data+",");
                    }
                    excel.write("\n");
                }

                excel.close();
                if (nSelectRowCount != 0)
                    JOptionPane.showMessageDialog(null, "Export Successful’", "Account Export", JOptionPane.INFORMATION_MESSAGE);
                else{
                    JOptionPane.showMessageDialog(null, "Please select the rows exported");

                    File removefile = new File(strFilePath); 
                    removefile.delete();

                }

            } catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(null, ex.toString());
            }

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        
        
//        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yy_hh_mm_ss");  
//        Date date = new Date();  
//        String strCurDate = formatter.format(date); 
//        
//        String outputpath = String.format("output");
//        
//        File file = new File(outputpath);
//        boolean isDirCreate = false;
//        if (!file.isDirectory())
//            isDirCreate = file.mkdir();
//        
//        String path = String.format("output/account_multibrowse_%s.csv", strCurDate);
        
        
    }//GEN-LAST:event_jBtn_ExportActionPerformed

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
            java.util.logging.Logger.getLogger(ExportAccs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExportAccs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExportAccs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExportAccs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ExportAccs dialog = new ExportAccs(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jBtn_Export;
    private javax.swing.JPanel jPan_Table;
    // End of variables declaration//GEN-END:variables
}

