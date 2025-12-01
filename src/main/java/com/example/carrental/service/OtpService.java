package com.example.carrental.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
    private final Map<Long, String> otpStorage = new HashMap<>();
    private final Random random = new Random();

    // Generate OTP for a given bookingId
    public String generateOtp(Long bookingId) {
        String otp = String.format("%06d", random.nextInt(1000000)); // 6-digit OTP
        otpStorage.put(bookingId, otp);
        return otp;
    }

    // Verify OTP for a given bookingId
    public boolean verifyOtp(Long bookingId, String userOtp) {
        String storedOtp = otpStorage.get(bookingId);
        if (storedOtp != null && storedOtp.equals(userOtp)) {
            otpStorage.remove(bookingId); // Remove OTP after successful verification
            return true;
        }
        return false;
    }
}
