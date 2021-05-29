package com.acciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JTextArea;


import dao.controlador.ControladorUsuarioLogin;
import token.JwtUtil;
import variables.VariableSQL;

public class AccionLogin {

	JTextArea textPane;

	private String email;
	

	private int contrasena;

	private DataInputStream entrada;

	private Socket misocket;
	
	private ControladorUsuarioLogin login;
	
	public AccionLogin(	JTextArea textPane,Socket misocket) {
		
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
		
		contrasena=entrada.readInt();

		enviar();
	
		
		} catch (Exception e) {
		
	// como ha perdido conexion con usuario hay que dar baja en linea 
			
			login.modificaUsuarioEnlinea(0,email);
			
			System.out.println("ssssssUsuario "+email+" ha perdido su conexion ");
		}
	}
	
	
	DataOutputStream salida;
	
	private void enviar()   {

		 try {
			
			salida=new DataOutputStream(misocket.getOutputStream());
			
			login=new ControladorUsuarioLogin();
			
			int correcto=login.mandarInformacionAcceso(email, contrasena);
			
			//-1 no puede accerder
			// 1 exito
			// 2 no esta activado
			// 3 ya esta en liena
			salida.writeInt(correcto);
			
			if(correcto==1) {
				
				// modifcar el usaurio en linea
				login.modificaUsuarioEnlinea(1,email);


				String token= JwtUtil.crearToken(email,login.buscarIdUsuario(email));

				salida.writeUTF(token);

				// en el momento de que el usuario accede con su cuenta
				// empiza contar atras 
				// si dentro de 10 segundo no hay respuesta del usuario corta socket 
				// y dejar usuario no esta en la linea
		
				CuentaAtras cuentaAtras=new CuentaAtras(misocket,login,email);
				
				cuentaAtras.start();
				
				while(cuentaAtras.getNumero()>0) {
					
					int nuemro=entrada.readInt();
				
					cuentaAtras.setNumero(nuemro);

					}

				
			}else {
			
				misocket.close();
			}
			
			} catch (IOException e) {
	
				e.printStackTrace();
				
				login.modificaUsuarioEnlinea(0,email);
				
				System.out.println("Usuario "+email+" ha perdido su conexion por excepcion");
			}
		
			
		}
		
	


	
	
	
	
}
