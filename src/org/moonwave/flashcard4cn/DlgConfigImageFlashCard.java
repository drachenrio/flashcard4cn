/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * flashcard4cn - FlashCard for Chinese Characters
 * Copyright (C) Jonathan Luo
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package org.moonwave.flashcard4cn;

import java.io.File;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Action;
import org.moonwave.util.DialogUtil;

/**
 * DlgConfigImageFlashCard.java
 *
 * Created on Sept 20, 2009, 11:30 PM
 *
 * @author Jonathan Luo
 */
public class DlgConfigImageFlashCard extends javax.swing.JDialog {

    ImageFlashCardEntity flashCardEntity;
    File selectedDirectory;

    /** Creates new form DlgOptions */
    public DlgConfigImageFlashCard(java.awt.Frame parent, boolean modal, ImageFlashCardEntity flashCardEntity) {
        super(parent, modal);
        this.flashCardEntity = flashCardEntity;
        initComponents();
        postInit();
    }

    private void postInit() {
        flashCardEntity.loadFlashCardKeys();
        cbFlashCard.setModel(new javax.swing.DefaultComboBoxModel(flashCardEntity.getFlashCardNames().toArray()));
        String flashCardName = (String) cbFlashCard.getSelectedItem();
        flashCardEntity.setCurrentFlashCardName(flashCardName);
        flashCardEntity.copyFlashCardValue(flashCardName);
        txtPath.setText(flashCardEntity.getFlashCardValue(flashCardName));
        setTotalCount();
        btnSave.setEnabled(true);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbFlashCard = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnBrowse = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtPath = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.moonwave.flashcard4cn.FlashCardApp.class).getContext().getResourceMap(DlgConfigImageFlashCard.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setModal(true);
        setName("Form"); // NOI18N
        setResizable(false);

        cbFlashCard.setToolTipText(resourceMap.getString("cbFlashCard.toolTipText")); // NOI18N
        cbFlashCard.setName("cbFlashCard"); // NOI18N
        cbFlashCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFlashCardActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );

