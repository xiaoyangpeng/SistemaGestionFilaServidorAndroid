package ConectarConMovil;

import java.io.DataInputStream;

import java.io.IOException;

import java.net.Socket;

import javax.swing.JTextArea;

import com.acciones.AccionActivaCuenta;
import com.acciones.AcCambiarCodigoActivacion;
import com.acciones.AccionLogin;
import com.acciones.AccionSiEstaDentrodeCola;
import com.acciones.AccionSign;
import com.IncorporarQR.IncorporarQR;


import variables.VariableRealizarOperacion;

public class ControladorServidor extends Thread {

	
	Socket misocket;
	
	JTextArea textPane;
	
	

	DataInputStream entrada;

	
	public ControladorServidor(	Socket misocket,JTextArea textPane) {

		this.misocket=misocket;
		this.textPane=textPane;
	
	
		
	}
	@Override
	public void run() {

		
	try {
		
		
		
		leerMensaje();
		

		}catch(IOException e)
		{
			
			try {
				misocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	
	public void leerMensaje() throws IOException {
		
		
		entrada=new DataInputStream(misocket.getInputStream());
		
		if(misocket.isConnected()) {

			
			// 103149417 es login
			// 3530173 es sign
			int queOperacion=entrada.readInt();
			
			switch (queOperacion) {
			
			case VariableRealizarOperacion.LOGIN:
				
					textPane.append("\n"+"Login");
					AccionLogin accionLogin=new AccionLogin(textPane, misocket);
					
					accionLogin.actuar();
			break;

				
			case VariableRealizarOperacion.SIGN:
			
					textPane.append("\n"+"Sign");
					
					AccionSign accionSign=new AccionSign(textPane, misocket);
					
					accionSign.sign();
					
			break;
			
			
			case VariableRealizarOperacion.ACTIVARCUENTA:
				
				textPane.append("\n"+"Activa cuenta");
				
				AccionActivaCuenta accionActivaCuenta=new AccionActivaCuenta(textPane, misocket);
				
				accionActivaCuenta.actuar();
			
			break;
			
			
			case VariableRealizarOperacion.CAMBIARCODIGOACITVACION:
			
				textPane.append("\n"+"Camiar codigo activacion");
				
				AcCambiarCodigoActivacion acCambiarCodigoActivacion =new AcCambiarCodigoActivacion(textPane, misocket);
				
				acCambiarCodigoActivacion.actuar();
			break;
			
			case VariableRealizarOperacion.INCORPORARQR:
				

				textPane.append("\n"+"incorporarQR");
				
				
				IncorporarQR  accioneIncorporarQR=new IncorporarQR(misocket);
				
				accioneIncorporarQR.actuar();
	
			break;
			
			case VariableRealizarOperacion.SIESTAENLACOLA:
				
				textPane.append("\n"+"Buscar si esta en la cola");
				
				AccionSiEstaDentrodeCola accionSiEstaDentrodeCola=new AccionSiEstaDentrodeCola(misocket);
				
				accionSiEstaDentrodeCola.actuar();
				
				
				break;




			default:
				break;
			}
			
			
			
		}
	}
	
	
	
	
	
	
	
}
