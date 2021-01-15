package com.clinique.app.ws.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.RoleEntity;


@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Long>{
	
	RoleEntity findByRoleId(String roleId);
	
	RoleEntity findByName(String name);
	
}
