package com.incorporar;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.google.gson.Gson;

import com.IncorporarQR.controlador.ControladorIncorporarQR;

public class EscuchaOpcionProductos extends Thread{

		private DataInputStream reader;
	
		private Socket misocket;

		
		private ProductoMandaUsuario productoMandaUsuario;
		
		public EscuchaOpcionProductos(Socket misocket) {
			this.misocket = misocket;
		}
		
		
		@Override
		public void run() {
		// TODO Auto-generated method stub
	
			try {
				
				reader = new DataInputStream( misocket.getInputStream());
			

		        
		    while(misocket.isConnected()) {
		        	
			     Gson gson=new Gson();
			     
				String json=reader.readUTF();
		        
				
		
		        productoMandaUsuario=gson.fromJson(json, ProductoMandaUsuario.class);
		       
		        
		        ControladorIncorporarQR controladorIncorporarQR=new ControladorIncorporarQR();
		        
		        controladorIncorporarQR.usuario_anadir_productos(productoMandaUsuario);
		        
		    
		        }
		        
		        misocket.close();
		        
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			
				
			e.printStackTrace();
			}
			
			
		}
		
		
	
	
}
