package com.wallet.wallet_app.controller;

import com.wallet.wallet_app.dto.*;
import com.wallet.wallet_app.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/wallets/{id}/deposit")
    public ResponseEntity<TransactionResponse> depositAmount(@PathVariable Long id, @RequestBody AmountRequest request){
        TransactionResponse response = walletService.depositAmount(
                id, request.getAmountCents()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wallets/{id}/withdraw")
    public ResponseEntity<TransactionResponse> withdrawAmount(@PathVariable Long id, @RequestBody AmountRequest request){
        TransactionResponse response = walletService.withdrawAmount(
                id, request.getAmountCents()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transferAmount(@RequestBody TransferRequest request){
        TransactionResponse response = walletService.transferAmount(
                 request.getFromWalletId(), request.getToWalletId(), request.getAmountCents()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable Long id){
        WalletResponse response = walletService.getWallet(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("wallets/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransaction(@PathVariable Long id){
        List <TransactionResponse> response = walletService.getTransactions(id);
        return ResponseEntity.ok(response);
    }

}
