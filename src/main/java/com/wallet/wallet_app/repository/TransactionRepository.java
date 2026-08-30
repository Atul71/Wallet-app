package com.wallet.wallet_app.repository;

import com.wallet.wallet_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromWalletIdOrToWalletId(Long fromWalletId, Long toWalletId);

}


