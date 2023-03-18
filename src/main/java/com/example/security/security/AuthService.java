package com.example.security.security;

import com.example.security.common.utils.JwtUtils;
import com.example.security.security.dto.request.LoginRequest;
import com.example.security.security.dto.request.RefreshRequest;
import com.example.security.security.dto.request.RegisterRequest;
import com.example.security.security.dto.response.LoginResponse;
import com.example.security.security.dto.response.RegisterResponse;
import com.example.security.security.model.RefreshToken;
import com.example.security.user.UserRepository;
import com.example.security.user.model.Role;
import com.example.security.user.model.RoleEnum;
import com.example.security.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginResponse login(LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if(user.isPresent()) {
            if(!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                throw new RuntimeException("Password is incorrect");
            }
            String accessToken = jwtUtils.generateToken(user.get().getUsername(), user.get().getRoles().stream().map(role -> role.getName().name()).toList());
            String refreshToken = jwtUtils.generateToken(user.get().getUsername(), user.get().getRoles().stream().map(role -> role.getName().name()).toList(), true);
            refreshTokenRepository.save(new RefreshToken().setToken(refreshToken).setActive(true).setFamily(UUID.randomUUID()));
            return LoginResponse.of(user.get(), accessToken, refreshToken);
        }
        throw new RuntimeException("User not found");
    }

    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<RoleEnum> roles= request.getRoles();
        user.setRoles(roles.stream().map(role -> new Role().setName(role)).collect(Collectors.toSet()));
        userRepository.save(user);
        return RegisterResponse.of(user);
    }

    public LoginResponse refresh(RefreshRequest request) {
        if(!jwtUtils.validateToken(request.getRefreshToken()) ) {
            throw new RuntimeException("Token is invalid");
        }
        Optional<RefreshToken> oldRefreshToken = refreshTokenRepository.findByToken(request.getRefreshToken());
        if(oldRefreshToken.isEmpty()) {
            throw new RuntimeException("Token is invalid");
        }
        if(!oldRefreshToken.get().getActive()) {
            // fire new event to set all refresh tokens with the same parent (including the parent) to inactive
            refreshTokenRepository.updateAllInvalidToken(oldRefreshToken.get().getFamily());
            throw new RuntimeException("Token reuse detected");
        }
        String username = jwtUtils.extractUsername(request.getRefreshToken());
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            String newAccessToken = jwtUtils.generateToken(user.get().getUsername(), user.get().getRoles().stream().map(role -> role.getName().name()).toList());
            String newRefreshToken = jwtUtils.generateToken(user.get().getUsername(), user.get().getRoles().stream().map(role -> role.getName().name()).toList(),true);
            refreshTokenRepository.save(new RefreshToken().setToken(newRefreshToken).setActive(true).setFamily(oldRefreshToken.get().getFamily()));
            refreshTokenRepository.save(oldRefreshToken.get().setToken(request.getRefreshToken()).setActive(false));
            return LoginResponse.of(user.get(), newAccessToken, newRefreshToken);
        }
        throw new RuntimeException("User not found");
    }
}
