package variables;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class hacerFicheroDeConfiguracion {

	
	/*public static final  String IP="localhost";
	
	
	public static final int puerto
	
	public static final String NOMBRE_SERVICIO="XE";
	
	
	public static final String USUARIO="";
	
	
	public static final String CONSTRASENA="";*/
	
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("sign".hashCode());
		
		
		Properties configuracion=new Properties();
		
		
		try {
			FileOutputStream salida=new FileOutputStream("FicheroConfiguracion/datosBBDD.conf");
			
			
	
			
		
			
			configuracion.put("IP", "192.168.31.146");
			
			configuracion.put("PUERTO", "1521");
			
			configuracion.put("NOMBRE_SERVICIO", "XE");
			
			
			configuracion.put("USUARIO", "C##ProyectoDam");
			
			configuracion.put("CONTRASENA","123456789");
			
			configuracion.store(salida, "Configuracion para conectar a base de dato");
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
