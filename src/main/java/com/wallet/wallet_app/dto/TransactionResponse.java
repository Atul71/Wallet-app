package com.wallet.wallet_app.dto;

import java.time.Instant;

public class TransactionResponse {
    private Long id;
    private String status;
    private String type;
    private long amountCents;
    private Instant createdAt;

    public TransactionResponse(Long id, String type, long amountCents, String status, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.amountCents = amountCents;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public long getAmountCents() { return amountCents; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
