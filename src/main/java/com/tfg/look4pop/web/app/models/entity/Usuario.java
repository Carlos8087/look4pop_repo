package com.tfg.look4pop.web.app.models.entity;

import java.sql.Timestamp;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class Usuario {

	private Integer id;
	private String username;
	private String password;
	private Integer enabled;
	private String nbregtro;
	private Timestamp fhregtro;
	private String nblogact;
	private Timestamp fhultact;
	
	public Usuario() {
	}

	public Usuario(Integer id, String username, String password, Integer enabled, String nbregtro, Timestamp fhregtro,
			String nblogact, Timestamp fhultact) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.nbregtro = nbregtro;
		this.fhregtro = fhregtro;
		this.nblogact = nblogact;
		this.fhultact = fhultact;
	}

	@RemoteProperty
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RemoteProperty
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@RemoteProperty
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@RemoteProperty
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@RemoteProperty
	public String getNbregtro() {
		return nbregtro;
	}

	public void setNbregtro(String nbregtro) {
		this.nbregtro = nbregtro;
	}

	@RemoteProperty
	public Timestamp getFhregtro() {
		return fhregtro;
	}

	public void setFhregtro(Timestamp fhregtro) {
		this.fhregtro = fhregtro;
	}

	@RemoteProperty
	public String getNblogact() {
		return nblogact;
	}

	public void setNblogact(String nblogact) {
		this.nblogact = nblogact;
	}

	@RemoteProperty
	public Timestamp getFhultact() {
		return fhultact;
	}

	public void setFhultact(Timestamp fhultact) {
		this.fhultact = fhultact;
	}

}
