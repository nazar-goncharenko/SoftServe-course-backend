package com.softserve.app.service.userService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import com.softserve.app.service.converterService.ConverterService;
import com.softserve.app.service.fileService.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final ConverterService converterService;


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
    public UserDTO updateUser(MultipartFile file, String userDTO) {
        UserDTO dto = converterService.convertStringToClass(userDTO, UserDTO.class);
        User usrFromDb = findById(dto.getId());

        // Avatar
        if (file != null) {
            dto.setPhotoUrl(fileService.saveImg(file));
        } else dto.setPhotoUrl(usrFromDb.getPhotoUrl());

        // Password
        if ((dto.getPassword() != null) && (dto.getNew_pass() != null)) {
            checkPassword(usrFromDb, dto.getPassword(), dto.getNew_pass(), dto.getNew_pass_2());
        }

        return userRepository.save(User.builder()
                .id(dto.getId())
                .username(dto.getUsername() != null ? dto.getUsername() : usrFromDb.getUsername())
                .email(dto.getEmail() != null ? dto.getEmail() : usrFromDb.getEmail())
                .photoUrl(dto.getPhotoUrl())
                .password(usrFromDb.getPassword())
                .role(usrFromDb.getRole())
                .build())
                .ofDTO();
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    private void checkPassword(User user, String oldPass, String pass1, String pass2) {
        if (pass1.equals(pass2)) {
            String encodedOld = passwordEncoder.encode(oldPass);
            if (encodedOld.equals(user.getPassword())) {
                updatePassword(user, pass2);
            }
        }
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

    @Override
    public UserDTO getByDTO(UserDTO userDTO) {
        return userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() ->
                new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 404)).ofDTO();
    }
}
