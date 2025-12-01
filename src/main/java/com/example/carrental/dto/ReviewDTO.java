package com.example.carrental.dto;

import java.time.Instant;

public class ReviewDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long vehicleId;
    private int rating;
    private String feedback;
    private Instant createdAt;
    private Instant modifiedAt;

    public ReviewDTO(Long id, Long userId, String userName, Long vehicleId, int rating, String feedback, Instant createdAt, Instant modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.vehicleId = vehicleId;
        this.rating = rating;
        this.feedback = feedback;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public Long getVehicleId() { return vehicleId; }
    public int getRating() { return rating; }
    public String getFeedback() { return feedback; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getModifiedAt() { return modifiedAt; }
}
