package com.example.carrental.controller;

import com.example.carrental.dto.ReviewRequest;
import com.example.carrental.model.Reviews;
import com.example.carrental.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) { // ✅ Correct constructor placement
        this.reviewService = reviewService;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitReview(@RequestBody ReviewRequest reviewRequest) {
        try {
            if (reviewRequest.getUserId() == null || reviewRequest.getVehicleId() == null || 
                reviewRequest.getFeedback() == null || reviewRequest.getFeedback().isEmpty()) {
                return ResponseEntity.badRequest().body("User ID, Vehicle ID, and Feedback must not be null or empty.");
            }

            Reviews review = reviewService.submitReview(
                reviewRequest.getUserId(),
                reviewRequest.getVehicleId(),
                reviewRequest.getRating(),
                reviewRequest.getFeedback()
            );
            return ResponseEntity.ok(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // ✅ Handles known issues
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<Reviews>> getReviews(@PathVariable Long vehicleId) { // ✅ Use Long instead of Integer
        return ResponseEntity.ok(reviewService.getReviewsByVehicle(vehicleId));
    }

}
