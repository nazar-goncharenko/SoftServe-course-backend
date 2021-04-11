package com.softserve.app.service.userService;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;
            user = userRepository.findByEmail(email).orElseThrow(() -> new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 401));

        return new AuthUserDetails(user);
    }
}
