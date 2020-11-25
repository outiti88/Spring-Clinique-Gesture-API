package com.clinique.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email) ;
	
}
