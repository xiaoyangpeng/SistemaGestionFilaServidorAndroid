package com.IncorporarQR;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.IncorporarQR.controlador.ControladorIncorporarQR;
import com.incorporar.EscuchaOpcionProductos;
import com.incorporar.MandaInformacionCola;

public class IncorporarQR {

	
	private Socket misocket;
	
	private DataInputStream entrada;
	
	private String email;
	
	private String qr;
	
	private ControladorIncorporarQR controladorIncorporarQR;
	
	public IncorporarQR(Socket misocket) {
		this.misocket = misocket;
	}
	
	
	public void actuar() {
		
		
		
		try {
			
			entrada=new DataInputStream(misocket.getInputStream());
			
			
			
			int tamano = entrada.readInt();
			
			byte[] buffer = new byte[tamano];
			
			entrada.readFully(buffer);
			
			email=new String(buffer);
			
			
			
			tamano=entrada.readInt();
			
			
			byte[] buffer2 = new byte[tamano];
			
			entrada.readFully(buffer2);
			
			
			qr=new String(buffer2);
			
		
			
			envairRespuestaQR();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				misocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
	}
	
    /**
    *
    * @return -1: error QR
    * 1 QR correcto
    * 2 ya esta dentro de la cola
    */
	
	private void envairRespuestaQR() throws IOException{
		
		controladorIncorporarQR=new ControladorIncorporarQR(email, qr);
		
		DataOutputStream out=new DataOutputStream(misocket.getOutputStream());

		
		int respuesta=controladorIncorporarQR.siExisteQR();
		
		out.writeInt(respuesta);
		
		if(respuesta==1) {
			
			   // coger el turno 
			String miturno=controladorIncorporarQR.incorporar();
		
			// arranca un hilo para esuchar los productos que eligra por el cliente
			EscuchaOpcionProductos escuchaOpcionProductos=new EscuchaOpcionProductos(misocket);
				
			escuchaOpcionProductos.start();
	
			// entra en bucle para mandar informacion acerca de cola 
			//siempre y cuando el socket esta conectado
			MandaInformacionCola informacion=new MandaInformacionCola(misocket,controladorIncorporarQR.getId_cola(), controladorIncorporarQR.getId_usuario(),miturno);
			
			informacion.mandarJsonCola();
			
		}else {
			
			out.close();
			misocket.close();
		}
	
	}
	
	
	
	
	
}
