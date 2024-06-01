package com.example.kurs.service;

import com.example.kurs.exception.UsernameAlreadyExistsException;
import com.example.kurs.model.User;
import com.example.kurs.model.UserAuthority;
import com.example.kurs.model.UserRole;
import com.example.kurs.repository.UserRepository;
import com.example.kurs.repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRolesRepository userRolesRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registration(String username, String password) {
        log.info("Registering new user with username: {}", username);
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = userRepository.save(
                    new User()
                            .setId(null)
                            .setUsername(username)
                            .setPassword(passwordEncoder.encode(password))
                            .setLocked(false)
                            .setExpired(false)
                            .setEnabled(true)
            );
            userRolesRepository.save(new UserRole(null, UserAuthority.customer, user));
            log.info("User registered successfully with username: {}", username);
        } else {
            log.error("Username already exists: {}", username);
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user details for username: {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username not found: {}", username);
            return new UsernameNotFoundException(username);
        });
    }
}
