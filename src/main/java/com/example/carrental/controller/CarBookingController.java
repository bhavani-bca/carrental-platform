package com.example.carrental.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.carrental.dto.BookingRequest;
import com.example.carrental.dto.BookingResponseDTO;
import com.example.carrental.dto.PaymentRequest;
import com.example.carrental.model.Bookings;
import com.example.carrental.model.CreditCard;
import com.example.carrental.model.Payment;
import com.example.carrental.model.Users;
import com.example.carrental.model.Vehicle;
import com.example.carrental.model.Wallet;
import com.example.carrental.repository.CreditCardRepository;
import com.example.carrental.repository.WalletRepository;
import com.example.carrental.service.BookingService;
import com.example.carrental.service.CreditCardService;
import com.example.carrental.service.EmailService;
import com.example.carrental.service.OtpService;
import com.example.carrental.service.PaymentService;
import com.example.carrental.service.UserService;
import com.example.carrental.service.VehicleService;
import com.example.carrental.service.WalletService;

@RestController
@RequestMapping("/booking")
public class CarBookingController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private CreditCardService creditCardService;
    
    @Autowired
    private WalletService walletService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private OtpService otpService;
    
    
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @PostMapping("/bookings/calculate-rent")
    public ResponseEntity<?> calculateRent(@RequestBody Map<String, Object> request) {
        Long vehicleId = ((Number) request.get("vehicleId")).longValue();
        String startDateStr = (String) request.get("startDate");
        String endDateStr = (String) request.get("endDate");

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        // Validation: start date should not be in the past
        if (startDate.isBefore(currentDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Start date cannot be in the past"));
        }

     // Validation: end date should be strictly greater than start date
        if (!endDate.isAfter(startDate)) { // Instead of isBefore(), use isAfter()
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "End date must be strictly after start date"));
        }


        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Vehicle not found"));
        }

        if (!vehicle.isAvailable()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Vehicle not available"));
        }

        // Calculate total rent based on rental duration
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        Long totalRent = Math.max(totalDays, 1) * (long) vehicle.getRent(); // Ensure at least 1-day rent

        return ResponseEntity.ok(Map.of("totalRent", totalRent));
    }


    
    @PostMapping("/bookCar")
    public ResponseEntity<?> bookCar(@RequestBody BookingRequest request) {
        if (request.getUserId() == null) {
            return ResponseEntity.badRequest().body("User ID is missing in request!");
        }
        if (request.getVehicleId() == null) {
            return ResponseEntity.badRequest().body("Vehicle ID is missing in request!");
        }

        Vehicle vehicle = vehicleService.getVehicleById(request.getVehicleId());
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found!");
        }

        if (!vehicle.getIsAvailable()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vehicle is not available!");
        }

        Users user = userService.getUserById(request.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        long days = ChronoUnit.DAYS.between(request.getFromDate(), request.getToDate());
        days = Math.max(days, 1);
        Long totalRent = days * vehicle.getRent();

        Instant fromDateInstant = request.getFromDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant toDateInstant = request.getToDate().atStartOfDay(ZoneId.systemDefault()).toInstant();

        Bookings booking = new Bookings(vehicle, user, fromDateInstant, toDateInstant);
        booking.setStatus("OTP_PENDING"); // ✅ Booking is OTP_PENDING
        Bookings savedBooking = bookingService.saveBooking(booking);

        // ✅ Generate OTP and send email
        String otp = otpService.generateOtp(savedBooking.getId());
        String subject = "Booking OTP Verification";
        String message = "Dear " + user.getFullName() + ",\n\n" +
                         "Your OTP for booking verification is: " + otp + "\n\n" +
                         "Please enter this OTP to confirm your booking.";
        emailService.sendEmail(user.getEmail(), subject, message);

        return ResponseEntity.ok(Map.of(
            "bookingId", savedBooking.getId(),
            "user", user,
            "vehicle", vehicle,
            "totalRent", totalRent,
            "message", "OTP sent to your email."
        ));
    }
    

    
    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, Object> request) {
        try {
            System.out.println("🔍 Received OTP verification request: " + request);

            // Validate input
            if (!request.containsKey("bookingId") || !request.containsKey("otp")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Booking ID and OTP are required!");
            }

            // Convert bookingId safely
            Long bookingId;
            try {
                bookingId = Long.parseLong(request.get("bookingId").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Invalid Booking ID format!");
            }

            String userOtp = request.get("otp").toString();

            // Retrieve booking details
            Bookings booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("🚫 Booking not found!");
            }

            // Verify OTP
            boolean isOtpValid = otpService.verifyOtp(bookingId, userOtp);
            if (!isOtpValid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Invalid OTP!");
            }

            // ✅ Update booking status
            booking.setStatus("PENDING");
            bookingService.saveBooking(booking);

            return ResponseEntity.ok("✅ OTP verified! You can now proceed to payment.");

        } catch (Exception e) {
            e.printStackTrace(); // Log the actual error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("🔥 Server Error: " + e.getMessage());
        }
    }

    // STEP 3: Check Balance before Payment
    @GetMapping("/checkBalance/{cardNumber}")
    public ResponseEntity<?> checkBalance(@PathVariable String cardNumber) {
        Long balance = creditCardService.getCardBalance(cardNumber); // Fetch balance using card number
        if (balance == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found or balance unavailable!");
        }
        return ResponseEntity.ok("Current Balance: " + balance);
    }


    @PostMapping("/makePayment")
    public ResponseEntity<?> makePayment(@RequestBody PaymentRequest request) {
        Bookings booking = bookingService.getBookingById(request.getBookingId());
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found!");
        }

        // Ensure OTP is verified before allowing payment
        if (!"PENDING".equals(booking.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please verify OTP before making a payment!");
        }

        Long totalRent = booking.calculateTotalRent();
        Long balance = null;
        boolean isWalletPayment = false;

        if (request.getPaymentType().equalsIgnoreCase("WALLET")) {
            if (request.getWalletPin() == null || request.getWalletPin().isEmpty()) {
                return ResponseEntity.badRequest().body("Wallet PIN is required for wallet payments!");
            }

            boolean isPinValid = walletService.validateWalletPin(request.getPaymentNumber(), request.getWalletPin());
            if (!isPinValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Wallet PIN!");
            }

            balance = walletService.getWalletBalance(request.getPaymentNumber());
            isWalletPayment = true;
        } else if (request.getPaymentType().equalsIgnoreCase("CREDIT_CARD")) {
            balance = creditCardService.getCardBalance(request.getPaymentNumber());
        } else {
            return ResponseEntity.badRequest().body("Invalid payment method! Use 'WALLET' or 'CREDIT_CARD'.");
        }

        if (balance == null || balance < totalRent) {
            return ResponseEntity.badRequest().body("Insufficient balance or invalid payment method!");
        }

        if (isWalletPayment) {
            walletService.updateWalletBalance(request.getPaymentNumber(), balance - totalRent);
        } else {
            creditCardService.updateCardBalance(request.getPaymentNumber(), balance - totalRent);
        }

        Payment payment = new Payment(booking.getUser(), booking, totalRent, "SUCCESS");
        payment.setTransactionId(UUID.randomUUID().toString());
        paymentService.savePayment(payment);

        // Confirm booking
        booking.setStatus("CONFIRMED");
        bookingService.saveBooking(booking);

        // Update vehicle availability
        Vehicle bookedVehicle = booking.getVehicle();
        if (bookedVehicle != null) {
            bookedVehicle.setIsAvailable(false);
            vehicleService.updateVehicle(bookedVehicle);
        }

        // Send confirmation email
        String userEmail = booking.getUser().getEmail();
        String subject = "Booking Confirmation - Payment Successful";
        String message = "Dear " + booking.getUser().getFullName() + ",\n\n" +
                         "Your payment of " + totalRent + " has been successfully processed. " +
                         "Your booking (ID: " + booking.getId() + ") is now confirmed.\n\n" +
                         "Thank you for choosing our service!";
        emailService.sendEmail(userEmail, subject, message);

        return ResponseEntity.ok("Payment successful! Booking confirmed.");
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookingResponseDTO> bookings = bookingService.getBookingsByUserId(userId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }
    
    
    
    
}
