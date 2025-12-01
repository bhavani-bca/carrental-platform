package com.example.carrental.repository;

import com.example.carrental.model.Reviews;
import com.example.carrental.model.Users;
import com.example.carrental.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

	
	List<Reviews> findByVehicleId(Integer vehicleId);
	
	// ✅ Get all reviews for a specific vehicle
    List<Reviews> findByVehicleId(Long vehicleId);

    // ✅ Check if a user has already reviewed a vehicle
    List<Reviews> findByUser_IdAndVehicle_Id(Long userId, Long vehicleId);
	
}

