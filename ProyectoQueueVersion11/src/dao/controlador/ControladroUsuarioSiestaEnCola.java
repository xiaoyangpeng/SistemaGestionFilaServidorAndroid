package dao.controlador;

import java.math.BigDecimal;

import dao.utils.BaseDao;
import variables.FehcaHoy;
import variables.VariableSQL;

public class ControladroUsuarioSiestaEnCola extends BaseDao{
	
	private String email;
	

	
	private BigDecimal id_usuario;
	
	private BigDecimal id_cola;
	public ControladroUsuarioSiestaEnCola(String email) {
		this.email = email;
	}
	
	
	/*public static void main(String[] args) {
		
		ControladroUsuarioSiestaEnCola controladroUsuarioSiestaEnCola=new ControladroUsuarioSiestaEnCola("8888");
		
		System.out.println(controladroUsuarioSiestaEnCola.estaEnlaCola());
		
		System.out.println(controladroUsuarioSiestaEnCola.buscarTurno());
		
	}
	*/
	
	
	public boolean estaEnlaCola() {
		
		buscaIdUsuario();
		
		 id_cola=(BigDecimal) queryForUnValor(VariableSQL.BUSCAR_SI_USUARIO_YA_ESTA_EN_COLA_CON_FECHA, FehcaHoy.diaHoy(),id_usuario);
		 
		 if(id_cola==null) {
			 
			 return false;
		 }
		 else {
			 
			 return true;
		 }
		
	}
	
	
	private void buscaIdUsuario() {
		
		
		id_usuario=(BigDecimal) queryForUnValor(VariableSQL.CON_EMAIL_BUSCAR_ID_USUARIO, email);
		
	}
	
	
	public String buscarTurno() {
		
		BigDecimal turno=(BigDecimal) queryForUnValor(VariableSQL.BUSCAR_TURNO_USUARIO, id_cola,id_usuario);
		
		return turno.toString();
		
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public BigDecimal getId_usuario() {
		return id_usuario;
	}


	public void setId_usuario(BigDecimal id_usuario) {
		this.id_usuario = id_usuario;
	}


	public BigDecimal getId_cola() {
		return id_cola;
	}


	public void setId_cola(BigDecimal id_cola) {
		this.id_cola = id_cola;
	}

	
	
	

}
