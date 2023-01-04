package com.tfg.look4pop.web.app.models.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class Territorio {

	private Integer idTerritorio;
	private String nombreActual;
	private String nombreCortoActual;
	private String tipoTerritorio;
	private String codigoOficial;
	private Integer e;
	private Integer r;
	private Integer p;
	private Integer m;
	private Integer mv;
	private Integer c;
	private Integer au;
	private Integer auv;
	private Integer i;
	private Integer zc;
	private Integer ac;
	private Integer du;
	private Integer pj;
	private Integer zf;
	private Double km2;
	private Double este;
	private Double oeste;
	private Double norte;
	private Double sur;
	private Integer x;
	private Integer y;
	private Integer capital;
	private byte activo;
	
	public Territorio() {
	}
	
	public Territorio(Integer idTerritorio, String nombreActual, String nombreCortoActual, String tipoTerritorio,
			String codigoOficial, Integer e, Integer r, Integer p, Integer m, Integer mv, Integer c, Integer au,
			Integer auv, Integer i, Integer zc, Integer ac, Integer du, Integer pj, Integer zf, Double km2, Double este,
			Double oeste, Double norte, Double sur, Integer x, Integer y, Integer capital, byte activo) {
		this.idTerritorio = idTerritorio;
		this.nombreActual = nombreActual;
		this.nombreCortoActual = nombreCortoActual;
		this.tipoTerritorio = tipoTerritorio;
		this.codigoOficial = codigoOficial;
		this.e = e;
		this.r = r;
		this.p = p;
		this.m = m;
		this.mv = mv;
		this.c = c;
		this.au = au;
		this.auv = auv;
		this.i = i;
		this.zc = zc;
		this.ac = ac;
		this.du = du;
		this.pj = pj;
		this.zf = zf;
		this.km2 = km2;
		this.este = este;
		this.oeste = oeste;
		this.norte = norte;
		this.sur = sur;
		this.x = x;
		this.y = y;
		this.capital = capital;
		this.activo = activo;
	}

	@RemoteProperty
	public Integer getIdTerritorio() {
		return idTerritorio;
	}

	public void setIdTerritorio(Integer idTerritorio) {
		this.idTerritorio = idTerritorio;
	}

	@RemoteProperty
	public String getNombreActual() {
		return nombreActual;
	}

	public void setNombreActual(String nombreActual) {
		this.nombreActual = nombreActual;
	}

	@RemoteProperty
	public String getNombreCortoActual() {
		return nombreCortoActual;
	}

	public void setNombreCortoActual(String nombreCortoActual) {
		this.nombreCortoActual = nombreCortoActual;
	}

	@RemoteProperty
	public String getTipoTerritorio() {
		return tipoTerritorio;
	}

	public void setTipoTerritorio(String tipoTerritorio) {
		this.tipoTerritorio = tipoTerritorio;
	}

	@RemoteProperty
	public String getCodigoOficial() {
		return codigoOficial;
	}

	public void setCodigoOficial(String codigoOficial) {
		this.codigoOficial = codigoOficial;
	}

	@RemoteProperty
	public Integer getE() {
		return e;
	}

	public void setE(Integer e) {
		this.e = e;
	}

	@RemoteProperty
	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	@RemoteProperty
	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	@RemoteProperty
	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	@RemoteProperty
	public Integer getMv() {
		return mv;
	}

	public void setMv(Integer mv) {
		this.mv = mv;
	}

	@RemoteProperty
	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	@RemoteProperty
	public Integer getAu() {
		return au;
	}

	public void setAu(Integer au) {
		this.au = au;
	}

	@RemoteProperty
	public Integer getAuv() {
		return auv;
	}

	public void setAuv(Integer auv) {
		this.auv = auv;
	}

	@RemoteProperty
	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	@RemoteProperty
	public Integer getZc() {
		return zc;
	}

	public void setZc(Integer zc) {
		this.zc = zc;
	}

	@RemoteProperty
	public Integer getAc() {
		return ac;
	}

	public void setAc(Integer ac) {
		this.ac = ac;
	}

	@RemoteProperty
	public Integer getDu() {
		return du;
	}

	public void setDu(Integer du) {
		this.du = du;
	}

	@RemoteProperty
	public Integer getPj() {
		return pj;
	}

	public void setPj(Integer pj) {
		this.pj = pj;
	}

	@RemoteProperty
	public Integer getZf() {
		return zf;
	}

	public void setZf(Integer zf) {
		this.zf = zf;
	}

	@RemoteProperty
	public Double getKm2() {
		return km2;
	}

	public void setKm2(Double km2) {
		this.km2 = km2;
	}

	@RemoteProperty
	public Double getEste() {
		return este;
	}

	public void setEste(Double este) {
		this.este = este;
	}

	@RemoteProperty
	public Double getOeste() {
		return oeste;
	}

	public void setOeste(Double oeste) {
		this.oeste = oeste;
	}

	@RemoteProperty
	public Double getNorte() {
		return norte;
	}

	public void setNorte(Double norte) {
		this.norte = norte;
	}

	@RemoteProperty
	public Double getSur() {
		return sur;
	}

	public void setSur(Double sur) {
		this.sur = sur;
	}

	@RemoteProperty
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	@RemoteProperty
	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	@RemoteProperty
	public Integer getCapital() {
		return capital;
	}

	public void setCapital(Integer capital) {
		this.capital = capital;
	}

	@RemoteProperty
	public byte getActivo() {
		return activo;
	}

	public void setActivo(byte activo) {
		this.activo = activo;
	}

}
