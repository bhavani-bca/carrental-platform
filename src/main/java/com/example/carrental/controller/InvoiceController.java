package com.example.carrental.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/{bookingId}")
    public ResponseEntity<Map<String, Object>> getInvoice(@PathVariable Long bookingId) {
        String sql = """
            SELECT b.id AS booking_id, b.from_date, b.to_date, b.status, 
                   p.amount, p.payment_status, p.transaction_id, 
                   v.model, v.company, v.rent, v.no_plate,
                   u.id AS user_id, u.first_name AS user_first_name, 
                   u.last_name AS user_last_name, u.email AS user_email
            FROM bookings b
            JOIN payments p ON b.id = p.booking_id
            JOIN vehicle v ON b.vehicle_id = v.id
            JOIN users u ON b.user_id = u.id
            WHERE b.id = ?
        """;

        try {
            Map<String, Object> invoice = jdbcTemplate.queryForMap(sql, bookingId);
            return ResponseEntity.ok(invoice);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }
}
