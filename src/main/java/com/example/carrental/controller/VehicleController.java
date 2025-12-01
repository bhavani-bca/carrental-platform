package com.example.carrental.controller;

import com.example.carrental.model.Vehicle;
import com.example.carrental.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public Page<Vehicle> getFilteredVehicles(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) Integer seatingCapacity,
            @RequestParam(required = false) Long maxRent,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        return vehicleService.getFilteredVehicles(company, fuelType, isAvailable, seatingCapacity, maxRent, search, page, size);
    }
}
