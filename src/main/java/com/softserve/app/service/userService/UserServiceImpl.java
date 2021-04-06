package com.softserve.app.service.userService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(auth.getName());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));
    }

    public User findByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return userRepository.findByEmail(email).get();
        } else {
            return null;
        }
    }

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
}
