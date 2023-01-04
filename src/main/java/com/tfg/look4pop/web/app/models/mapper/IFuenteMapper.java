package com.tfg.look4pop.web.app.models.mapper;

import java.util.List;

import com.tfg.look4pop.web.app.models.dto.FuenteFormDTO;
import com.tfg.look4pop.web.app.models.entity.Fuente;

public interface IFuenteMapper {
	
	public List<Fuente> getFuenteCensoByTipoLst(String tpCenso);
	public List<Fuente> getFuentePadronLst();
	public Fuente getAnioByTpFuente(FuenteFormDTO fuenteForm);
	public Integer insert(Fuente fuente);
	public Integer delete(Fuente fuente);
}
