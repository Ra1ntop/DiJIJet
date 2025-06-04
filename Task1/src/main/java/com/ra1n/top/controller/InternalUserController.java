package com.ra1n.top.controller;

import com.ra1n.top.model.dto.AuthResponse;
import com.ra1n.top.model.dto.LoginRequest;
import com.ra1n.top.model.dto.RegisterRequest;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Travkin Andrii
 * @version 25.05.2025
 */
@RestController
@RequestMapping("/api-v1/auth")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @GetMapping("/user-id")
    public ResponseEntity<String> getUserId(@RequestParam @Valid @NotBlank String email) {
        return ResponseEntity.ok(userService.getUserId(email));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (request == null || request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {
            throw new Ra1nException(
                    "Invalid registration data", HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        if (request == null || request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {
            throw new Ra1nException(
                    "Invalid login data", HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity.ok(userService.login(request));
    }
}
