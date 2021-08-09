package com.tfg.look4pop.web.app.validations;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tfg.look4pop.web.app.models.dto.FuenteFormDTO;
import com.tfg.look4pop.web.app.models.dto.UsuarioFormDTO;

@Component
public class FuenteFormValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return FuenteFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		FuenteFormDTO fuenteForm = (FuenteFormDTO) target;
		
		if (fuenteForm.getTpAccion().equals("A")) {
		
			// Campo 'Año' obligatorio
			if ((fuenteForm.getTipo().equals("censo"))
				&& (StringUtils.isEmpty(fuenteForm.getAnioC()))) {
				errors.rejectValue("anioC", "text.admin.fuente.form.error.mandatory.anio");
			}
			
			// Campo 'Año' obligatorio
			if ((fuenteForm.getTipo().equals("padron"))
				&& (StringUtils.isEmpty(fuenteForm.getAnioP()))) {
				errors.rejectValue("anioP", "text.admin.fuente.form.error.mandatory.anio");
			}
			
		}

	}

}
