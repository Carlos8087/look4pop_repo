package com.tfg.look4pop.web.app.models.mapper;

import java.util.List;

import com.tfg.look4pop.web.app.models.entity.TipoTerritorio;

public interface ITipoTerritorioMapper {
	
	public TipoTerritorio getTipoTerritorioById(String idTt);
	public List<TipoTerritorio> getTipoTerritorioNivelLst();
	public List<TipoTerritorio> getTipoTerritorioAmbitoGenLst(List<String> perteneceALst);
}
