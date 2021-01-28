package com.clinique.app.ws.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clinique.app.ws.entities.SoinEntity;

@Repository
public interface SoinRepository extends CrudRepository<SoinEntity, Long> {
	
	SoinEntity findBySoinId(String soinId);
	
	@Transactional
	@Modifying
	@Query(value="delete from soin_entity where soin_id = :soinId", nativeQuery = true)
	public void deleteSoinBySoinId(@Param("soinId") String soinId);
	
}
