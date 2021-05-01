package com.acciones;

import java.io.IOException;
import java.net.Socket;

import dao.controlador.ControladorUsuarioLogin;

public class CuentaAtras extends Thread {

	
	private int numero=5;
	
	private ControladorUsuarioLogin login;
	
	private String email;
	private Socket socket;
	public CuentaAtras( Socket socket,ControladorUsuarioLogin login,String email) {
		// TODO Auto-generated constructor stub
		
		this.login=login;
		this.email=email;
		
		this.socket=socket;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(numero>=0) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			numero--;

		}
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		login.modificaUsuarioEnlinea(0,email);
		
		System.out.println("Usuario "+email+" ha perdido su conexion ");
		
	}



	public int getNumero() {
		return numero;
	}



	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
	
}
