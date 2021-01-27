package com.clinique.app.ws.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "Dossier")
@Table(name = "dossier")
public class Dossier implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -188112302610794762L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(mappedBy = "dossier", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private RdvEntity rdv;
	
	@Column(length = 100 ,nullable=false)
	private String dossierId;
	
	@ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "dossiers_medicaments", joinColumns = {@JoinColumn(name="dossiers_id")}, inverseJoinColumns = {@JoinColumn(name="medicaments_id")})
	private List<Medicament> medicaments = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "dossiers_soins", joinColumns = {@JoinColumn(name="dossiers_id")}, inverseJoinColumns = {@JoinColumn(name="soins_id")})
	private Set<SoinEntity> soins = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "dossiers_scanners", joinColumns = {@JoinColumn(name="dossiers_id")}, inverseJoinColumns = {@JoinColumn(name="scanners_id")})
	private Set<ScannerEntity> scanners = new HashSet<>();
	
	@ManyToOne()
	@JoinColumn(name = "factureId")
	private Facture facture;
	
	
	public List<Medicament> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}
	
	public void addMedicament(Medicament medicament) {
		this.medicaments.add(medicament);
		medicament.getDossiers().add(this);
	}
	
	public void removeMedicament(Medicament medicament) {
		this.getMedicaments().remove(medicament);
		medicament.getDossiers().remove(this);
	}
	
	public void removeMedicaments() {
		for (Medicament medicament : new HashSet<>(medicaments)) {
            removeMedicament(medicament);
        }
	}

	public RdvEntity getRdv() {
		return rdv;
	}

	public void setRdv(RdvEntity rdv) {
		this.rdv = rdv;
	}

	public Set<SoinEntity> getSoins() {
		return soins;
	}

	public void setSoins(Set<SoinEntity> soins) {
		this.soins = soins;
	}

	public Set<ScannerEntity> getScanners() {
		return scanners;
	}

	public void setScanners(Set<ScannerEntity> scanners) {
		this.scanners = scanners;
	}

	public void addSoin(SoinEntity soin) {
		this.soins.add(soin);
		soin.getDossiers().add(this);
	}
	
	public void removeSoin(SoinEntity soin) {
		this.getSoins().remove(soin);
		soin.getDossiers().remove(this);
	}
	
	public void removeSoins() {
		for (SoinEntity soin : new HashSet<>(soins)) {
            removeSoin(soin);
        }
	}
	
	public void addScanner(ScannerEntity scanner) {
		this.scanners.add(scanner);
		scanner.getDossiers().add(this);
	}
	
	public void removeScanner(ScannerEntity scanner) {
		this.getScanners().remove(scanner);
		scanner.getDossiers().remove(this);
	}
	
	public void removeScanners() {
		for (ScannerEntity scanner : new HashSet<>(scanners)) {
            removeScanner(scanner);
        }
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDossierId() {
		return dossierId;
	}

	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	
	
	
}
