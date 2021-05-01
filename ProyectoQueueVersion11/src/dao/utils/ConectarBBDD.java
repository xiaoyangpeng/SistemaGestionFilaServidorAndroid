package dao.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConectarBBDD  {
	
	
private  Connection conexion;
	


public Connection conectarOracle() {
		
		try {
		
			FileInputStream fileEntrada=new FileInputStream("FicheroConfiguracion/datosBBDD.conf");
			
			Properties entrada=new Properties();

			entrada.load(fileEntrada);
			
		

			conectar(entrada.getProperty("IP"), entrada.getProperty("PUERTO"), 
					entrada.getProperty("NOMBRE_SERVICIO"), entrada.getProperty("USUARIO"), 
					entrada.getProperty("CONTRASENA"));
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conexion;
	 }
	
	
	
 private  void  conectar(String ip,String puerto , String nombreServicio,String Usuario,String Contrasena) {
		  
		  try {
			  
			  	Class.forName("oracle.jdbc.OracleDriver");
	
			  	String db="jdbc:oracle:thin:@"+ip+":"+puerto+":"+nombreServicio;
			  
			  	conexion=DriverManager.getConnection(db,Usuario,Contrasena);
			  
			  	if(conexion!=null) {
			  		
			  		System.out.println("Conexion bbdd correcta");
			  	}
			  	else {
			  		
			  		System.out.println("Fallo conexion");
			  	}
			  
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		  
	  }

}
