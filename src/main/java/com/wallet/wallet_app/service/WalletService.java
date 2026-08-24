package com.wallet.wallet_app.service;

import com.wallet.wallet_app.dto.WalletResponse;
import com.wallet.wallet_app.entity.Wallet;
import com.wallet.wallet_app.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletResponse createWallet(Long userId, String currency){
        Wallet wallet = new Wallet(userId, currency);
        Wallet saved = walletRepository.save(wallet);
        return new WalletResponse(saved.getId(), saved.getUserId(), saved.getBalanceCents(), saved.getCurrency());
    }

    public WalletResponse getWallet(Long id){
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new RuntimeException("Wallet not found"));
        return new WalletResponse(wallet.getId(), wallet.getUserId(), wallet.getBalanceCents(), wallet.getCurrency());
    }
}
