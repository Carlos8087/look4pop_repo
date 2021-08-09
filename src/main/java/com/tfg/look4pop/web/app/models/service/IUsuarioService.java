package com.tfg.look4pop.web.app.models.service;

import java.util.List;

import com.tfg.look4pop.web.app.models.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario getUsuarioById(Integer id);
	public Usuario getUsuarioByUsername(String username);
	public List<Usuario> getUsuarioLst();
	public Integer insert(Usuario usuario);
	public Integer update(Usuario usuario);
	public Integer delete(Usuario usuario);

}
