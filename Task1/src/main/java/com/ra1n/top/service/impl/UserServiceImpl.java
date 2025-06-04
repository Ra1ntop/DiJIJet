package com.ra1n.top.service.impl;

import com.ra1n.top.config.JwtUtils;
import com.ra1n.top.model.dto.AuthResponse;
import com.ra1n.top.model.dto.LoginRequest;
import com.ra1n.top.model.dto.RegisterRequest;
import com.ra1n.top.model.entity.sec.AbstractUser;
import com.ra1n.top.model.entity.sec.RoleType;
import com.ra1n.top.model.entity.sec.User;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.output.persistent.UserRepository;
import com.ra1n.top.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Travkin Andrii
 * @version 28.05.2025
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public String getUserId(String email) {
        if (email == null || email.isBlank()) {
            throw new Ra1nException("Email must not be empty", HttpStatus.BAD_REQUEST);
        }
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new Ra1nException("User with sub " + email + " not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (request == null || request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {
            throw new Ra1nException("Invalid registration data", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new Ra1nException("Email already in use: " + request.email(), HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(RoleType.CLIENT);
        userRepository.save(user);
        String token = jwtUtils.generateToken(user);
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        if (request == null || request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {
            throw new Ra1nException("Invalid login data", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (org.springframework.security.core.AuthenticationException ex) {
            throw new Ra1nException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        AbstractUser user = (AbstractUser) authentication.getPrincipal();
        String token = jwtUtils.generateToken(user);
        return new AuthResponse(token);
    }
}
