package io.github.auggud.projectmanager.service;

import io.github.auggud.projectmanager.dto.AuthResponse;
import io.github.auggud.projectmanager.dto.RegisterRequest;
import io.github.auggud.projectmanager.entity.User;
import io.github.auggud.projectmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return new AuthResponse("User registered successfully");
    }
}
