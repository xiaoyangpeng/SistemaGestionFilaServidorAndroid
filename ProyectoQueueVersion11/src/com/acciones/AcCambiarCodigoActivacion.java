package com.acciones;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;


import com.mandarEmail.Email;

import dao.controlador.ControladorUsuarioLogin;
import oracle.net.aso.c;
import oracle.net.aso.m;
import variables.GeneraCodigo;

public class AcCambiarCodigoActivacion {

	JTextArea textPane;


	private Socket misocket;
	
	private DataInputStream entrada;
	
	private String email;
	
	private String codigo;
	
	
	public AcCambiarCodigoActivacion(JTextArea textPane, Socket misocket) {
		
		this.textPane=textPane;
		
		this.misocket=misocket;
	
	}
		
	public void actuar() {
		
		
		try {
			entrada=new DataInputStream(misocket.getInputStream());
			
			int tamano = entrada.readInt();
			
			byte[] buffer = new byte[tamano];
			
			entrada.readFully(buffer);
			
			email=new String(buffer);
			
			cambiar();
			
			Email emailmantar=new Email(email, codigo);
			
			emailmantar.mantar();
			
			misocket.close();
			
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
	
	private void cambiar() throws IOException {
		
		
		codigo=GeneraCodigo.LetraAleatoria();
		
		ControladorUsuarioLogin controladorUsuarioLogin=new ControladorUsuarioLogin();
		
		controladorUsuarioLogin.cambairCodigoActivacion(email, codigo);

		misocket.close();

	
	}
	
	
}
