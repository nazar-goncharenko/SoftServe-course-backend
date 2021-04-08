package com.softserve.app.repository;

import com.softserve.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    User findByResetPasswordToken(String resetToken);
}





