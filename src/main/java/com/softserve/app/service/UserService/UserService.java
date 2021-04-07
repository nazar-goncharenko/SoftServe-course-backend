package com.softserve.app.service.UserService;

import com.softserve.app.models.User;

import java.util.List;

public interface UserService {
    public User getCurrentUser();

    public List<User> findAll();

    public User findById(Long id);

    public User findByEmail(String  email);

    public boolean updateUser(User user);
}
