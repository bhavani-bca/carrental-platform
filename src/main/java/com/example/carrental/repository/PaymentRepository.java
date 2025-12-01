package com.example.carrental.repository;

import com.example.carrental.model.Bookings;
import com.example.carrental.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionId(String transactionId);
    boolean existsByBooking(Bookings booking); // ✅ Add this method
}
