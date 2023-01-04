package com.tfg.look4pop.web.app.models.entity;

import java.sql.Timestamp;

public class Role {

	private Integer id;
	private Integer userId;
	private String authority;
	private String nbregtro;
	private Timestamp fhregtro;
	private String nblogact;
	private Timestamp fhultact;
	
	public Role() {
	}

	public Role(Integer id, Integer userId, String authority, String nbregtro, Timestamp fhregtro, String nblogact,
			Timestamp fhultact) {
		this.id = id;
		this.userId = userId;
		this.authority = authority;
		this.nbregtro = nbregtro;
		this.fhregtro = fhregtro;
		this.nblogact = nblogact;
		this.fhultact = fhultact;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getNbregtro() {
		return nbregtro;
	}

	public void setNbregtro(String nbregtro) {
		this.nbregtro = nbregtro;
	}

	public Timestamp getFhregtro() {
		return fhregtro;
	}

	public void setFhregtro(Timestamp fhregtro) {
		this.fhregtro = fhregtro;
	}

	public String getNblogact() {
		return nblogact;
	}

	public void setNblogact(String nblogact) {
		this.nblogact = nblogact;
	}

	public Timestamp getFhultact() {
		return fhultact;
	}

	public void setFhultact(Timestamp fhultact) {
		this.fhultact = fhultact;
	}

}
