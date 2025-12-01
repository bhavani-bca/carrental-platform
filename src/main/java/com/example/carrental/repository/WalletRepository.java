package com.example.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.carrental.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByWalletNumber(String walletNumber);  
}
