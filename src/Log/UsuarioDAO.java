package Log;

import java.awt.Image;
import java.awt.Label;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static Log.Conexion.conectar;

public class UsuarioDAO {
    Usuario1 us = new Usuario1();
    public Usuario1 IniciarSesion(String usuario, String pass){
        String consulta = "SELECT nom_usu, pass_usu FROM usuario";
        ResultSet rs = null;
         Usuario1 us = new Usuario1(); // Crear una nueva instancia de Usuario1
    try {
        Connection cn = conectar();
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
    
    public Usuario1 CargarUsuario(String usua){
        String consulta="SELECT nombres, foto_usu FROM usuario WHERE nom_usu=?";
        ResultSet rs=null;
         Usuario1 us = new Usuario1();
        try{
            Connection cn=conectar();
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
