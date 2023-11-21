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
    public boolean IniciarSesion(String usuario, String pass){
        String consulta = "SELECT nom_usu, pass_usu FROM usuario";
        boolean accesoConcedido = false;
        ResultSet rs = null;
        try {
            Usuario us = new Usuario();
            Connection cn = getConnection("arquitectura");
            PreparedStatement pst = cn.prepareStatement(consulta);
            rs = pst.executeQuery();
            while (rs.next()) {
                us.setUser(rs.getString(1));
                us.setPass(rs.getString(2)); 
                if (usuario.equals(us.getUser()) && pass.equals(us.getPass())) {
                JOptionPane.showMessageDialog(null, "Bienvenido");
                accesoConcedido=true;
            }
            }
            if (!accesoConcedido) {
                JOptionPane.showMessageDialog(null, "Acceso denegado");
                accesoConcedido=false;
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
        return accesoConcedido;
    }
    
    public void CargarUsuario(String usua){
        String consulta="SELECT CONCAT(nombre,' ', apellidos), foto_usu FROM usuario WHERE nom_usu=?";
        ResultSet rs=null;
        try{
            Connection cn=getConnection("arquitectura");
            PreparedStatement pst=cn.prepareStatement(consulta);
            Usuario us = new Usuario();
            usua = us.getUser();
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
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: "+e.toString());
        }
    }
}