        btnBrowse.setText(resourceMap.getString("btnBrowse.text")); // NOI18N
        btnBrowse.setName("btnBrowse"); // NOI18N
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        btnNew.setMnemonic('N');
        btnNew.setText(resourceMap.getString("btnNew.text")); // NOI18N
        btnNew.setToolTipText(resourceMap.getString("btnNew.toolTipText")); // NOI18N
        btnNew.setName("btnNew"); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnDelete.setMnemonic('D');
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setToolTipText(resourceMap.getString("btnDelete.toolTipText")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClose.setText(resourceMap.getString("btnClose.text")); // NOI18N
        btnClose.setToolTipText(resourceMap.getString("btnClose.toolTipText")); // NOI18N
        btnClose.setName("btnClose"); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        txtPath.setText(resourceMap.getString("txtPath.text")); // NOI18N
        txtPath.setName("txtPath"); // NOI18N
        txtPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPathActionPerformed(evt);
            }
        });
        txtPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPathFocusLost(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbFlashCard, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPath, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowse))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btnNew)
                        .addGap(53, 53, 53)
                        .addComponent(btnDelete)
                        .addGap(49, 49, 49)
                        .addComponent(btnSave)
                        .addGap(46, 46, 46)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClose, btnDelete, btnNew, btnSave});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbFlashCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnClose)
                    .addComponent(btnNew)
                    .addComponent(btnDelete)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbFlashCard, txtPath});

        getAccessibleContext().setAccessibleName(resourceMap.getString("Form.AccessibleContext.accessibleName")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbFlashCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFlashCardActionPerformed
        JComboBox cb = (JComboBox) evt.getSource();
        String selectedFlashCard = (String) cb.getSelectedItem();
        String prevSelectedFlashCard = flashCardEntity.getCurrentFlashCardName();
        System.out.println("selectedFlashCardName: " + selectedFlashCard);
        System.out.println("prevSelectedFlashCard: " + prevSelectedFlashCard);
        if (flashCardEntity.hasChanged(prevSelectedFlashCard)) {
            int iRet = DialogUtil.showYesNoConfirm(this, "Switch to New Flash Card Confirmation", "Data has changed, are you sure you want to swicth to a new flashcard and discard the changes?");
            if (iRet == JOptionPane.YES_OPTION) {
                flashCardEntity.restoreFlashCardValue(prevSelectedFlashCard);
                switchToNewFlashCard(selectedFlashCard);
            } else {
                cb.setSelectedItem(prevSelectedFlashCard);
            }
        } else {
            switchToNewFlashCard(selectedFlashCard);
        }
    }//GEN-LAST:event_cbFlashCardActionPerformed

    private void switchToNewFlashCard(String flashCardName) {
        flashCardEntity.copyFlashCardValue(flashCardName);
        flashCardEntity.setCurrentFlashCardName(flashCardName);

        this.txtPath.setText(flashCardEntity.getFlashCardValue(flashCardName));
        this.setTotalCount();
        updateButtonStates();
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int iRet = DialogUtil.showYesNoConfirm(this, "Deletion Confirmation", "Are you sure you want to delete selected FlashCard?");
        if (iRet == JOptionPane.YES_OPTION) {
            String selectedFlashCard = (String) cbFlashCard.getSelectedItem();
            System.out.println("delete selectedFlashCard: " + selectedFlashCard);
            flashCardEntity.deleteFlashCard(selectedFlashCard);
            if (flashCardEntity.save()) {
                btnSave.setEnabled(false);
                cbFlashCard.removeItem(selectedFlashCard);
                if (cbFlashCard.getModel().getSize() > 0) {
                    cbFlashCard.setSelectedIndex(0);
                    selectedFlashCard = (String) cbFlashCard.getSelectedItem();
                    System.out.println("selectedFlashCard 2: " + selectedFlashCard);
                    flashCardEntity.setCurrentFlashCardName(selectedFlashCard);
                    flashCardEntity.copyFlashCardValue(selectedFlashCard);
                }
            } else {
                DialogUtil.showError(this, "Save Error", "An error occurred when saving data");
            }
            this.updateButtonStates();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String flashCardName = (String) cbFlashCard.getSelectedItem();        
        String path = this.txtPath.getText();
        flashCardEntity.setFlashCardValue(flashCardName, path); // save current folder info first
        if (flashCardEntity.hasChanged(flashCardName)) {
            if (flashCardEntity.save()) {
                btnSave.setEnabled(false);
                flashCardEntity.copyFlashCardValue(flashCardName);
            } else {
                DialogUtil.showError(this, "Save Error", "An error occurred when saving data");
            }
        }        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        DlgNewFlashCardName dlg = new DlgNewFlashCardName(this, "Create a new FlashCard", flashCardEntity);
        dlg.setVisible(true);
        if (dlg.getResultObject() != null) {
            String newFlashCardName = (String) dlg.getResultObject(); // get a valid new flash card name
            flashCardEntity.setCurrentFlashCardName(newFlashCardName);
            flashCardEntity.setFlashCardValue(newFlashCardName, ""); // add new flashcard to flashCardMap, must before the next line
            flashCardEntity.copyFlashCardValue(newFlashCardName);

            flashCardEntity.createAndSortFlashCardNames();
            cbFlashCard.setModel(new javax.swing.DefaultComboBoxModel(flashCardEntity.getFlashCardNames().toArray()));
            cbFlashCard.getModel().setSelectedItem(newFlashCardName);
        }
        dlg.dispose();
        updateButtonStates();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        String flashCardName = (String) cbFlashCard.getSelectedItem();
        if (flashCardEntity.hasChanged(flashCardName)) {
            int iRet = DialogUtil.showYesNoConfirm(this, "Close Confirmation", "Data has changed, are you sure you want to close this dialog and discard any changes?");
            if (iRet == JOptionPane.YES_OPTION) {
                closeDlg();
            }
        } else {
            closeDlg();
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        
        JFileChooser chooser = new JFileChooser(); // remember previous selection
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        try {
            // Create a File object containing the canonical path of the
            // desired directory
            if (selectedDirectory == null)
                selectedDirectory = new File(new File(".").getCanonicalPath());

            // Set the current directory
            chooser.setCurrentDirectory(selectedDirectory);
            chooser.showOpenDialog(this);

            selectedDirectory = chooser.getSelectedFile();
            this.txtPath.setText((selectedDirectory != null) ? selectedDirectory.getPath() : "");
            // update to entity
            String flashCardName = (String) cbFlashCard.getSelectedItem();
            this.flashCardEntity.getFlashCardMap().put(flashCardName, this.txtPath.getText().trim());
            updateButtonStates();
        } catch (IOException e) {
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void txtPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPathFocusLost
        String flashCardName = (String) cbFlashCard.getSelectedItem();        
        String path = this.txtPath.getText();
        flashCardEntity.setFlashCardValue(flashCardName, path);
        updateButtonStates();
    }//GEN-LAST:event_txtPathFocusLost

    private void txtPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPathActionPerformed
        String flashCardName = (String) cbFlashCard.getSelectedItem();        
        String path = this.txtPath.getText();
        flashCardEntity.setFlashCardValue(flashCardName, path);
    }//GEN-LAST:event_txtPathActionPerformed

    private void updateButtonStates() {
        String flashCardName = (String) cbFlashCard.getSelectedItem();
        if (flashCardEntity.hasChanged(flashCardName)) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }

    private void setTotalCount() {
        String text = txtPath.getText().trim();
        String[] tokens = StringUtils.split(text, ", ， "); // \uFF0C; //OK for both english and Chinese ","s and " "s
//        this.lblTotalCount.setText((tokens.length > 0) ? String.valueOf(tokens.length) : "0");
    }

    @Action
    public void closeDlg() {
        this.setVisible(false);
        this.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cbFlashCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtPath;
    // End of variables declaration//GEN-END:variables

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DlgConfigImageFlashCard dialog = new DlgConfigImageFlashCard(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}
