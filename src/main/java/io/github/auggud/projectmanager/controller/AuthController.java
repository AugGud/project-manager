package io.github.auggud.projectmanager.controller;

import io.github.auggud.projectmanager.dto.AuthResponseDto;
import io.github.auggud.projectmanager.dto.LoginRequestDto;
import io.github.auggud.projectmanager.dto.RegisterRequestDto;
import io.github.auggud.projectmanager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        AuthResponseDto response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
