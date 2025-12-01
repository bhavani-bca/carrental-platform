package com.example.carrental.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.carrental.dto.BookingResponseDTO;
import com.example.carrental.model.Bookings;
import com.example.carrental.repository.BookingsRepository;
import com.example.carrental.repository.VehicleRepository;

import jakarta.transaction.Transactional;

import java.util.stream.Collectors;


@Service
public class BookingService {

    @Autowired
    private BookingsRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;
    
    public Bookings saveBooking(Bookings booking) {
        return bookingRepository.save(booking);
    }

    public Bookings getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }
    
    @Scheduled(fixedRate = 60000) // Runs every 1 minute
    @Transactional
    public void updateVehicleAvailability() {
        System.out.println("🔄 Checking and updating vehicle availability...");

        // Fetch vehicles where to_date is in the future (still booked)
        List<Long> bookedVehicleIds = bookingRepository.findVehiclesStillBooked();
        if (!bookedVehicleIds.isEmpty()) {
            System.out.println("🚗 Vehicles still booked: " + bookedVehicleIds);
            vehicleRepository.updateAvailabilityByIds(bookedVehicleIds, false);
        }

        // Fetch vehicles where to_date has passed (make available)
        List<Long> expiredVehicleIds = bookingRepository.findVehiclesWithExpiredBookings();
        if (!expiredVehicleIds.isEmpty()) {
            System.out.println("✅ Vehicles now available: " + expiredVehicleIds);
            vehicleRepository.updateAvailabilityByIds(expiredVehicleIds, true);
        }
    }

    public List<BookingResponseDTO> getBookingsByUserId(Long userId) {
        List<Bookings> bookings = bookingRepository.findByUser_Id(userId);
        return bookings.stream()
                .map(BookingResponseDTO::new)
                .collect(Collectors.toList());
    }
    
}
