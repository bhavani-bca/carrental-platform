package com.example.carrental.repository;

import com.example.carrental.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long> {
	
    Optional<Bookings> findByUserIdAndVehicleId(Long userId, Long vehicleId);
    
    // ✅ Check if a booking exists with CONFIRMED status for the given user and vehicle
    Optional<Bookings> findByUser_IdAndVehicle_IdAndStatus(Long userId, Long vehicleId, String status);
    
 // Find vehicles still booked (to_date is in the future)
    @Query("SELECT b.vehicle.id FROM Bookings b WHERE b.toDate >= CURRENT_TIMESTAMP")
    List<Long> findVehiclesStillBooked();

    // Find vehicles whose booking has expired (to_date is in the past)
    @Query("SELECT b.vehicle.id FROM Bookings b WHERE b.toDate < CURRENT_TIMESTAMP")
    List<Long> findVehiclesWithExpiredBookings();

    List<Bookings> findByUser_Id(Long userId);  // Correct syntax
}
