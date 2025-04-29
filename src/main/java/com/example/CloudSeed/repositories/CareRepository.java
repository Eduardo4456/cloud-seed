package com.example.CloudSeed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CloudSeed.model.CarePlantModel;

public interface CareRepository extends JpaRepository<CarePlantModel, Long>{

}
