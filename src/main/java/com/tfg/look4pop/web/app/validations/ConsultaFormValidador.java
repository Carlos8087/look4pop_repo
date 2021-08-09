package com.tfg.look4pop.web.app.validations;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tfg.look4pop.web.app.models.dto.ConsultaFormDTO;
import com.tfg.look4pop.web.app.models.dto.ConsultaFormDescripcionDTO;
import com.tfg.look4pop.web.app.models.dto.PoblacionDataDTO;

@Component
public class ConsultaFormValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// Comprobamos que el objeto pasado por argumento se corresponda con el que queremos validar
		return ConsultaFormDTO.class.isAssignableFrom(clazz) || ConsultaFormDescripcionDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ConsultaFormDTO consultaForm = (ConsultaFormDTO) target;
		
		// Campo 'Nivel' obligatorio
		if ( (StringUtils.isEmpty(consultaForm.getNivel()))
				|| ("-1".equals(consultaForm.getNivel())) ) {
			// nombre del argumento del objeto a validar + mensaje de error
			errors.rejectValue("nivel", "text.validation.form.error.mandatory.nivel");
		}
		
		// Campo 'Ambito general' obligatorio
		if ( (StringUtils.isEmpty(consultaForm.getAmbitoGen()))
				|| ("-1".equals(consultaForm.getAmbitoGen())) ) {
			// nombre del argumento del objeto a validar + mensaje de error
			errors.rejectValue("ambitoGen", "text.validation.form.error.mandatory.ambitoGen");
		}
		
		// Campo 'Ambito particular' obligatorio
		if ( (StringUtils.isEmpty(consultaForm.getAmbitosPar()))
				|| (consultaForm.getAmbitosPar().length == 0) ) {
			// nombre del argumento del objeto a validar + mensaje de error
			errors.rejectValue("ambitosPar", "text.validation.form.error.mandatory.ambitosPar");
		}
		
		// Campo 'Tipo de fuente' obligatorio
		if ( (StringUtils.isEmpty(consultaForm.getTpsFuente()))
				|| (consultaForm.getTpsFuente().length == 0) ) {
			// nombre del argumento del objeto a validar + mensaje de error
			errors.rejectValue("tpsFuente", "text.validation.form.error.mandatory.tpsFuente");
		}
		
		// Campo 'Anio' obligatorio
		if ( (StringUtils.isEmpty(consultaForm.getAnios()))
				|| (consultaForm.getAnios().length == 0) ) {
			// nombre del argumento del objeto a validar + mensaje de error
			errors.rejectValue("anios", "text.validation.form.error.mandatory.anios.1");
		} else {
			
			// Si se ha seleccionado tipo fuente 'censo de derecho' -> Obligatorio seleccionar al menos un anio de ese tipo
			// Si se ha seleccionado tipo fuente 'censo de hecho' -> Obligatorio seleccionar al menos un anio de ese tipo
			// Si se ha seleccionado tipo fuente 'padron' -> Obligatorio seleccionar al menos un anio de ese tipo
			
			List<String> tpFuenteLst = Arrays.asList(consultaForm.getTpsFuente());
			
			boolean errorInformado = false;
			if (tpFuenteLst.contains("censoderecho")) {
				boolean existeDiscrepa = true;
				for (String anio : consultaForm.getAnios()) {
					Character tpFuente = anio.charAt(0);
					if ("d".equals(tpFuente.toString())) {
						existeDiscrepa = false;
					}
				}
				if (existeDiscrepa) {
					// nombre del argumento del objeto a validar + mensaje de error
					errors.rejectValue("anios", "text.validation.form.error.mandatory.anios.2");
					errorInformado = true;
				}
			}
			
			if ( (tpFuenteLst.contains("censohecho"))
					&& (!errorInformado) ) {
				boolean existeDiscrepa = true;
				for (String anio : consultaForm.getAnios()) {
					Character tpFuente = anio.charAt(0);
					if ("h".equals(tpFuente.toString())) {
						existeDiscrepa = false;
					}
				}
				if (existeDiscrepa) {
					// nombre del argumento del objeto a validar + mensaje de error
					errors.rejectValue("anios", "text.validation.form.error.mandatory.anios.2");
					errorInformado = true;
				}
			}
			
			if ( (tpFuenteLst.contains("padron")) 
					&& (!errorInformado) ) {
				boolean existeDiscrepa = true;
				for (String anio : consultaForm.getAnios()) {
					Character tpFuente = anio.charAt(0);
					if ("p".equals(tpFuente.toString())) {
						existeDiscrepa = false;
					}
				}
				if (existeDiscrepa) {
					// nombre del argumento del objeto a validar + mensaje de error
					errors.rejectValue("anios", "text.validation.form.error.mandatory.anios.2");
					errorInformado = true;
				}
			}
			
		}
		
		/*
		// Check 'Censo' o check 'Padron' obligatorio
		if ( (StringUtils.isEmpty(consultaForm.getTpsFuente()))
				|| (consultaForm.getTpsFuente().length == 0) ) {
			// nombre del argumento del objeto a validar + mensaje de error
			errors.rejectValue("tpsFuente", "text.validation.form.error.mandatory.tpsFuente");
		}
		
		// Campo 'Tipo de censo' obligatorio (para tipo de fuente 'Censo')
		if ( (!StringUtils.isEmpty(consultaForm.getTpsFuente()))
				&& (consultaForm.getTpsFuente().length > 0) ) {
				
			List<String> tpsFuenteLst = Arrays.asList(consultaForm.getTpsFuente());
			
			if ( (tpsFuenteLst.contains("censo")) 
				&& ( (StringUtils.isEmpty(consultaForm.getTpsCenso())) 
						|| (consultaForm.getTpsCenso().length == 0) ) ) {

				// nombre del argumento del objeto a validar + mensaje de error
				errors.rejectValue("tpsCenso", "text.validation.form.error.mandatory.tpsCenso");
			}
		}
		
		// Campo 'Año' obligatorio (para tipo de fuente 'Censo' y según subtipo)
		if ( (!StringUtils.isEmpty(consultaForm.getTpsCenso()))
				&& (consultaForm.getTpsCenso().length > 0) ) {
					
			List<String> tpsCensoLst = Arrays.asList(consultaForm.getTpsCenso());
					
			if ( (tpsCensoLst.contains("derecho")) 
				&& ( (StringUtils.isEmpty(consultaForm.getCensoDerAnios())) 
						|| (consultaForm.getCensoDerAnios().length == 0) ) ) {

				// nombre del argumento del objeto a validar + mensaje de error
				errors.rejectValue("censoDerAnios", "text.validation.form.error.mandatory.censoDerAnios");
			}
			
			if ( (tpsCensoLst.contains("hecho")) 
				&& ( (StringUtils.isEmpty(consultaForm.getCensoHecAnios())) 
						|| (consultaForm.getCensoHecAnios().length == 0) ) ) {

				// nombre del argumento del objeto a validar + mensaje de error
				errors.rejectValue("censoHecAnios", "text.validation.form.error.mandatory.censoHecAnios");
			}
		}
		
		// Campo 'Año' obligatorio (para tipo de fuente 'Padron')
		if ( (!StringUtils.isEmpty(consultaForm.getTpsFuente()))
				&& (consultaForm.getTpsFuente().length > 0) ) {
						
			List<String> tpsFuenteLst = Arrays.asList(consultaForm.getTpsFuente());
					
			if ( (tpsFuenteLst.contains("padron")) 
				&& ( (StringUtils.isEmpty(consultaForm.getPadronAnios())) 
						|| (consultaForm.getPadronAnios().length == 0) ) ) {

				// nombre del argumento del objeto a validar + mensaje de error
				errors.rejectValue("padronAnios", "text.validation.form.error.mandatory.padronAnios");
			}
		}
		*/

	}

}
