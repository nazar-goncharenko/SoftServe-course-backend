package com.softserve.app.service.UserService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


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
        return userRepository.findByEmail(email).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        User curUser = userRepository.findById(userDTO.getId()).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));

        // Username
        if (userDTO.getUsername() != null) {
            curUser.setUsername(userDTO.getUsername());
        }

        // Email
        if (userDTO.getId().equals(curUser.getId())) {
            curUser.setEmail(userDTO.getEmail());
        }

        // Photo = avatar
        if (userDTO.getPhotoUrl() != null) {
            curUser.setPhotoUrl(userDTO.getPhotoUrl());
        }

        // Password
        if (userDTO.getPassword() != null) {
            curUser.setPassword(userDTO.getPassword());
        }

        // Surveys list
        if (userDTO.getUserSurveys() != null) {
            curUser.setUserSurveys(userDTO.getUserSurveys());
        }

        // Favourites list
        if (userDTO.getFavourites() != null) {
            curUser.setFavourites(userDTO.getFavourites());
        }

        return true;
    }

    public boolean updateAvatar(Long user_id, MultipartFile userAva) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));

        String directory = "C:\\Users\\User\\Desktop\\Projects\\SoftServe-course-backend" +
                "\\src\\main\\resources\\PhotoDirectory\\UserAvatars\\"
                + user.getId() + "avatar.jpg";

        File logo = new File(directory);
        try {
            userAva.transferTo(logo);
            user.setPhotoUrl(logo.getName());
            // user.setPhotoUrl(directory); - ?
        } catch (Exception e) {
            throw new SportHubException(SportHubConstant.FILE_LOADING_EXCEPTION.getMessage(), 500);
        }
        return true;
    }
}
