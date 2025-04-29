package com.example.CloudSeed.service;

import com.example.CloudSeed.dtos.CreateUserDto;
import com.example.CloudSeed.model.UserModel;
import com.example.CloudSeed.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(CreateUserDto createUserDto) {
        var Entity = new UserModel(null,
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSave = userRepository.save(Entity);
        return userSave.getId();
    }
}
