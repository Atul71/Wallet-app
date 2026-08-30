package com.wallet.wallet_app.service;

import com.wallet.wallet_app.dto.TransactionResponse;
import com.wallet.wallet_app.dto.WalletResponse;
import com.wallet.wallet_app.entity.Transaction;
import com.wallet.wallet_app.entity.Wallet;
import com.wallet.wallet_app.repository.TransactionRepository;
import com.wallet.wallet_app.repository.WalletRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final StringRedisTemplate redisTemplate;

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository, StringRedisTemplate redisTemplate) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.redisTemplate = redisTemplate;
    }

    public WalletResponse createWallet(Long userId, String currency){
        Wallet wallet = new Wallet(userId, currency);
        Wallet saved = walletRepository.save(wallet);
        return new WalletResponse(saved.getId(), saved.getUserId(), saved.getBalanceCents(), saved.getCurrency());
    }

    public WalletResponse getWallet(Long id){

        String key = "wallet:balance:" + id;
        String cached = redisTemplate.opsForValue().get(key);

        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (cached != null) {
            System.out.println("CACHE HIT for wallet " + id);
            return new WalletResponse(wallet.getId(), wallet.getUserId(), Long.parseLong(cached), wallet.getCurrency());
        }
        System.out.println("CACHE MISS for wallet " + id);
        redisTemplate.opsForValue().set(key, String.valueOf(wallet.getBalanceCents()));
        return new WalletResponse(wallet.getId(), wallet.getUserId(), wallet.getBalanceCents(), wallet.getCurrency());
    }

    public TransactionResponse depositAmount(Long id, long amount){
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (amount<=0){
            throw new RuntimeException("Amount cannot be negative");
        }
        Transaction transaction = new Transaction(null, id, Transaction.TransactionType.DEPOSIT, amount, Transaction.TransactionStatus.PENDING, null);
        wallet.setBalanceCents(wallet.getBalanceCents()+amount);
        Wallet saveWalllet = walletRepository.save(wallet);
        transaction.setStatus(Transaction.TransactionStatus.valueOf("COMPLETED"));
        Transaction saveTransaction = transactionRepository.save(transaction);

        redisTemplate.delete("wallet:balance:" + id);

        return new TransactionResponse(saveTransaction.getId(), transaction.getType().name(), transaction.getAmountCents(), transaction.getStatus().name(), transaction.getCreatedAt());

    }

    public TransactionResponse withdrawAmount(Long id, long amount){
        Wallet wallet = walletRepository.findById(id).orElseThrow(()->new RuntimeException("Wallet not found"));
        if(amount <= 0){
            throw new RuntimeException("Amount to withdraw must be positive");
        }
        if(amount > wallet.getBalanceCents()){
            throw new RuntimeException("Insufficient Balance cannot withdraw amount");
        }
        Transaction transaction = new Transaction(id, null, Transaction.TransactionType.WITHDRAW, amount, Transaction.TransactionStatus.PENDING, null);
        wallet.setBalanceCents(wallet.getBalanceCents()-amount);
        Wallet saveWallet = walletRepository.save(wallet);
        transaction.setStatus(Transaction.TransactionStatus.valueOf("COMPLETED"));
        Transaction saveTransaction = transactionRepository.save(transaction);
        redisTemplate.delete("wallet:balance:" + id);
        return new TransactionResponse(saveTransaction.getId(), transaction.getType().name(), transaction.getAmountCents(), transaction.getStatus().name(), transaction.getCreatedAt());

    }

    @Transactional
    public TransactionResponse transferAmount(Long fromWalletId, Long toWalletId, long amount){
        Wallet toWallet = walletRepository.findById(toWalletId).orElseThrow(()->new RuntimeException("Wallet not found"));
        Wallet fromWallet = walletRepository.findById(fromWalletId).orElseThrow(()->new RuntimeException("Wallet not found"));
        if(amount <= 0){
            throw new RuntimeException("Amount to withdraw must be positive");
        }

        if(amount > fromWallet.getBalanceCents()){
            throw new RuntimeException("Insufficient balance to withdraw amount");
        }

        Transaction transaction = new Transaction(fromWalletId, toWalletId, Transaction.TransactionType.TRANSFER, amount, Transaction.TransactionStatus.PENDING, null);
        toWallet.setBalanceCents(toWallet.getBalanceCents()+amount);
        fromWallet.setBalanceCents(fromWallet.getBalanceCents()-amount);
        Wallet saveToWallet = walletRepository.save(toWallet);
        Wallet saveFromWallet = walletRepository.save(fromWallet);
        transaction.setStatus(Transaction.TransactionStatus.valueOf("COMPLETED"));
        Transaction saveTransaction = transactionRepository.save(transaction);

        redisTemplate.delete("wallet:balance:" + fromWalletId);
        redisTemplate.delete("wallet:balance:" + toWalletId);

        return new TransactionResponse(saveTransaction.getId(), transaction.getType().name(), transaction.getAmountCents(), transaction.getStatus().name(), transaction.getCreatedAt());

    }

    public List<TransactionResponse> getTransactions(Long walletId){
        List<Transaction> transactionList = transactionRepository.findByFromWalletIdOrToWalletId(walletId, walletId);
        List<TransactionResponse> result = new ArrayList<>();
        for (Transaction t : transactionList) {
            result.add(new TransactionResponse(t.getId(), t.getType().name(), t.getAmountCents(), t.getStatus().name(), t.getCreatedAt()));
        }
        return result;
    }
}
