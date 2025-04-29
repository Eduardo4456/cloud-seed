package com.example.CloudSeed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.CloudSeed.model.PlantModel;

public interface PlantRepository extends JpaRepository<PlantModel, Long> {}