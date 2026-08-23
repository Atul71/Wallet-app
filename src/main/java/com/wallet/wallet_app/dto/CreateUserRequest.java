package com.wallet.wallet_app.dto;

public class CreateUserRequest {

    private String email;
    private String password;
    private String confirmPassword;

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }
}