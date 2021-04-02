package com.softserve.app.repositories.UserRepository;

import com.softserve.app.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    Optional<User> createUser(User user);
    Optional<User> updateUser(User user);
}

