package com.wallet.wallet_app.dto;

public class TransferRequest {
    private Long fromWalletId;
    private Long toWalletId;
    private long amountCents;
    private String idempotencyKey;

    public String getIdempotencyKey() { return idempotencyKey; }
    public Long getToWalletId(){ return toWalletId; }
    public Long getFromWalletId(){ return fromWalletId; }
    public long getAmountCents() {
        return amountCents;
    }
}
