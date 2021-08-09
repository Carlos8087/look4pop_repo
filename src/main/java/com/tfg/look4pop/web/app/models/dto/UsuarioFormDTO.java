package com.tfg.look4pop.web.app.models.dto;

import java.io.Serializable;

public class UsuarioFormDTO implements Serializable {

	private String idUsuarioSelect;
	private String username;
	private String password;
	private String enabled;
	private String tpAccion;

	public String getIdUsuarioSelect() {
		return idUsuarioSelect;
	}

	public void setIdUsuarioSelect(String idUsuarioSelect) {
		this.idUsuarioSelect = idUsuarioSelect;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getTpAccion() {
		return tpAccion;
	}

	public void setTpAccion(String tpAccion) {
		this.tpAccion = tpAccion;
	}

	private static final long serialVersionUID = 1L;

}
