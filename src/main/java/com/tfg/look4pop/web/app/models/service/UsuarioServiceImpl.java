package com.tfg.look4pop.web.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.look4pop.web.app.models.entity.Usuario;
import com.tfg.look4pop.web.app.models.mapper.IUsuarioMapper;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioMapper usuarioMapper;
	
	@Override
	public Usuario getUsuarioById(Integer id) {
		return usuarioMapper.getUsuarioById(id);
	}
	
	@Override
	public Usuario getUsuarioByUsername(String username) {
		return usuarioMapper.getUsuarioByUsername(username);
	}
	
	@Override
	public List<Usuario> getUsuarioLst() {
		return usuarioMapper.getUsuarioLst();
	}
	
	@Override
	public Integer insert(Usuario usuario) {
		return usuarioMapper.insert(usuario);
	}

	@Override
	public Integer update(Usuario usuario) {
		return usuarioMapper.update(usuario);
	}

	@Override
	public Integer delete(Usuario usuario) {
		return usuarioMapper.delete(usuario);
	}

}
