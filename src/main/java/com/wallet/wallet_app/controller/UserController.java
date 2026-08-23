package com.wallet.wallet_app.controller;

import com.wallet.wallet_app.dto.CreateUserRequest;
import com.wallet.wallet_app.dto.UserResponse;
import com.wallet.wallet_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request){
        UserResponse response = userService.createUser(
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(response);

    }

}
