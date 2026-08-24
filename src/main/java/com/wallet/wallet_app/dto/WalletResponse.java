package com.wallet.wallet_app.dto;

public class WalletResponse {
    private Long userId;
    private long balanceCents;
    private String currency;
    private Long id;

    public WalletResponse(Long id, Long userId, long balanceCents, String currency) {
        this.id = id;
        this.userId = userId;
        this.balanceCents = balanceCents;
        this.currency = currency;
    }
    public Long getId() {
        return id;
    }

    public long getBalanceCents() {
        return balanceCents;
    }

    public String getCurrency() {
        return currency;
    }

    public long getUserId() {
        return userId;
    }
}
