package com.softserve.app.service.userService;

import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return  userRepository.findAll();
    }

    public User findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public User findByEmail(String  email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return userRepository.findByEmail(email).get();
        } else {
            return null;
        }
    }

    public boolean update(User user) {
        User curUser;
        if (userRepository.findById(user.getId()).isPresent()) {
            curUser =  userRepository.findById(user.getId()).get();
        } else {
            return false;
        }

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
