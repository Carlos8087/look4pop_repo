package com.tfg.look4pop.web.app.models.dto;

import java.io.Serializable;

public class PoblacionDataDTO implements Serializable {

	private String tipoTerritorio;
	private String nombre;
	private String codigoOficial;
	private String tipoFuente;
	//private String subtipoFuente;
	private String anio;
	private String poblacion;

	public String getTipoTerritorio() {
		return tipoTerritorio;
	}

	public void setTipoTerritorio(String tipoTerritorio) {
		this.tipoTerritorio = tipoTerritorio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoOficial() {
		return codigoOficial;
	}

	public void setCodigoOficial(String codigoOficial) {
		this.codigoOficial = codigoOficial;
	}

	public String getTipoFuente() {
		return tipoFuente;
	}

	public void setTipoFuente(String tipoFuente) {
		this.tipoFuente = tipoFuente;
	}

	/*
	public String getSubtipoFuente() {
		return subtipoFuente;
	}

	public void setSubtipoFuente(String subtipoFuente) {
		this.subtipoFuente = subtipoFuente;
	}
	*/

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	private static final long serialVersionUID = 1L;

}
