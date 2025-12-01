package com.example.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carrental.model.Wallet;
import com.example.carrental.repository.WalletRepository;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet getWalletByNumber(String walletNumber) {
        return walletRepository.findByWalletNumber(walletNumber);
    }


    public Wallet findByWalletNumber(String walletNumber) {  
        return walletRepository.findByWalletNumber(walletNumber);  
    }
    
    public boolean validateWalletPin(String walletNumber, String enteredPin) {
        Wallet wallet = walletRepository.findByWalletNumber(walletNumber);
        if (wallet == null) {
            return false;
        }
        return wallet.getPin().equals(enteredPin); // Direct comparison (no hashing)
    }

    public Long getWalletBalance(String walletNumber) {
        Wallet wallet = walletRepository.findByWalletNumber(walletNumber);
        return wallet != null ? wallet.getBalance() : null;
    }

    public void updateWalletBalance(String walletNumber, Long newBalance) {
        Wallet wallet = walletRepository.findByWalletNumber(walletNumber);
        if (wallet != null) {
            wallet.setBalance(newBalance);
            walletRepository.save(wallet);
        }
    }
    
    public double getBalance(String walletNumber, String pin) {
        Wallet wallet = walletRepository.findByWalletNumber(walletNumber);

        if (wallet == null) {
            throw new RuntimeException("Wallet not found");
        }

        if (!wallet.getPin().equals(pin)) {
            throw new RuntimeException("Invalid PIN");
        }

        return wallet.getBalance();
    }
}
