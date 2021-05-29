package com.acciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.incorporar.MandaInformacionCola;

import dao.controlador.ControladroUsuarioSiestaEnCola;

public class AccionSiEstaDentrodeCola {
	
	
private Socket misocket;
	
	private DataInputStream entrada;
	
	private String email;
	
	public AccionSiEstaDentrodeCola(Socket misocket) {
		this.misocket = misocket;
	}
	
	
	public void actuar() {
		
		
		
		try {
			
			entrada=new DataInputStream(misocket.getInputStream());
			
			
			
			int tamano = entrada.readInt();
			
			byte[] buffer = new byte[tamano];
			
			entrada.readFully(buffer);
			
			email=new String(buffer);
			
			
			envairRespuesta();
			
			
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
	
	
	public void envairRespuesta() throws IOException {
		

		DataOutputStream out=new DataOutputStream(misocket.getOutputStream());
		
		ControladroUsuarioSiestaEnCola controladroUsuarioSiestaEnCola=new ControladroUsuarioSiestaEnCola(email);
		
		
		if(controladroUsuarioSiestaEnCola.estaEnlaCola()) {
			
			out.writeUTF("true");
			
				String miturno=controladroUsuarioSiestaEnCola.buscarTurno();
			
					System.out.println("fffffffffffffffffffff");
				// entra en bucle para mandar informacion acerca de cola 
				//siempre y cuando el socket esta conectado
			
				MandaInformacionCola informacion=new MandaInformacionCola(misocket,controladroUsuarioSiestaEnCola.getId_cola(), controladroUsuarioSiestaEnCola.getId_usuario(),miturno);
				
				// manda qr como el usuario 
				// ha incorporardo otra vez de automaticamente en fila
				// por lo tanto no tiene su codigo qr
				// para luego solicitar productos a la tienda
				
				out.writeUTF(informacion.mandaQRtienda());
				
			
				informacion.mandarJsonCola();
			
			
		}else {
			
			out.writeUTF("false");
			out.close();
			misocket.close();
			
		}
		
		
	}
	

}
