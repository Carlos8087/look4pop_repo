package com.tfg.look4pop.web.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;
import com.tfg.look4pop.web.app.models.mapper.IPoblacionMapper;
import com.tfg.look4pop.web.app.models.mapper.ITerritorioMapper;

@Service
public class PoblacionServiceImpl implements IPoblacionService {

	@Autowired
	private IPoblacionMapper poblacionMapper;
	
	@Autowired
	private ITerritorioMapper territorioMapper; 

	@Override
	public List<PoblacionDataDTO> getPoblacionByMunicipioLst(ConsultaFormDTO consultaForm) {
		return poblacionMapper.getPoblacionByMunicipioLst(consultaForm);
	}
	
	@Override
	public List<PoblacionDataDTO> getPoblacionLst(ConsultaFormDTO consultaForm) {
		
		Integer[] territorioIdLst;
		territorioIdLst = territorioMapper.getTerritorioIdIncludedInAmbitoGenLst(consultaForm);
		
		consultaForm.setIdsTerritorioNivel(territorioIdLst);
		
		return poblacionMapper.getPoblacionLst(consultaForm);
	}

}
