package com.tfg.look4pop.web.app.models.dto;

import java.io.Serializable;

public class FuenteFormDTO implements Serializable {

	private String tipo;
	private String subtipo;
	private String anioC;
	private String anioP;
	private String procedenciaDatos;
	private String tpAccion;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

	public String getAnioC() {
		return anioC;
	}

	public void setAnioC(String anioC) {
		this.anioC = anioC;
	}

	public String getAnioP() {
		return anioP;
	}

	public void setAnioP(String anioP) {
		this.anioP = anioP;
	}

	public String getProcedenciaDatos() {
		return procedenciaDatos;
	}

	public void setProcedenciaDatos(String procedenciaDatos) {
		this.procedenciaDatos = procedenciaDatos;
	}

	public String getTpAccion() {
		return tpAccion;
	}

	public void setTpAccion(String tpAccion) {
		this.tpAccion = tpAccion;
	}

	private static final long serialVersionUID = 1L;

}
