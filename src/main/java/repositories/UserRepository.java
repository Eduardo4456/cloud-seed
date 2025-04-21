package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{}
