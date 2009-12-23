/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JConfiguration.java
 *
 * Created on 10 Δεκ 2009, 3:28:21 πμ
 */
package com.panayotis.cafeports.config;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 *
 * @author teras
 */
public class JConfiguration extends javax.swing.JDialog {

    private static final JConfiguration dialog = new JConfiguration();

    /** Creates new form JConfiguration */
    private JConfiguration() {
        super();
        initComponents();
        BrowseB.putClientProperty("JButton.buttonType", "textured");
        setLocationByPlatform(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        PrefixP = new javax.swing.JPanel();
        PrefixL = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        PrefixT = new javax.swing.JTextField();
        BrowseB = new javax.swing.JButton();
        CommandP = new javax.swing.JPanel();
        CommandL = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        CommandT = new javax.swing.JTextField();
        SudoC = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaféPorts Configuration");
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 16, 24, 16));
        jPanel4.setLayout(new java.awt.BorderLayout(0, 12));

        PrefixP.setLayout(new java.awt.BorderLayout(8, 0));

        PrefixL.setText("MacPorts prefix (usually /opt/local)");
        PrefixL.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 4, 0));
        PrefixP.add(PrefixL, java.awt.BorderLayout.NORTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 24, 0, 1));
        jPanel2.setLayout(new java.awt.BorderLayout());

        PrefixT.setColumns(20);
        PrefixT.setEditable(false);
        jPanel2.add(PrefixT, java.awt.BorderLayout.CENTER);

        BrowseB.setText("Browse");
        BrowseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrowseBActionPerformed(evt);
            }
        });
        jPanel2.add(BrowseB, java.awt.BorderLayout.EAST);

        PrefixP.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.add(PrefixP, java.awt.BorderLayout.NORTH);

        CommandP.setLayout(new java.awt.BorderLayout());

        CommandL.setText("Use special launcher command (usually empty)");
        CommandL.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 4, 0));
        CommandP.add(CommandL, java.awt.BorderLayout.NORTH);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 24, 0, 1));
        jPanel1.setLayout(new java.awt.BorderLayout());

        CommandT.setColumns(20);
        jPanel1.add(CommandT, java.awt.BorderLayout.NORTH);

        SudoC.setSelected(true);
        SudoC.setText("Requires SUDO privileges");
        jPanel1.add(SudoC, java.awt.BorderLayout.CENTER);

        CommandP.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel4.add(CommandP, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BrowseBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrowseBActionPerformed
        File sel = new File(PrefixT.getText());
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setSelectedFile(sel);
        fc.ensureFileIsVisible(sel);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        disableNewFolderButton(fc);
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
            PrefixT.setText(fc.getSelectedFile().getPath());
    }//GEN-LAST:event_BrowseBActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Config.base.setPrefix(PrefixT.getText());
    }//GEN-LAST:event_formWindowClosed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BrowseB;
    private javax.swing.JLabel CommandL;
    private javax.swing.JPanel CommandP;
    private javax.swing.JTextField CommandT;
    private javax.swing.JLabel PrefixL;
    private javax.swing.JPanel PrefixP;
    private javax.swing.JTextField PrefixT;
    private javax.swing.JCheckBox SudoC;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables

    public static void fireDisplay() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                dialog.PrefixT.setText(Config.base.getPrefix());
                dialog.setVisible(true);
            }
        });
    }

    private void disableNewFolderButton(Container c) {
        int len = c.getComponentCount();
        for (int i = 0; i < len; i++) {
            Component comp = c.getComponent(i);
            if (comp instanceof JButton) {
                JButton b = (JButton) comp;
                if (b.getText().equals("New Folder"))
                    b.setVisible(false);
            } else if (comp instanceof Container)
                disableNewFolderButton((Container) comp);
        }
    }
}
