package com.tfg.look4pop.web.app.models.service;

import java.util.List;

import com.tfg.look4pop.web.app.models.entity.TipoTerritorio;

public interface ITipoTerritorioService {
	
	public TipoTerritorio getTipoTerritorioById(String idTt);
	public List<TipoTerritorio> getTipoTerritorioNivelLst();
	public List<TipoTerritorio> getTipoTerritorioAmbitoGenLst(String nivel);
}
