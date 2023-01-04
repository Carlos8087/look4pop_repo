package com.tfg.look4pop.web.app.models.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class Fuente {

	private String tipo;
	private String subtipo;
	private String anio;
	private String procedenciaDatos;
	
	public Fuente() {
	}

	public Fuente(String tipo, String subtipo, String anio, String procedenciaDatos) {
		this.tipo = tipo;
		this.subtipo = subtipo;
		this.anio = anio;
		this.procedenciaDatos = procedenciaDatos;
	}

	@RemoteProperty
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@RemoteProperty
	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

	@RemoteProperty
	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	@RemoteProperty
	public String getProcedenciaDatos() {
		return procedenciaDatos;
	}

	public void setProcedenciaDatos(String procedenciaDatos) {
		this.procedenciaDatos = procedenciaDatos;
	}

}
