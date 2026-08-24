package com.wallet.wallet_app.dto;

public class CreateWalletRequest {
    private Long userId;
    private String currency;

    public Long getUserId() {
        return userId;
    }

    public String getCurrency(){
        return currency;
    }
}
