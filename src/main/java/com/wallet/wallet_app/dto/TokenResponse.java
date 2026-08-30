package com.wallet.wallet_app.dto;

public class TokenResponse {
    private String token;
    private int expiresInSeconds;

    public TokenResponse(String token, int expiresInSeconds){
        this.token = token;
        this.expiresInSeconds = expiresInSeconds;
    }
    public int getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public String getToken() {
        return token;
    }
}
