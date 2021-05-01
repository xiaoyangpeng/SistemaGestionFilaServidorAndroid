package dao.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtils {
	
	private Connection conexion;
	
	
	/**
	 * hacer la conecion 
	 * @return
	 */

	public static Connection getConeection() {
		
		ConectarBBDD conectarBBDD=new ConectarBBDD();
		
		return	conectarBBDD.conectarOracle();
		
	
	}
	
	
	public static void close(Connection conexion) {
		
		if(conexion!=null) {
			
			try {
				conexion.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
		}
		
	}

}
