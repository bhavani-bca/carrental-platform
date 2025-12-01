package com.example.carrental.service;

import com.example.carrental.model.Reviews;
import com.example.carrental.model.Bookings;
import com.example.carrental.repository.ReviewsRepository;
import com.example.carrental.repository.BookingsRepository;
import com.example.carrental.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewsRepository reviewsRepository;
    private final BookingsRepository bookingsRepository;
    private final VehicleRepository vehicleRepository;

    public ReviewService(ReviewsRepository reviewsRepository, BookingsRepository bookingsRepository, VehicleRepository vehicleRepository) {
        this.reviewsRepository = reviewsRepository;
        this.bookingsRepository = bookingsRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Reviews submitReview(Long userId, Long vehicleId, int rating, String feedback) {
        // ✅ Check if the user has already submitted a review for this vehicle
        List<Reviews> existingReviews = reviewsRepository.findByUser_IdAndVehicle_Id(userId, vehicleId);
        if (!existingReviews.isEmpty()) {
            throw new IllegalArgumentException("You have already submitted a review for this vehicle.");
        }

        // ✅ Check if user has a confirmed booking for the vehicle
        Optional<Bookings> booking = bookingsRepository.findByUser_IdAndVehicle_IdAndStatus(userId, vehicleId, "CONFIRMED");
        if (booking.isEmpty()) {
            throw new IllegalArgumentException("Review submission failed: No confirmed booking found for this vehicle.");
        }

        // ✅ Save the review
        Reviews review = new Reviews(booking.get().getUser(), booking.get().getVehicle(), booking.get(), rating, feedback);
        return reviewsRepository.save(review);
    }

    public List<Reviews> getReviewsByVehicle(Long vehicleId) {
        return reviewsRepository.findByVehicleId(vehicleId);
    }
}
