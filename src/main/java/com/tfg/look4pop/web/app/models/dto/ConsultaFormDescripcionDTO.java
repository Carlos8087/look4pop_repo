package com.tfg.look4pop.web.app.models.dto;

import java.io.Serializable;

public class ConsultaFormDescripcionDTO implements Serializable {

	private String nivelDesc;
	private String ambitoGenDesc;
	private String[] ambitosParDesc;
	private String ambitosParDescFormat;
	private String[] censoDerAniosDesc;
	private String censoDerAniosDescFormat;
	private String[] censoHecAniosDesc;
	private String censoHecAniosDescFormat;
	private String[] padronAniosDesc;
	private String padronAniosDescFormat;

	public String getNivelDesc() {
		return nivelDesc;
	}

	public void setNivelDesc(String nivelDesc) {
		this.nivelDesc = nivelDesc;
	}

	public String getAmbitoGenDesc() {
		return ambitoGenDesc;
	}

	public void setAmbitoGenDesc(String ambitoGenDesc) {
		this.ambitoGenDesc = ambitoGenDesc;
	}

	public String[] getAmbitosParDesc() {
		return ambitosParDesc;
	}

	public void setAmbitosParDesc(String[] ambitosParDesc) {
		this.ambitosParDesc = ambitosParDesc;
	}

	public String getAmbitosParDescFormat() {
		return ambitosParDescFormat;
	}

	public void setAmbitosParDescFormat(String ambitosParDescFormat) {
		this.ambitosParDescFormat = ambitosParDescFormat;
	}

	public String[] getCensoDerAniosDesc() {
		return censoDerAniosDesc;
	}

	public void setCensoDerAniosDesc(String[] censoDerAniosDesc) {
		this.censoDerAniosDesc = censoDerAniosDesc;
	}

	public String getCensoDerAniosDescFormat() {
		return censoDerAniosDescFormat;
	}

	public void setCensoDerAniosDescFormat(String censoDerAniosDescFormat) {
		this.censoDerAniosDescFormat = censoDerAniosDescFormat;
	}

	public String[] getCensoHecAniosDesc() {
		return censoHecAniosDesc;
	}

	public void setCensoHecAniosDesc(String[] censoHecAniosDesc) {
		this.censoHecAniosDesc = censoHecAniosDesc;
	}

	public String getCensoHecAniosDescFormat() {
		return censoHecAniosDescFormat;
	}

	public void setCensoHecAniosDescFormat(String censoHecAniosDescFormat) {
		this.censoHecAniosDescFormat = censoHecAniosDescFormat;
	}

	public String[] getPadronAniosDesc() {
		return padronAniosDesc;
	}

	public void setPadronAniosDesc(String[] padronAniosDesc) {
		this.padronAniosDesc = padronAniosDesc;
	}

	public String getPadronAniosDescFormat() {
		return padronAniosDescFormat;
	}

	public void setPadronAniosDescFormat(String padronAniosDescFormat) {
		this.padronAniosDescFormat = padronAniosDescFormat;
	}

	private static final long serialVersionUID = 1L;

}
