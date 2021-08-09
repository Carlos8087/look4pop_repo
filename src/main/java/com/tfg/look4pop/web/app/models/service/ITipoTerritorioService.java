package com.tfg.look4pop.web.app.models.service;

import java.util.List;

import com.tfg.look4pop.web.app.models.entity.TipoTerritorio;

public interface ITipoTerritorioService {
	
	public TipoTerritorio getTipoTerritorioById(String idTt);
	public List<TipoTerritorio> getTipoTerritorioNivelLst();
	public List<TipoTerritorio> getTipoTerritorioAmbitoGenLst(String nivel);
	
	/*
	public List<Futbolista> listar();
	public List<Futbolista> listarConLimites(MainFormDTO mainForm);
	public String saludo(String nombre);
	
	public Futbolista getFutbolistaById(Integer id);
	
	// Combos dependientes
	public List<Futbolista> getAllFutbolistas() throws Exception;
	public List<Coche> getAllCochesByFutbolista(Integer id) throws Exception;
	public List<Color> getAllColoresByCoche(Integer id);
	
	// Formulario
	public List<FutbolistaDataDTO> getFutbolistasByFormParams(MainFormDTO mainForm);
	*/

}
