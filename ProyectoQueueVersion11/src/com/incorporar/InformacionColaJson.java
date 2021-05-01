package com.incorporar;

public class InformacionColaJson {

	
	private int timepo;
	
	
	private String turnoActual;

	

	private String turnoQueda;

	
	public InformacionColaJson(int timepo, String turnoActual,String turnoqueda) {
		this.timepo = timepo;
		this.turnoActual = turnoActual;
		this.turnoQueda=turnoqueda;

	}


	public int getTimepo() {
		return timepo;
	}


	public void setTimepo(int timepo) {
		this.timepo = timepo;
	}


	public String getTurnoActual() {
		return turnoActual;
	}


	public void setTurnoActual(String turnoActual) {
		this.turnoActual = turnoActual;
	}


	public String getTurnoQueda() {
		return turnoQueda;
	}


	public void setTurnoQueda(String turnoQueda) {
		this.turnoQueda = turnoQueda;
	}


	
	
	
}
