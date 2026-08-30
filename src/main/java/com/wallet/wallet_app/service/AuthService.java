package com.wallet.wallet_app.service;

import com.wallet.wallet_app.dto.TokenResponse;
import com.wallet.wallet_app.entity.User;
import com.wallet.wallet_app.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping
    public TokenResponse login(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));;

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("auth:token:" + token, String.valueOf(user.getId()), 900, TimeUnit.SECONDS);
        return new TokenResponse(token, 900);
    }

}
