package com.tfg.look4pop.web.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.look4pop.web.app.models.entity.Role;
import com.tfg.look4pop.web.app.models.mapper.IRoleMapper;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleMapper roleMapper;
	
	@Override
	public Integer insert(Role role) {
		return roleMapper.insert(role);
	}

}
