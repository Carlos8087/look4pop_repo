package com.tfg.look4pop.web.app.models.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.entity.Territorio;
import com.tfg.look4pop.web.app.models.mapper.ITerritorioMapper;

@Service
public class TerritorioServiceImpl implements ITerritorioService {

	@Autowired
	private ITerritorioMapper territorioMapper;

	@Override
	public Territorio getTerritorioById(String idTerritorio) {
		return territorioMapper.getTerritorioById(idTerritorio);
	}
	
	@Override
	public List<String> getTerritorioIdByNivelAndAmbitoGenLst(Map<String, String> territorioIdLstQueryParms) {
		return territorioMapper.getTerritorioIdByNivelAndAmbitoGenLst(territorioIdLstQueryParms);
	}

	@Override
	public List<Territorio> getTerritorioAmbitoParLst(String nivel, String ambitoGen) {
		List<Territorio> ambitoParLst = null;
		List<String> territorioIdLst = new ArrayList<String>();
		
		Map<String, String> territorioIdLstQueryParms = new HashMap<String, String>();
		territorioIdLstQueryParms.put("nivel", nivel);
		territorioIdLstQueryParms.put("ambitoGen", ambitoGen);
		
		territorioIdLst = territorioMapper.getTerritorioIdByNivelAndAmbitoGenLst(territorioIdLstQueryParms);
		ambitoParLst = territorioMapper.getTerritorioAmbitoParLst(territorioIdLst);
		
		return ambitoParLst;
	}

	@Override
	public Integer[] getTerritorioIdIncludedInAmbitoGenLst(ConsultaFormDTO consultaForm) {
		return territorioMapper.getTerritorioIdIncludedInAmbitoGenLst(consultaForm);
	}

}
