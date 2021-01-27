package com.clinique.app.ws.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.clinique.app.ws.entities.Dossier;

@Repository
public interface DossierRepository  extends CrudRepository<Dossier, Long>{
	
	public Dossier findByDossierId(String dossierId);
	
	@Query(value = "select * from Dossier where factureId = ?1", nativeQuery = true)
	public List<Dossier> findDossiersByFacture(String factureId);

}
