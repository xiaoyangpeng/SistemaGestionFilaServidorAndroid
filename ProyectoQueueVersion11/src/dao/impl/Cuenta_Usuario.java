package dao.impl;


public class Cuenta_Usuario {

	
	private String email;
	
	private int id_usuario;
	
	private int contrasena;
	
	private int activado;
	
	private String codigo_activacion;
	
	private int en_linea;



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getContrasena() {
		return contrasena;
	}

	public void setContrasena(int contrasena) {
		this.contrasena = contrasena;
	}

	public int getActivado() {
		return activado;
	}

	public void setActivado(int activado) {
		this.activado = activado;
	}

	public String getCodigo_activacion() {
		return codigo_activacion;
	}

	public void setCodigo_activacion(String codigo_activacion) {
		this.codigo_activacion = codigo_activacion;
	}

	public int getEn_linea() {
		return en_linea;
	}

	public void setEn_linea(int en_linea) {
		this.en_linea = en_linea;
	}

	
	
	

}
