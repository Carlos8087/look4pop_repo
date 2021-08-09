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



	
	
	/*
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
		
		TipoTerritorio tipoTerritorio = tipoTerritorioMapper.getTipoTerritorioById(nivel);
		String perteneceA = tipoTerritorio.getPerteneceA();
		
		StringTokenizer st = new StringTokenizer(perteneceA, ",");
		
		while(st.hasMoreTokens()) {
			String terr = st.nextToken();
			perteneceALst.add(terr);
	    }
		
		ambitoGenLst = tipoTerritorioMapper.getTipoTerritorioAmbitoGenLst(perteneceALst);   
		
		return ambitoGenLst;
	}
	
	//@Autowired
	//private FutbolistaMapper futbolistaMapper;
	
	*/
	/*
	@Override
	public List<Futbolista> listar() {
		List<Futbolista> lista = futbolistaMapper.listar();
        return lista;
	}
	
	@Override
	public List<Futbolista> listarConLimites(MainFormDTO mainForm) {
		List<Futbolista> lista = futbolistaMapper.listarConLimites(mainForm);
        return lista;
	}

	@Override
	public String saludo(String nombre) {
		return "Hola: " + nombre;
	}

	@Override
	public Futbolista getFutbolistaById(Integer id) {
		Futbolista futbolista = futbolistaMapper.getFutbolistaById(id);
		return futbolista;
	}
	
	// Combos dependientes

	@Override
	public List<Futbolista> getAllFutbolistas() throws Exception {
		//Thread.sleep(5000);
		List<Futbolista> lista = futbolistaMapper.getAllFutbolistas();
        return lista;
	}

	@Override
	public List<Coche> getAllCochesByFutbolista(Integer id) throws Exception {
		//Thread.sleep(5000);
		List<Coche> lista = futbolistaMapper.getAllCochesByFutbolista(id);
        return lista;
	}

	@Override
	public List<Color> getAllColoresByCoche(Integer id) {
		List<Color> lista = futbolistaMapper.getAllColoresByCoche(id);
        return lista;
	}
	
	// Formulario
	@Override
	public List<FutbolistaDataDTO> getFutbolistasByFormParams(MainFormDTO mainForm) {
		List<FutbolistaDataDTO> lista = futbolistaMapper.getFutbolistasByFormParams(mainForm);
		return lista;
	}
	
	*/

}
