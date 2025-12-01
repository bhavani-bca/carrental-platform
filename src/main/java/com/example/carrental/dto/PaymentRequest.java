package com.example.carrental.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class PaymentRequest {
    private Long userId;
    private Long vehicleId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Double totalRent;
    private String transactionId;
    
    private String walletPin;  // Ensure this field exists

    private Long bookingId;
    
    private String paymentType;  // "CREDIT_CARD" or "WALLET"
    private String paymentNumber;  // Card number or Wallet number
   
    
    @Column(name = "card_number") // Ensure the correct column name
    private String cardNumber;
    
    // Constructors
    public PaymentRequest() {}

    public PaymentRequest(Long userId, Long vehicleId, LocalDate fromDate, LocalDate toDate, Double totalRent, String transactionId) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalRent = totalRent;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Double getTotalRent() {
        return totalRent;
    }

    public void setTotalRent(Double totalRent) {
        this.totalRent = totalRent;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public Long getBookingId() {
        return bookingId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }
    
    public String getWalletPin() {  // Add this getter
        return walletPin;
    }

    public void setWalletPin(String walletPin) {  // Add this setter
        this.walletPin = walletPin;
    }
}
