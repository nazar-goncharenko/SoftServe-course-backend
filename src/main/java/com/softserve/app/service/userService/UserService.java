package com.softserve.app.service.userService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(User::ofDTO).collect(Collectors.toList());
    }


    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(auth.getName());
    }


    public UserDTO findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404)).ofDTO();
    }

    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new SportHubException(
                SportHubConstant.USER_NOT_FOUND.getMessage(), 404)).ofDTO();
    }

    public boolean updateUser(UserDTO user) {
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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 401));
        user.setResetPasswordToken(token);
        userRepository.save(user);

    }

    public User saveUser(User user) {
        Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb.isPresent()) {
            throw new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 401);
        }

        user.setRole(User.Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void authorize(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() ->
                new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 404));
        ////checking passwords
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(),
                userDTO.getPassword()));
    }
}
