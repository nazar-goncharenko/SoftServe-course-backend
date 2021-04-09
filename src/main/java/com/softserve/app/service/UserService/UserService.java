package com.softserve.app.service.UserService;

import com.softserve.app.dto.UserDTO;
import com.softserve.app.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User getCurrentUser();

    List<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    boolean  updateUser(UserDTO userDTO);

    boolean updateAvatar(Long user_id, MultipartFile userAva);
}
