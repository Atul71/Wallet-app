package com.wallet.wallet_app.service;

import com.wallet.wallet_app.dto.UserResponse;
import com.wallet.wallet_app.entity.User;
import com.wallet.wallet_app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(String email, String password){
        String hashPassword = passwordEncoder.encode(password);
        User user = new User(email, hashPassword);
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getEmail(), saved.getStatus().name());

    }
}
