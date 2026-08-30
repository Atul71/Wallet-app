package com.wallet.wallet_app.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table (name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromWalletId;
    private Long toWalletId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(unique = true)
    private String idempotencyKey;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private long amountCents;

    public enum TransactionType{
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    }

    public enum TransactionStatus{
        PENDING,
        COMPLETED,
        FAILED
    }

    public Transaction() {}

    public Transaction(Long fromWalletId, Long toWalletId, TransactionType type,
                       long amountCents, TransactionStatus status, String idempotencyKey) {
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.type = type;
        this.amountCents = amountCents;
        this.status  = status;
        this.idempotencyKey = idempotencyKey;
    }

    public Long getId() { return id; }
    public Long getFromWalletId() { return fromWalletId; }
    public Long getToWalletId() { return toWalletId; }
    public TransactionType getType() { return type; }
    public long getAmountCents() { return amountCents; }
    public TransactionStatus getStatus() { return status; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public Instant getCreatedAt() { return createdAt; }

    public void setStatus(TransactionStatus status) { this.status = status; }
}
