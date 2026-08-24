package com.wallet.wallet_app.dto;

import com.wallet.wallet_app.entity.Transaction;
import jakarta.persistence.Column;

public class AmountRequest {
    private long amountCents;
    private String idempotencyKey;

    public long getAmountCents() { return amountCents; }
    public String getIdempotencyKey() { return idempotencyKey;}

    }
