package com.example.carrental.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "bookings")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonIgnore
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users user;

    @Column(nullable = false)
    private Instant fromDate;

    @Column(nullable = false)
    private Instant toDate;

    @Column(nullable = false)
    private String status;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonIgnore
    private Payment payment;

    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant modifiedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.modifiedAt = Instant.now();
        this.status = "PENDING"; // Default status
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = Instant.now();
    }

    // Constructors
    public Bookings() {}

    public Bookings(Vehicle vehicle, Users user, Instant fromDate, Instant toDate) {
        this.vehicle = vehicle;
        this.user = user;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = "PENDING";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Instant getFromDate() { return fromDate; }
    public void setFromDate(Instant fromDate) { this.fromDate = fromDate; }

    public Instant getToDate() { return toDate; }
    public void setToDate(Instant toDate) { this.toDate = toDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getModifiedAt() { return modifiedAt; }
    
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setModifiedAt(Instant modifiedAt) { this.modifiedAt = modifiedAt; }


    public Long calculateTotalRent() {
        long days = ChronoUnit.DAYS.between(fromDate, toDate);
        days = Math.max(days, 1); // Ensure at least 1-day rent is applied
        return this.vehicle.getRent() * days;
    }
    
}
