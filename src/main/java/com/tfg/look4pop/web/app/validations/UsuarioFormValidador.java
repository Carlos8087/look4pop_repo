package com.tfg.look4pop.web.app.validations;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tfg.look4pop.web.app.models.dto.UsuarioFormDTO;

@Component
public class UsuarioFormValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UsuarioFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UsuarioFormDTO usuarioForm = (UsuarioFormDTO) target;
		
		if (usuarioForm.getTpAccion().equals("A")) {
			
			// Campo 'Username' obligatorio
			if (StringUtils.isEmpty(usuarioForm.getUsername())) {
				errors.rejectValue("username", "text.admin.usuario.form.error.mandatory.username");
			}
						
			// Campo 'Password' obligatorio
			if (StringUtils.isEmpty(usuarioForm.getPassword())) {
				errors.rejectValue("password", "text.admin.usuario.form.error.mandatory.password");
			}
			
		}
			
		if (usuarioForm.getTpAccion().equals("M")) {
			
			// Seleccion de usuario obligatoria
			if ( (StringUtils.isEmpty(usuarioForm.getIdUsuarioSelect())) 
					|| ("-1".equals(usuarioForm.getIdUsuarioSelect())) ) {
				errors.rejectValue("idUsuarioSelect", "text.admin.usuario.form.error.mandatory.user");
			} else {
				
				// Campo 'Username' obligatorio
				if (StringUtils.isEmpty(usuarioForm.getUsername())) {
					errors.rejectValue("username", "text.admin.usuario.form.error.mandatory.username");
				}
				
				// Campo 'Password' obligatorio
				if (StringUtils.isEmpty(usuarioForm.getPassword())) {
					errors.rejectValue("password", "text.admin.usuario.form.error.mandatory.password");
				}
				
			}
			
		}
		
		if (usuarioForm.getTpAccion().equals("E"))  {
			
			// Seleccion de usuario obligatoria
			if ( (StringUtils.isEmpty(usuarioForm.getIdUsuarioSelect())) 
					|| ("-1".equals(usuarioForm.getIdUsuarioSelect())) ) {
				errors.rejectValue("idUsuarioSelect", "text.admin.usuario.form.error.mandatory.user");
			}			
			
		}
		
	}
		
}
