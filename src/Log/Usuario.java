package Log;

//import com.mysql.cj.jdbc.Blob;

import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static Log.Conexion.conectar;
import java.sql.Blob;

public class Usuario extends javax.swing.JFrame {
    
    Usuario1 us = new Usuario1();
    UsuarioDAO consult_usu = new UsuarioDAO();
    public Usuario(String u) {
        initComponents();
        this.getContentPane().setBackground(new Color(38,41,48));
        us.setUser(u);
        cargarUser();
        
    }
    
    public void cargarUser() {
        Usuario1 use = consult_usu.CargarUsuario(us.getUser());
        
        if (use != null) {
        ImageIcon image = (ImageIcon) use.getFoto();
        int tamano = Math.min(lblFoto.getWidth(), lblFoto.getHeight());
        Image escalaImage = image.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
        ImageIcon nuevaImg = new ImageIcon(escalaImage);
        lblFoto.setIcon(nuevaImg);

        txt_user.setText(use.getNombre());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFoto = new javax.swing.JLabel();
        txt_user = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_user.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Brigada Salvaje", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("BIENVENIDO");

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(31, 31, 31))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    public void CargarUsuario(String usua){
        String consulta="SELECT nombres, foto_usu FROM usuario WHERE nom_usu=?";
        ResultSet rs=null;
        try{
            Connection cn=conectar();
            PreparedStatement pst=cn.prepareStatement(consulta);
            pst.setString(1, usua);
            rs=pst.executeQuery();
            if(rs.next()){
                String usu=rs.getString(1);
                Blob img=(Blob) rs.getBlob(2);
                
                txt_user.setText(usu);
                
                byte[] imgBytes = img.getBytes(1, (int) img.length());
                ImageIcon image = new ImageIcon(imgBytes);
                
                // Escalado y ajuste a un cuadrado
                int tamano = Math.min(lblFoto.getWidth(), lblFoto.getHeight());
                Image escalaImage = image.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
                ImageIcon nuevaImg = new ImageIcon(escalaImage);
                lblFoto.setIcon(nuevaImg);
                this.repaint();
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: "+e.toString());
        }
    }
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
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables
}
