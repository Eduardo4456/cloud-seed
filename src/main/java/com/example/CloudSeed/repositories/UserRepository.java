package com.example.CloudSeed.repositories;

import com.example.CloudSeed.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long>{}
