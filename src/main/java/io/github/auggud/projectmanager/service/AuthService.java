package io.github.auggud.projectmanager.service;

import io.github.auggud.projectmanager.dto.AuthResponseDto;
import io.github.auggud.projectmanager.dto.LoginRequestDto;
import io.github.auggud.projectmanager.dto.RegisterRequestDto;
import io.github.auggud.projectmanager.entity.User;
import io.github.auggud.projectmanager.repository.UserRepository;
import io.github.auggud.projectmanager.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public AuthResponseDto register(RegisterRequestDto request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return new AuthResponseDto("User registered successfully");
    }

    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = (User) userDetailsService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token);
    }
}
