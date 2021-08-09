package com.tfg.look4pop.web.app.models.dto;

import java.io.Serializable;

public class ConsultaFormDTO implements Serializable {

	// Campos mapeo formulario
	private String nivel;
	private String ambitoGen;
	private String[] ambitosPar;
	private String[] tpsFuente;
	private String[] anios;

	// private String[] tpsCenso; // Censo de derecho - Censo de hecho
	private String[] censoDerAnios;
	private String[] censoHecAnios;
	private String[] padronAnios;

	private Integer[] idsTerritorioNivel; // Definido para 'Nivel' distinto a 'Municipio'
	private Integer numElementAct; // Numero actual de elementos cargados en pantalla
	private Integer numMaxRegs; // Numero maximo de registros a incluir en cada carga
	private Integer numTotRegs; // Numero total de registros devueltos en la consulta

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getAmbitoGen() {
		return ambitoGen;
	}

	public void setAmbitoGen(String ambitoGen) {
		this.ambitoGen = ambitoGen;
	}

	public String[] getAmbitosPar() {
		return ambitosPar;
	}

	public void setAmbitosPar(String[] ambitosPar) {
		this.ambitosPar = ambitosPar;
	}

	public String[] getTpsFuente() {
		return tpsFuente;
	}

	public void setTpsFuente(String[] tpsFuente) {
		this.tpsFuente = tpsFuente;
	}

	public String[] getAnios() {
		return anios;
	}

	public void setAnios(String[] anios) {
		this.anios = anios;
	}

	/*
	 * public String[] getTpsCenso() { return tpsCenso; }
	 * 
	 * public void setTpsCenso(String[] tpsCenso) { this.tpsCenso = tpsCenso; }
	 */

	public String[] getCensoDerAnios() {
		return censoDerAnios;
	}

	public void setCensoDerAnios(String[] censoDerAnios) {
		this.censoDerAnios = censoDerAnios;
	}

	public String[] getCensoHecAnios() {
		return censoHecAnios;
	}

	public void setCensoHecAnios(String[] censoHecAnios) {
		this.censoHecAnios = censoHecAnios;
	}

	public String[] getPadronAnios() {
		return padronAnios;
	}

	public void setPadronAnios(String[] padronAnios) {
		this.padronAnios = padronAnios;
	}

	public Integer[] getIdsTerritorioNivel() {
		return idsTerritorioNivel;
	}

	public void setIdsTerritorioNivel(Integer[] idsTerritorioNivel) {
		this.idsTerritorioNivel = idsTerritorioNivel;
	}

	public Integer getNumElementAct() {
		return numElementAct;
	}

	public void setNumElementAct(Integer numElementAct) {
		this.numElementAct = numElementAct;
	}

	public Integer getNumMaxRegs() {
		return numMaxRegs;
	}

	public void setNumMaxRegs(Integer numMaxRegs) {
		this.numMaxRegs = numMaxRegs;
	}

	public Integer getNumTotRegs() {
		return numTotRegs;
	}

	public void setNumTotRegs(Integer numTotRegs) {
		this.numTotRegs = numTotRegs;
	}

	private static final long serialVersionUID = 1L;

}
