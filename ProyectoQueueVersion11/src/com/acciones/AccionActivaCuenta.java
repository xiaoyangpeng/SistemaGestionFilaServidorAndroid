package com.acciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;

import dao.controlador.ControladorUsuarioLogin;


public class AccionActivaCuenta {


	JTextArea textPane;
	
	private DataInputStream entrada;

	private Socket misocket;
	
	private  String codigoActivacion;
	
	private String email;
	
	public AccionActivaCuenta(	JTextArea textPane,Socket misocket) {
		
		this.textPane=textPane;
		
		this.misocket=misocket;
	
	}
	
	public void actuar() {
		
		
		try {
			
			entrada=new DataInputStream(misocket.getInputStream());
	
			
			int tamano = entrada.readInt();
			
			byte[] bufferEmail = new byte[tamano];
			
			entrada.readFully(bufferEmail);
			
			email=new String(bufferEmail);
			
			
			tamano = entrada.readInt();
			
			byte[] bufferCodigo = new byte[tamano];
			
			entrada.readFully(bufferCodigo );
			
			codigoActivacion=new String(bufferCodigo);
			
			
			enviar();
		
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
	
	
	private void enviar() throws IOException {
		
		DataOutputStream salida=new DataOutputStream(misocket.getOutputStream());
		
		ControladorUsuarioLogin activarCuentaUsuario=new ControladorUsuarioLogin();
		
		if(activarCuentaUsuario.probarCodigoActivacion(email, codigoActivacion)) {
			
			salida.writeInt(1);
		
		}else {
	
			salida.writeInt(0);
		}
		

		misocket.close();
		
	}
	
}
