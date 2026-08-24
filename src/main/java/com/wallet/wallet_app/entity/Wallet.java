package com.wallet.wallet_app.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table (name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private long balanceCents = 0;

    @Column(nullable = false)
    private String currency;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Wallet() {}

    public Wallet(Long userId, String currency) {
        this.userId = userId;
        this.currency = currency;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public long getBalanceCents() { return balanceCents; }
    public void setBalanceCents(long balanceCents) { this.balanceCents = balanceCents; }
    public String getCurrency() { return currency; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }

}
