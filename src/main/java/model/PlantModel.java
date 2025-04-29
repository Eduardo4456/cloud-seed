package model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_plant")
public class PlantModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPlant;

	private String namePlant;

	private String species;
	
	public PlantModel() {
		
	}

	public PlantModel(long idPlant, String namePlant, String species) {
		super();
		this.idPlant = idPlant;
		this.namePlant = namePlant;
		this.species = species;
	}

	public String getNamePlant() {
		return namePlant;
	}

	public void setNamePlant(String namePlant) {
		this.namePlant = namePlant;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public long getIdPlant() {
		return idPlant;
	}

}
