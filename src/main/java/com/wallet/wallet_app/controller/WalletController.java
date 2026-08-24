package com.wallet.wallet_app.controller;

import com.wallet.wallet_app.dto.CreateWalletRequest;
import com.wallet.wallet_app.dto.WalletResponse;
import com.wallet.wallet_app.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<WalletResponse> createWallet(@RequestBody CreateWalletRequest request){
        WalletResponse response = walletService.createWallet(
                request.getUserId(),
                request.getCurrency()
                );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable Long id){
        WalletResponse response = walletService.getWallet(id);
        return ResponseEntity.ok(response);
    }
}
