package com.clinique.app.ws.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clinique.app.ws.entities.PatientEntity;
import com.clinique.app.ws.entities.RoleEntity;
import com.clinique.app.ws.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	
	UserEntity findByUserID(String userID);
	
	@Query(value = "select * from users where role_id = :roleId", nativeQuery = true)
	List<UserEntity> findByRole(@Param("roleId") Long roleId);
	
}
