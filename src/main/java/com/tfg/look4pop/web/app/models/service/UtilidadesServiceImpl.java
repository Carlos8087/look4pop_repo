package com.tfg.look4pop.web.app.models.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UtilidadesServiceImpl implements IUtilidadesService {

	@Override
	public void activarOpcionMenu(Model model, Integer menuOpcion) {
		
		// Opciones por defecto
		model.addAttribute("clsInicio", "nav-item");
		model.addAttribute("clsConsultas", "nav-item dropdown");
		model.addAttribute("clsConsuForm", "dropdown-item");
		model.addAttribute("clsConsuFile", "dropdown-item");
		model.addAttribute("clsAdmin", "nav-item");
		
		String menuOpcionStr = ""; 
		switch(menuOpcion) {
			case 1:
				menuOpcionStr = (String) model.getAttribute("clsInicio");
				model.addAttribute("clsInicio", menuOpcionStr + " active");
			break;
			case 2:
				menuOpcionStr = (String) model.getAttribute("clsConsuForm");
				model.addAttribute("clsConsuForm", menuOpcionStr + " active");
				menuOpcionStr = (String) model.getAttribute("clsConsultas");
				model.addAttribute("clsConsultas", menuOpcionStr + " active");
			break;
			case 3:
				menuOpcionStr = (String) model.getAttribute("clsConsuFile");
				model.addAttribute("clsConsuFile", menuOpcionStr + " active");
				menuOpcionStr = (String) model.getAttribute("clsConsultas");
				model.addAttribute("clsConsultas", menuOpcionStr + " active");
			break;
			case 4:
				menuOpcionStr = (String) model.getAttribute("clsAdmin");
				model.addAttribute("clsAdmin", menuOpcionStr + " active");
				
		}
		
	}

}
