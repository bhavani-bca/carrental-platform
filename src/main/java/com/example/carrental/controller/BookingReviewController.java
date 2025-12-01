package com.example.carrental.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// Controller class
@RestController
@RequestMapping("/api/bookings/reviews")
public class BookingReviewController {

    @Autowired
    private BookingReviewService bookingReviewService;

    // Get all bookings for a user with reviews and without reviews
    @GetMapping("/{userId}")
    public ResponseEntity<BookingResponseWrapper> getBookings(@PathVariable Long userId) {
        BookingResponseWrapper response = bookingReviewService.getAllBookingsByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Service class
    @Service
    public static class BookingReviewService {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        // Method to fetch bookings with reviews and bookings without reviews
        public BookingResponseWrapper getAllBookingsByUserId(Long userId) {
            // Query for bookings with reviews
            String queryWithReviews = "SELECT b.id AS booking_id, b.user_id, b.vehicle_id, b.from_date, b.to_date, r.id AS review_id, r.rating, r.feedback AS comment " +
                                      "FROM bookings b LEFT JOIN reviews r ON b.id = r.booking_id " +
                                      "WHERE b.user_id = ? AND r.id IS NOT NULL";
            List<BookingReviewResponse> bookingsWithReviews = jdbcTemplate.query(queryWithReviews, new Object[]{userId}, new BeanPropertyRowMapper<>(BookingReviewResponse.class));

            // Query for bookings without reviews
            String queryWithoutReviews = "SELECT b.id AS booking_id, b.user_id, b.vehicle_id, b.from_date, b.to_date " +
                                         "FROM bookings b LEFT JOIN reviews r ON b.id = r.booking_id " +
                                         "WHERE b.user_id = ? AND r.id IS NULL";
            List<BookingResponse> bookingsWithoutReviews = jdbcTemplate.query(queryWithoutReviews, new Object[]{userId}, new BeanPropertyRowMapper<>(BookingResponse.class));

            // Wrapping both lists into a response wrapper
            BookingResponseWrapper wrapper = new BookingResponseWrapper();
            wrapper.setBookingsWithReviews(bookingsWithReviews);
            wrapper.setBookingsWithoutReviews(bookingsWithoutReviews);

            return wrapper;
        }
    }

    // Response DTO for Bookings with Reviews
    public static class BookingReviewResponse {
        private Long bookingId;
        private Long userId;
        private Long vehicleId;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;
        private Long reviewId;
        private Integer rating;
        private String comment;

        // Getters and Setters
        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public Long getVehicleId() { return vehicleId; }
        public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

        public LocalDateTime getFromDate() { return fromDate; }
        public void setFromDate(LocalDateTime fromDate) { this.fromDate = fromDate; }

        public LocalDateTime getToDate() { return toDate; }
        public void setToDate(LocalDateTime toDate) { this.toDate = toDate; }

        public Long getReviewId() { return reviewId; }
        public void setReviewId(Long reviewId) { this.reviewId = reviewId; }

        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }

        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }

    // Response DTO for Bookings without Reviews
    public static class BookingResponse {
        private Long bookingId;
        private Long userId;
        private Long vehicleId;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;

        // Getters and Setters
        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public Long getVehicleId() { return vehicleId; }
        public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

        public LocalDateTime getFromDate() { return fromDate; }
        public void setFromDate(LocalDateTime fromDate) { this.fromDate = fromDate; }

        public LocalDateTime getToDate() { return toDate; }
        public void setToDate(LocalDateTime toDate) { this.toDate = toDate; }
    }

    // Wrapper DTO for combining both lists of bookings
    public static class BookingResponseWrapper {
        private List<BookingReviewResponse> bookingsWithReviews;
        private List<BookingResponse> bookingsWithoutReviews;

        // Getters and Setters
        public List<BookingReviewResponse> getBookingsWithReviews() { return bookingsWithReviews; }
        public void setBookingsWithReviews(List<BookingReviewResponse> bookingsWithReviews) { this.bookingsWithReviews = bookingsWithReviews; }

        public List<BookingResponse> getBookingsWithoutReviews() { return bookingsWithoutReviews; }
        public void setBookingsWithoutReviews(List<BookingResponse> bookingsWithoutReviews) { this.bookingsWithoutReviews = bookingsWithoutReviews; }
    }
}
