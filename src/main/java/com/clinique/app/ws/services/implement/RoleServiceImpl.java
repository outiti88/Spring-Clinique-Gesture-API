package com.clinique.app.ws.services.implement;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinique.app.ws.dto.RoleDto;
import com.clinique.app.ws.entities.RoleEntity;
import com.clinique.app.ws.repositories.RoleRepository;
import com.clinique.app.ws.services.RoleService;
import com.clinique.app.ws.shared.Utils;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	Utils util ;

	@Override
	public RoleDto createRole(RoleDto roleDto) {
		
		roleDto.setRoleId(util.generateStringId(32));
		
		RoleEntity roleEntity = new RoleEntity();
		
		BeanUtils.copyProperties(roleDto, roleEntity);
		
		RoleEntity newRoleEntity = roleRepository.save(roleEntity);
		
		RoleDto newRoleDto = new RoleDto();
		
		BeanUtils.copyProperties(newRoleEntity, newRoleDto);
		
		return newRoleDto;
	}

}
