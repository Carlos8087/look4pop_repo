package com.tfg.look4pop.web.app.models.service;

import java.util.List;

import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;

public interface IPoblacionService {
	
	public List<PoblacionDataDTO> getPoblacionByMunicipioLst(ConsultaFormDTO consultaForm);
	public List<PoblacionDataDTO> getPoblacionLst(ConsultaFormDTO consultaForm);

}
