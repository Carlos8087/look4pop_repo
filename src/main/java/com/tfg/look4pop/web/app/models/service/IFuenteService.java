package com.tfg.look4pop.web.app.models.service;

import java.util.List;

import com.tfg.look4pop.web.app.models.dto.FuenteFormDTO;
import com.tfg.look4pop.web.app.models.entity.Fuente;

public interface IFuenteService {
	
	//public List<Fuente> getFuenteCensoByTpCensoLst(String tpCenso);
	public List<Fuente> getFuenteCensoByTipoLst(String tpCenso);
	public List<Fuente> getFuentePadronLst();
	public Fuente getAnioByTpFuente(FuenteFormDTO fuenteForm);
	public Integer insert(Fuente fuente);
	public Integer delete(Fuente fuente);

}
