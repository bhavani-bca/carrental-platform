package com.example.carrental.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import com.example.carrental.model.Bookings;

public class BookingResponseDTO {
    private Long bookingId;
    private Long userId;
    private Long vehicleId;
    private LocalDate fromDate;
    private LocalDate toDate;

    // Constructor
    public BookingResponseDTO(Bookings booking) {
        this.bookingId = booking.getId();
        this.userId = booking.getUser().getId();
        this.vehicleId = booking.getVehicle().getId();
       
     // Convert Instant to LocalDate
        this.fromDate = convertInstantToLocalDate(booking.getFromDate());
        this.toDate = convertInstantToLocalDate(booking.getToDate());
    }

    // Getters
    public Long getBookingId() {
        return bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

  

    // Setters
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
    
    private LocalDate convertInstantToLocalDate(Instant instant) {
        return instant != null ? instant.atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

}
