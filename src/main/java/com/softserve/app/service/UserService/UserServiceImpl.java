package com.softserve.app.service.UserService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


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
    public UserDTO updateUser(UserDTO userDTO) {
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
/*
        // Surveys list
        if (userDTO.getUserSurveys() != null) {
            curUser.setUserSurveys(new HashSet<>(userDTO.getUserSurveys()));
        }

        // Favourites list
        if (userDTO.getFavourites() != null) {
            curUser.setFavourites(new HashSet<>(userDTO.getFavourites()));
        }
*/
        return curUser.ofDTO();
    }


    @Value("${img.path}")
    private String uploadPath;

    @Override
    public UserDTO updateAvatar(Long user_id, MultipartFile userAva) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404));


        String directory = uploadPath + "UserAvatars/" + user.getId() + "avatar.jpg";

        File logo = new File(directory);
        try {
            userAva.transferTo(logo);
            user.setPhotoUrl(logo.getName());
        } catch (Exception e) {
            throw new SportHubException(SportHubConstant.FILE_LOADING_EXCEPTION.getMessage(), 500);
        }
        return user.ofDTO();
    }




// Auth


    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 401));
        user.setResetPasswordToken(token);
        userRepository.save(user);

    }

    @Override
    public User saveUser(User user) {
        Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb.isPresent()) {
            throw new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 401);
        }

        user.setRole(User.Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void authorize(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() ->
                new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 404));
        ////checking passwords
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(),
                userDTO.getPassword()));
    }
}
