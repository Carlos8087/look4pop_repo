package com.tfg.look4pop.web.app.models.mapper;

import java.util.List;

import com.tfg.look4pop.web.app.models.entity.TipoTerritorio;

public interface ITipoTerritorioMapper {
	
	public TipoTerritorio getTipoTerritorioById(String idTt);
	public List<TipoTerritorio> getTipoTerritorioNivelLst();
	public List<TipoTerritorio> getTipoTerritorioAmbitoGenLst(List<String> perteneceALst);
	
	/*
	public List<Futbolista> listar();
	public List<Futbolista> listarConLimites(MainFormDTO mainForm);
	
	public Futbolista getFutbolistaById(Integer id);
	
	// Combos dependientes
	public List<Futbolista> getAllFutbolistas();
	public List<Coche> getAllCochesByFutbolista(Integer id);
	public List<Color> getAllColoresByCoche(Integer id);
	
	// Formulario
	public List<FutbolistaDataDTO> getFutbolistasByFormParams(MainFormDTO mainForm);
	*/

}
