package com.ra1n.top.service.impl;

import com.ra1n.top.config.JwtUtils;
import com.ra1n.top.model.dto.AuthResponse;
import com.ra1n.top.model.dto.LoginRequest;
import com.ra1n.top.model.dto.RegisterRequest;
import com.ra1n.top.model.entity.sec.User;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.output.persistent.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserId_nullOrBlank_throwsException() {
        Ra1nException ex = assertThrows(Ra1nException.class, () -> userService.getUserId(null));
        assertEquals("Email must not be empty", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        Ra1nException ex2 = assertThrows(Ra1nException.class, () -> userService.getUserId(""));
        assertEquals("Email must not be empty", ex2.getMessage());
    }

    @Test
    void getUserId_notFound_throwsException() {
        when(userRepository.findByEmail("e")).thenReturn(Optional.empty());
        Ra1nException ex = assertThrows(Ra1nException.class, () -> userService.getUserId("e"));
        assertEquals("User with sub e not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void getUserId_found_returnsId() {
        User user = new User();
        user.setId("1");
        when(userRepository.findByEmail("e")).thenReturn(Optional.of(user));
        String result = userService.getUserId("e");
        assertEquals("1", result);
    }

    @Test
    void register_invalidData_throwsException() {
        Ra1nException ex = assertThrows(Ra1nException.class, () -> userService.register(null));
        assertEquals("Invalid registration data", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void register_emailExists_throwsException() {
        when(userRepository.existsByEmail("e")).thenReturn(true);
        Ra1nException ex = assertThrows(Ra1nException.class,
                () -> userService.register(new RegisterRequest("e", "p")));
        assertEquals("Email already in use: e", ex.getMessage());
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void login_invalidData_throwsException() {
        Ra1nException ex = assertThrows(Ra1nException.class, () -> userService.login(null));
        assertEquals("Invalid login data", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void login_authenticationFails_throwsException() {
        LoginRequest req = new LoginRequest("e", "p");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.core.AuthenticationException("fail") {});
        Ra1nException ex = assertThrows(Ra1nException.class, () -> userService.login(req));
        assertEquals("Invalid credentials", ex.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }

    @Test
    void login_success_returnsToken() {
        LoginRequest req = new LoginRequest("e", "p");
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        User user = new User();
        when(auth.getPrincipal()).thenReturn(user);
        when(jwtUtils.generateToken(user)).thenReturn("token");
        AuthResponse resp = userService.login(req);
        assertEquals("token", resp.token());
    }
}