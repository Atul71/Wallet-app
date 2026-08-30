package com.wallet.wallet_app.controller;

import com.wallet.wallet_app.dto.LoginRequest;
import com.wallet.wallet_app.dto.TokenResponse;
import com.wallet.wallet_app.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginRequest request){
        TokenResponse response = authService.login(
                request.getEmail(), request.getPassword()
        );
        return ResponseEntity.ok(response);
    }
}
