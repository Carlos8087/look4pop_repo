package com.tfg.look4pop.web.app.models.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class TipoTerritorio {

	private String idTt;
	private String nombre;
	private String comentarios;
	private String perteneceA;
	
	public TipoTerritorio() {
	}

	public TipoTerritorio(String idTt, String nombre, String comentarios, String perteneceA) {
		this.idTt = idTt;
		this.nombre = nombre;
		this.comentarios = comentarios;
		this.perteneceA = perteneceA;
	}

	@RemoteProperty
	public String getIdTt() {
		return idTt;
	}

	public void setIdTt(String idTt) {
		this.idTt = idTt;
	}

	@RemoteProperty
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RemoteProperty
	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	@RemoteProperty
	public String getPerteneceA() {
		return perteneceA;
	}

	public void setPerteneceA(String perteneceA) {
		this.perteneceA = perteneceA;
	}

}
