package com.wallet.wallet_app.dto;


public class UserResponse {
    private Long id;
    private String email;
    private String status;

    public UserResponse(Long id, String email, String status) {
        this.id = id;
        this.email = email;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }
}
