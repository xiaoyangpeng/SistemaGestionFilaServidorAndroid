package variables;

import java.text.SimpleDateFormat;
import java.util.Date;



public class FehcaHoy {


	public static String diaHoy() {
		
		
		Date now = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		
		return sdf.format(now);
		
	}
	
	public static String horaNow() {
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
		
		return sdf.format(now);
		
	}
	
	
}
