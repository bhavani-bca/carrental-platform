package com.example.carrental.dto;

public class WalletBalanceRequest {
    private String walletNumber;
    private String pin;

    // Getters and Setters
    public String getWalletNumber() {
        return walletNumber;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
