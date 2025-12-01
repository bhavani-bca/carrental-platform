package com.example.carrental.dto;

import java.time.LocalDate;

import com.example.carrental.model.Bookings;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingRequest {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("vehicleId")
    private Long vehicleId;

    @JsonProperty("fromDate")
    private LocalDate fromDate;

    @JsonProperty("toDate")
    private LocalDate toDate;

    // Getters
    public Long getUserId() { return userId; }
    public Long getVehicleId() { return vehicleId; }
    public LocalDate getFromDate() { return fromDate; }
    public LocalDate getToDate() { return toDate; }

    // Setters
    public void setUserId(Long userId) { this.userId = userId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    @Override
    public String toString() {
        return "BookingRequest(userId=" + userId + ", vehicleId=" + vehicleId + ", fromDate=" + fromDate + ", toDate=" + toDate + ")";
    }
    
   
}
