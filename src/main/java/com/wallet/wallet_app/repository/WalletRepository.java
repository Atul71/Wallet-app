package com.wallet.wallet_app.repository;

import com.wallet.wallet_app.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface WalletRepository extends JpaRepository <Wallet, Long> {
}
