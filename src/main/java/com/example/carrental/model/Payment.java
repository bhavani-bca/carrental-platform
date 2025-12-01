package com.example.carrental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Bookings booking;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false, length = 50)
    private String paymentStatus;

    @Column(nullable = false, unique = true)
    private String transactionId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

 // ✅ Default Constructor (Required for JPA)
    public Payment() {}

    // ✅ Add This Constructor
    public Payment(Users user, Bookings booking, double amount, String paymentStatus) {
        this.user = user;
        this.booking = booking;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.createdAt = Instant.now(); // Automatically set the timestamp
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Bookings getBooking() { return booking; }
    public void setBooking(Bookings booking) { this.booking = booking; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public Instant getCreatedAt() { return createdAt; }
    
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

}
