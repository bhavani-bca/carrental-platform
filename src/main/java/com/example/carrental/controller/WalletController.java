package com.example.carrental.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.carrental.dto.WalletBalanceRequest;
import com.example.carrental.model.CreditCard;
import com.example.carrental.model.Wallet;
import com.example.carrental.service.CreditCardService;
import com.example.carrental.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private CreditCardService creditCardService;

    // Get Wallet Balance by Wallet Number
    @PostMapping("/checkBalance")
    public ResponseEntity<String> checkBalance(@RequestBody WalletBalanceRequest request) {
        double balance = walletService.getBalance(request.getWalletNumber(), request.getPin());
        return ResponseEntity.ok("Current Balance: " + balance);
    }

    // Add Amount to Wallet from Credit Card
    @PostMapping("/addAmount")
    public ResponseEntity<?> addAmountToWallet(@RequestBody Map<String, String> request) {
        String walletNumber = request.get("walletNumber");
        String cardNumber = request.get("cardNumber");
        String cvv = request.get("cvv"); // CVV added for validation
        Long amount;

        // Validate amount
        try {
            amount = Long.parseLong(request.get("amount"));
            if (amount <= 0) {
                return ResponseEntity.badRequest().body("Amount must be greater than zero!");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid amount format!");
        }

        // Get credit card details
        CreditCard creditCard = creditCardService.getCreditCardByNumber(cardNumber);
        if (creditCard == null) {
            return ResponseEntity.badRequest().body("Credit card not found!");
        }

     // Convert CVV from String to int before comparing
        int requestCvv;
        try {
            requestCvv = Integer.parseInt(cvv); // Convert String CVV to int
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid CVV format!");
        }

        // Validate CVV
        if (creditCard.getCvv() != requestCvv) { // Compare as integers
            return ResponseEntity.badRequest().body("Invalid CVV!");
        }


        // Get credit card balance
        Long cardBalance = creditCard.getBalance();
        if (cardBalance < amount) {
            return ResponseEntity.badRequest().body("Insufficient credit card balance!");
        }

        // Get wallet details
        Wallet wallet = walletService.getWalletByNumber(walletNumber);
        if (wallet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found!");
        }

        // Deduct from Credit Card and Add to Wallet
        creditCardService.updateCardBalance(cardNumber, cardBalance - amount);
        walletService.updateWalletBalance(walletNumber, wallet.getBalance() + amount);

        return ResponseEntity.ok("Amount added successfully! Wallet updated.");
    }


    // Make Payment Using Wallet without PIN Hashing
 // Make Payment Using Wallet without PIN Hashing
    @PostMapping("/pay")
    public ResponseEntity<?> payWithWallet(@RequestBody Map<String, String> request) {
        String walletNumber = request.get("walletNumber");
        String pin = request.get("pin");
        Long amount = Long.parseLong(request.get("amount"));

        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero!");
        }

        // Get Wallet
        Wallet wallet = walletService.findByWalletNumber(walletNumber);
        if (wallet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found!");
        }

        // Directly compare PIN (without hashing)
        if (!pin.equals(wallet.getPin())) {  // FIXED: Using getPin() instead of getPinHash()
            return ResponseEntity.badRequest().body("Incorrect PIN!");
        }

        // Check Balance
        if (wallet.getBalance() < amount) {
            return ResponseEntity.badRequest().body("Insufficient Wallet Balance!");
        }

        // Deduct Amount
        walletService.updateWalletBalance(walletNumber, wallet.getBalance() - amount);

        return ResponseEntity.ok("Payment Successful! Remaining Wallet Balance: " + (wallet.getBalance() - amount));
    }

}
