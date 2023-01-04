package com.tfg.look4pop.web.app.models.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.entity.Territorio;

public interface ITerritorioService {
	
	public Territorio getTerritorioById(String idTerritorio);
	public List<String> getTerritorioIdByNivelAndAmbitoGenLst(@Param("params") Map<String, String> params);
	public List<Territorio> getTerritorioAmbitoParLst(String nivel, String ambitoGen);
	public Integer[] getTerritorioIdIncludedInAmbitoGenLst(ConsultaFormDTO consultaForm);
}
