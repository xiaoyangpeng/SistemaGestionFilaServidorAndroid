package com.incorporar;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;

import com.google.gson.Gson;

import dao.utils.BaseDao;
import tiempo.estimado.tiempoEstimado;
import variables.VariableSQL;

public class MandaInformacionCola  extends BaseDao{

	private BigDecimal id_cola;
	
	private BigDecimal id_usuario;
	
	private Socket misocket;
	
	
	private String turnoActual;
	
	private String miturno;

	private int tiempomediaturno;
	public MandaInformacionCola(Socket misocket,BigDecimal id_cola, BigDecimal id_usuario,String miturno) {
		this.misocket=misocket;
		this.id_cola = id_cola;
		this.id_usuario = id_usuario;
		this.miturno=miturno;
		tiempomediaturno=buscaTiempoMedia();
	}
	
		
	public void mandarJsonCola() {
			DataOutputStream out;
		
		try {
			
		 out=new DataOutputStream(misocket.getOutputStream());
			
			turnoActual=buscaTurnoActual();
			
			// mandar id_cola y id_usuario al usuario para facilitar gesiones 
			out.writeUTF(id_cola.toString());
			
			out.writeUTF(id_usuario.toString());
			
			out.writeUTF(miturno);
			
			String datos=crearJsonUsuario(turnoActual);
			// manda primera vez una vez que el usuario ha incorporado en la cola


			out.writeUTF(datos);
			
			
		while(!misocket.isClosed()) {
				
			 
				String turnoAux=buscaTurnoActual();
			
				// solo manda cuando ha cambiado su turno
				if(!turnoAux.equals(turnoActual)) {
					
					turnoActual=turnoAux;
					
					out.writeUTF(crearJsonUsuario(turnoActual));
					 
					out.flush();
				}
				
				
				if(turnoActual.equals(miturno)) {
					
					break;
				
				}
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
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
	
	/*
		public static void main(String[] args) {
			
			
			MandaInformacionCola col=new MandaInformacionCola(null,null);
			
			System.out.println(col.crearJsonUsuario());
		}
	*/
	
	public String crearJsonUsuario(String turnoActual) {
		
		int cuandoPersonaqueda=Integer.parseInt(miturno)-Integer.parseInt(turnoActual);
		
		
		InformacionColaJson cola=new InformacionColaJson((tiempomediaturno*cuandoPersonaqueda)*60,turnoActual,String.valueOf(cuandoPersonaqueda));
	
		Gson gosn=new Gson();
		
		return gosn.toJson(cola);
		
	}
	
	public String buscaTurnoActual() {
		
		
		//id_cola=new BigDecimal(1);

		return ((BigDecimal)queryForUnValor(VariableSQL.TURNO_ACTUAL_EN_COLA, id_cola)).toString();
		
	}

	public int buscaTiempoMedia(){


		String sql="select TIEMPOMEDIA from tienda\n" +
				"join cola on tienda.id_tienda=cola.id_tienda\n" +
				"\n" +
				"where id_cola=?";

		BigDecimal timepo=(BigDecimal)queryForUnValor(sql,id_cola);
		return
				Integer.parseInt(timepo.toString());

	}
	
	public String mandaQRtienda() {
		
	
		return (String)queryForUnValor(VariableSQL.CON_ID_COLA_BUSCA_QR,id_cola);
	}
	

	
}
