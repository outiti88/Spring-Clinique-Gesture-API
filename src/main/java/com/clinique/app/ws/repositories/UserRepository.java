package com.clinique.app.ws.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	
	UserEntity findByUserID(String userID);
	
}
