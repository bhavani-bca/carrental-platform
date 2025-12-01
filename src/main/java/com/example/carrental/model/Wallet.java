package com.example.carrental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String walletNumber;
    
    @Column(nullable = false)
    private Long balance;
    

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT '0000'")
    private String pin;

    public Wallet() {}

    public Wallet(String walletNumber, Long balance, String pin) { // Include PIN in constructor
        this.walletNumber = walletNumber;
        this.balance = balance;
        this.pin = pin;
    }
    
    public Long getId() {
        return id;
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
    
    public String getPin() { // Add this getter method
        return pin;
    }

    public void setPin(String pin) { // Setter for pin
        this.pin = pin;
    }

}
