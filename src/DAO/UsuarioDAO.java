package DAO;

import clases.Usuario;
import java.awt.Image;
import java.awt.Label;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static util.SqlDbConexion.getConnection;

public class UsuarioDAO {
    Usuario us = new Usuario();
    public Usuario IniciarSesion(String usuario, String pass){
        String consulta = "SELECT nom_usu, pass_usu FROM usuario";
        Usuario us = new Usuario();
        ResultSet rs = null;
        try {
        Connection cn = getConnection("arquitectura");
        PreparedStatement pst = cn.prepareStatement(consulta);
        rs = pst.executeQuery();

        while (rs.next()) {
            us.setUser(rs.getString(1));
            us.setPass(rs.getString(2));

            if (usuario.equals(us.getUser()) && pass.equals(us.getPass())) {
                JOptionPane.showMessageDialog(null, "Bienvenido");
                return us; // Devolver la instancia de Usuario1 si el inicio de sesión es exitoso
            }
        }

        // Si llega aquí, significa que el inicio de sesión falló
        JOptionPane.showMessageDialog(null, "Acceso denegado");
        return null;
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        return null; // Manejar cualquier excepción y devolver null en caso de error
    }
    }
    
    public Usuario CargarUsuario(String usua){
        String consulta="SELECT nombres, foto_usu FROM usuario WHERE nom_usu=?";
        ResultSet rs=null;
        Usuario us = new Usuario();
        try{
            Connection cn=getConnection("arquitectura");
            PreparedStatement pst=cn.prepareStatement(consulta);
            us.setUser(usua);
            pst.setString(1, usua);
            rs=pst.executeQuery();
            if(rs.next()){
                String usu=rs.getString(1);
                Blob img=(Blob) rs.getBlob(2);
                us.setNombre(usu);
                
                byte[] imgBytes = img.getBytes(1, (int) img.length());
                ImageIcon image = new ImageIcon(imgBytes);
                us.setFoto(image);
                // Escalado y ajuste a un cuadrado
                return us;
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: "+e.toString());
        }
        return null;
    }
}
