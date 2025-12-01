package com.example.carrental.model;

import jakarta.persistence.*;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference 
    private Users user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Bookings booking;

    private int rating;
    private String feedback;

    private Instant createdAt = Instant.now();
    private Instant modifiedAt = Instant.now();

    // ✅ Constructor
    public Reviews(Users user, Vehicle vehicle, Bookings booking, int rating, String feedback) {
        this.user = user;
        this.vehicle = vehicle;
        this.booking = booking;
        this.rating = rating;
        this.feedback = feedback;
        this.createdAt = Instant.now();
        this.modifiedAt = Instant.now();
    }

    // ✅ Empty Constructor (Required by JPA)
    public Reviews() {}

    // ✅ Getter Methods
    public Long getId() {
        return id;
    }

    public Users getUser() {
        return user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Bookings getBooking() {
        return booking;
    }

    public int getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }
}
