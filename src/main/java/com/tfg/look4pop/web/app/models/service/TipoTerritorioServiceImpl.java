package com.tfg.look4pop.web.app.models.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.look4pop.web.app.models.entity.TipoTerritorio;
import com.tfg.look4pop.web.app.models.mapper.ITipoTerritorioMapper;

@Service
public class TipoTerritorioServiceImpl implements ITipoTerritorioService {

	@Autowired
	private ITipoTerritorioMapper tipoTerritorioMapper;
	
	@Override
	public TipoTerritorio getTipoTerritorioById(String idTt) {
		return tipoTerritorioMapper.getTipoTerritorioById(idTt);
	}
	
	@Override
	public List<TipoTerritorio> getTipoTerritorioNivelLst() {
		return tipoTerritorioMapper.getTipoTerritorioNivelLst();
	}

	@Override
	public List<TipoTerritorio> getTipoTerritorioAmbitoGenLst(String nivel) {
		List<TipoTerritorio> ambitoGenLst = null;
		List<String> perteneceALst = new ArrayList<String>();
		
		TipoTerritorio tipoTerritorio = getTipoTerritorioById(nivel);
		String perteneceA = tipoTerritorio.getPerteneceA();
		
		StringTokenizer st = new StringTokenizer(perteneceA, ",");
		
		while(st.hasMoreTokens()) {
			String terr = st.nextToken();
			perteneceALst.add(terr);
	    }
		
		ambitoGenLst = tipoTerritorioMapper.getTipoTerritorioAmbitoGenLst(perteneceALst);   
		
		return ambitoGenLst;
	}

}
