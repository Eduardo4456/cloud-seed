package com.example.CloudSeed.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_plant")
public class PlantModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name_plant")
	private String name;

	@Column(name = "specie")
	private String species;

	@Column(name = "water_amount")
	private double waterAmount;

	@Column(name = "description")
	private String description;
	
	public PlantModel() {
		
	}

	public PlantModel(long id, String name, String species, double waterAmount, String description) {
		this.id = id;
		this.name = name;
		this.species = species;
		this.waterAmount = waterAmount;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public double getWaterAmount() {
		return waterAmount;
	}

	public void setWaterAmount(double waterAmount) {
		this.waterAmount = waterAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
