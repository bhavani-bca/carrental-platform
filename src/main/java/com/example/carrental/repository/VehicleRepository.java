package com.example.carrental.repository;

import com.example.carrental.model.Vehicle;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	@Query("SELECT v FROM Vehicle v " +
		       "WHERE (:company IS NULL OR :company = '' OR v.company = :company) " +
		       "AND (:fuelType IS NULL OR :fuelType = '' OR v.fuelType = :fuelType) " +
		       "AND (:isAvailable IS NULL OR v.isAvailable = :isAvailable) " +
		       "AND (:seatingCapacity IS NULL OR v.seatingCapacity = :seatingCapacity) " +
		       "AND (:maxRent IS NULL OR v.rent <= :maxRent) " +
		       "AND (:search IS NULL OR :search = '' OR LOWER(v.model) LIKE LOWER(CONCAT('%', :search, '%')))")
		Page<Vehicle> filterVehicles(
		        @Param("company") String company,
		        @Param("fuelType") String fuelType,
		        @Param("isAvailable") Boolean isAvailable,
		        @Param("seatingCapacity") Integer seatingCapacity,
		        @Param("maxRent") Long maxRent,
		        @Param("search") String search,
		        Pageable pageable
		);
	
	List<Vehicle> findByRentLessThanEqual(Long maxRent);

	@Modifying
	@Query("UPDATE Vehicle v SET v.isAvailable = :availability WHERE v.id IN :vehicleIds")
	void updateAvailabilityByIds(@Param("vehicleIds") List<Long> vehicleIds, @Param("availability") boolean availability);


}
