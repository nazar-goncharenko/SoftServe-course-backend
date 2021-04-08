package com.softserve.app.service.userService;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.models.Role;
import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(auth.getName());
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));
    }

    @Override
    public User findByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return userRepository.findByEmail(email).get();
        } else {
            return null;
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }


    @Override
    public boolean updateUser(User user) {
        User curUser = userRepository.findById(user.getId()).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));

        // Username
        if (user.getUsername() != null) {
            curUser.setUsername(user.getUsername());
        }

        // Email
        if (user.getEmail().equals(curUser.getEmail())) {
            curUser.setEmail(user.getEmail());
        }

        // Photo = avatar
        if (user.getPhotoUrl() != null) {
            curUser.setPhotoUrl(user.getPhotoUrl());
        }
        return true;
    }
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(),401));
            user.setResetPasswordToken(token);
            userRepository.save(user);

    }

    public User saveUser(User user) {
        Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "USER FOUND");
        }

        user.setRoles(Collections.singletonList((Role.ROLE_ADMIN)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
