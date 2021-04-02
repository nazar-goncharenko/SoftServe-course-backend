package com.softserve.app.services.userService;

import com.softserve.app.models.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();

    public User findById(Long id);

    public User findByEmail(String  email);

    public boolean update(User user);

}
