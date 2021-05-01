package dao.controlador;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.impl.Cuenta_Usuario;
import dao.utils.BaseDao;
import variables.VariableSQL;

public class ControladorUsuarioLogin extends BaseDao {

	
	private Cuenta_Usuario cuenta_Usuario;

	

	/**
	 * 
	 * @return  -1 contraseña o email incorrecto
	  1   exito
	  2    no esta activado
	   3    ya esta en linea
	 */

	public int mandarInformacionAcceso(String email,int contrasena) {
		
		this.cuenta_Usuario= queryForOne(Cuenta_Usuario.class, VariableSQL.BUSCA_USUARIO, email);
		
		
		if(cuenta_Usuario!=null) {
			
				if(cuenta_Usuario.getContrasena()==contrasena) {
					
					
							if(cuenta_Usuario.getActivado()==1) {
								
										if(cuenta_Usuario.getEn_linea()==0) {
											
											return 1;
											
										}else {
											
											
											return 3;
										}
								
							}else {
								return 2;
								
							}
					
				}else {
					
					return -1;
				}
			
		}else {
			
			return -1;
		}
		
	}
	
/*
	public static void main(String[] args) {
		ControladorUsuarioLogin co=new ControladorUsuarioLogin();
		//Usuario u=;
		//System.out.println(co.mandarInformacion());
		
		//co.modificaUsuarioEnlinea(1,"8888");
		
		//co.probarCodigoActivacion("8888","1KpjghfSlm");
		


		co.cambairCodigoActivacion("8888", "gggggg");
	}*/
	
	public synchronized void modificaUsuarioEnlinea(int enlinea,String email) {
		
		update(VariableSQL.USAURIO_ENLINEA, enlinea,email);
		
	}
	
	
	public  boolean probarCodigoActivacion(String email,String codigoAcvacion) {
		
		String codigoaux=(String)queryForUnValor(VariableSQL.BUSCAR_CODIGO_ACTIVACION,email);
		
		if(codigoAcvacion.equals(codigoaux)) {
			
			activarCuenta(email);
			
			return true;
			
		}else return false;
		
		
	}
	
	private synchronized void activarCuenta(String email) {
		
		update(VariableSQL.ACTIVAR_LA_CUENTA, email);
		
	}
	
	
	
	public synchronized void cambairCodigoActivacion(String email,String codigo) {
		
		update(VariableSQL.ACTUALIZAR_CODIGO_ACTIVACION,codigo,email);
		
	}
	
	
	
}
