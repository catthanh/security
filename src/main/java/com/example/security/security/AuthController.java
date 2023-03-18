package com.example.security.security;

import com.example.security.common.dto.response.Response;
import com.example.security.security.dto.request.LoginRequest;
import com.example.security.security.dto.request.RefreshRequest;
import com.example.security.security.dto.request.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest request) {
        return Response.ok(authService.login(request));
    }

    @PostMapping("/register")
    public Response register(@RequestBody RegisterRequest request) {
        return Response.ok(authService.register(request));
    }

    @PostMapping("/refresh")
    public Response refresh(@RequestBody RefreshRequest request) {
        return Response.ok(authService.refresh(request));
    }

}
