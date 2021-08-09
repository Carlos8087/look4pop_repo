package com.tfg.look4pop.web.app.models.mapper;

import java.util.List;

import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;

public interface IPoblacionMapper {
	
	public List<PoblacionDataDTO> getPoblacionByMunicipioLst(ConsultaFormDTO consultaForm);
	public List<PoblacionDataDTO> getPoblacionLst(ConsultaFormDTO consultaForm);
	
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
