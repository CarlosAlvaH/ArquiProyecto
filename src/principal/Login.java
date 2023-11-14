/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package principal;

import java.awt.Color;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.util.logging.Level;
//import java.util.logging.Logger;
import static principal.Conexion.conexionJDB;


/**
 *
 * @author Julio
 */
public class Login extends javax.swing.JFrame {
    
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        this.getContentPane().setBackground(new Color(38,41,48));
    }

    public void getUsuario(String usuario){
        
        try {
            ResultSet rs = null;
            String sql = "SELECT * FROM usuario WHERE nom_user=?";
            
            Connection cn = conexionJDB();
            PreparedStatement pst = cn.prepareStatement(sql);
            
            pst.setString(1, usuario);
            
            rs = pst.executeQuery();
            while(rs.next()){
                String usu = rs.getString(1);
                String pass = rs.getString(2);
                
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JTextField textEmail = new javax.swing.JTextField();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JPasswordField txtPass = new javax.swing.JPasswordField();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JCheckBox jCheckBox1 = new javax.swing.JCheckBox();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JSeparator jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JSeparator jSeparator2 = new javax.swing.JSeparator();
        javax.swing.JSeparator jSeparator3 = new javax.swing.JSeparator();
        javax.swing.JSeparator jSeparator4 = new javax.swing.JSeparator();
        javax.swing.JButton jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(38, 41, 48));
        setName("frame()"); // NOI18N
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Logfin+.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 980, 612));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/icon_correo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 150, -1, 139));

        textEmail.setFont(new java.awt.Font("Dubai", 0, 24)); // NOI18N
        getContentPane().add(textEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 200, 278, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/icon_pass.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 320, 141, 132));

        txtPass.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        getContentPane().add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 380, 278, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Sign up");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1420, 560, -1, -1));

        jCheckBox1.setBackground(new java.awt.Color(38, 41, 48));
        jCheckBox1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Remember me");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 500, 149, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Lost your password");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 560, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(38, 41, 48));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 580, 154, 10));

        jSeparator2.setBackground(new java.awt.Color(38, 41, 48));
        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1420, 580, 60, 10));

        jSeparator3.setBackground(new java.awt.Color(38, 41, 48));
        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setToolTipText("");
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 100, 20, 420));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setAlignmentX(2.0F);
        jSeparator4.setAlignmentY(2.0F);
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 560, 20, 50));

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 640, 140, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        text
        try {
            ResultSet rs = null;
            String sql = "SELECT * FROM usuario WHERE nom_user=?";
            
            Connection cn = conexionJDB();
            PreparedStatement pst = cn.prepareStatement(sql);
            
            pst.setString(1, usuario);
            
            rs = pst.executeQuery();
            while(rs.next()){
                String usu = rs.getString(1);
                String pass = rs.getString(2);
                
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
