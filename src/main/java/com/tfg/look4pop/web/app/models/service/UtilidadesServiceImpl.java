package com.tfg.look4pop.web.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

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
	

	/*
	private final static String UPLOADS_FOLDER = "C://Look4Pop//App//QueryForms//Uploads//";
	private final static String DOWNLOADS_FOLDER = "C://Look4Pop//App//QueryForms//Downloads//";

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) { // El recurso no existe o no es leible
			throw new RuntimeException("Error: no se puede cargar la imagen '" + pathFoto.toString() + "'");
		}

		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) { // El archivo existe y se puede leer
			if (archivo.delete()) {
				return true;
			}
		}
		
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER + filename);
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}
	*/

}
