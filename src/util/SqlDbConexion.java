/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
public class SqlDbConexion {
    //METODO PARA INVOCAR EL USO DE LA LIBRERIA
	//=========================================
	static
	{
		try
		{Class.forName("com.mysql.jdbc.Driver");}
		catch (Exception e)
		{
			System.out.println("Error en el Driver"+e.getMessage());
		}
	}
	//METODO PARA CREAR LA CONEXION A LA BASE DE DATOS
	//================================================
	public static Connection getConnection(String db)
	{
		Connection connection=null;
		try {
			connection=
			DriverManager.getConnection("jdbc:mysql://localhost:3307/"+db,"root","");
		}catch (Exception e)
		{
			System.out.println("Error en la conexion:"+e.getMessage());
		}
		return connection;
	}
}
