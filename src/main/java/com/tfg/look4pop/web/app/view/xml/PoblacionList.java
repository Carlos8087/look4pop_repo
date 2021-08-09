package com.tfg.look4pop.web.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;

// Clase Wrapper
@XmlRootElement(name = "poblacionList") // Nombre de la etiqueta 'padre'
public class PoblacionList {

	//@XmlElement(name = "territorio") // Nombre de las etiquetas 'hijo'
	@JsonProperty("territorio")
	public List<PoblacionDataDTO> poblacionDataLst;

	public PoblacionList() {
	}

	public PoblacionList(List<PoblacionDataDTO> poblacionDataLst) {
		this.poblacionDataLst = poblacionDataLst;
	}

	public List<PoblacionDataDTO> getPoblacionDataLst() {
		return poblacionDataLst;
	}

}
