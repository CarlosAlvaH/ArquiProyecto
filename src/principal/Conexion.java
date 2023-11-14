package principal;

import java.sql.*;

public class Conexion {
    public static Connection conexionJDB() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3309/arquitectura", "root", "");
        return con;
    }
}
