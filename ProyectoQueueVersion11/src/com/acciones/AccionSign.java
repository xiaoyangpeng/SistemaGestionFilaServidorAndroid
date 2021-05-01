package com.acciones;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import javax.swing.JTextArea;

import com.google.gson.Gson;

import dao.controlador.ControladorUsuarioSign;
import dao.controlador.UsuarioJson;

public class AccionSign {

	
	JTextArea textPane;

	private UsuarioJson usuario;

	private Socket misocket;
	
	BufferedReader entrada; 

	
	public  AccionSign(	JTextArea textPane,Socket misocket) {
		
		this.textPane=textPane;
		
		this.misocket=misocket;
	
	}
	
	
	
	public void sign() {
		
		try {
			
			entrada=new BufferedReader(new InputStreamReader(misocket.getInputStream(),"UTF-8"));
			
			StringBuffer datos=new StringBuffer();
			
			String linea;
		
			while((linea=entrada.readLine())!=null) {
			
				datos.append(linea);
			
			}
		
		
			enviar(datos.toString());
		
		} catch (IOException e) {
			
			try {
				misocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		
		
	}
	
	
	private void enviar(String datos) throws IOException {
		
		
		Gson gosn=new Gson();
		
		usuario=gosn.fromJson(datos, UsuarioJson.class);
	
		DataOutputStream salida=new DataOutputStream(misocket.getOutputStream());
		
		ControladorUsuarioSign sign=new ControladorUsuarioSign(usuario);
	
		if(sign.siExisteEmail()) {
			
			salida.writeBoolean(true);

		}else {
	
			salida.writeBoolean(false);
			
		}
		
		sign.cerrarConexion();
		
		misocket.close();
		

	}
	
	
	
}
