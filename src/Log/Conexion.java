package Log;

import java.sql.*; // * es el Connection para no estar a cada momento 
import javax.swing.JOptionPane;

public class Conexion {
    public static Connection conectar(){
        Connection conectar=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar= DriverManager.getConnection("jdbc:mysql://localhost:3307/arqui_user","root","");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarsee a la base de datos; ERROR: "+e.toString());
        }
        return conectar;
    }
}
