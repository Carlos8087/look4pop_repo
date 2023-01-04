package com.tfg.look4pop.web.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.look4pop.web.app.models.dto.FuenteFormDTO;
import com.tfg.look4pop.web.app.models.entity.Fuente;
import com.tfg.look4pop.web.app.models.mapper.IFuenteMapper;

@Service
public class FuenteServiceImpl implements IFuenteService {

	@Autowired
	private IFuenteMapper fuenteMapper;
	
	@Override
	public List<Fuente> getFuenteCensoByTipoLst(String tpCenso) {
		return fuenteMapper.getFuenteCensoByTipoLst(tpCenso);
	}

	@Override
	public List<Fuente> getFuentePadronLst() {
		List<Fuente> listado = new ArrayList<Fuente>();
		listado = fuenteMapper.getFuentePadronLst();
		return listado;
	}

	@Override
	public Fuente getAnioByTpFuente(FuenteFormDTO fuenteForm) {
		return fuenteMapper.getAnioByTpFuente(fuenteForm);
	}

	@Override
	public Integer insert(Fuente fuente) {
		return fuenteMapper.insert(fuente);
	}

	@Override
	public Integer delete(Fuente fuente) {
		return fuenteMapper.delete(fuente);
	}

}
