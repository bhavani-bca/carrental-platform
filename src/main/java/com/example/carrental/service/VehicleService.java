package com.example.carrental.service;

import com.example.carrental.model.Vehicle;
import com.example.carrental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    // Fetch vehicles with optional filters, search, and pagination
    public Page<Vehicle> getFilteredVehicles(
            String company, String fuelType, Boolean isAvailable, Integer seatingCapacity,
            Long maxRent, String search, int page, int size) {

        return vehicleRepository.filterVehicles(
                company, fuelType, isAvailable, seatingCapacity, maxRent, search, PageRequest.of(page, size));
    }
    
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null); // Fetch vehicle or return null
    }
    
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
    
    public void updateVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }
}
