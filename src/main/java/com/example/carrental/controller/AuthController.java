package com.example.carrental.controller;

import com.example.carrental.dto.LoginRequest;
import com.example.carrental.dto.RegisterRequest;
import com.example.carrental.dto.PasswordResetRequest;
import com.example.carrental.dto.UpdateUserRequest; // ✅ Import this
import com.example.carrental.model.Users; // ✅ Import Users model
import com.example.carrental.service.AuthService;

import java.util.Collections;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
        private final AuthService authService;

        public AuthController(AuthService authService) {
            this.authService = authService;
        }

        @PostMapping("/register")
        public String register(@RequestBody RegisterRequest request) {
            return authService.register(request);
        }

        @PostMapping("/verify-otp")
        public String verifyOtp(@RequestBody RegisterRequest request) {
            return authService.verifyOtp(request);
        }


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest request) {
        return authService.resetPassword(request);
    }
    
    
    // Request OTP for account deletion
    @PostMapping("/delete/request-otp")
    public String requestDeleteOtp(@RequestBody Map<String, String> request) {
        return authService.requestDeleteOtp(request.get("email"));
    }

    // Verify OTP and delete account
    @DeleteMapping("/delete/verify")
    public String verifyDeleteOtp(@RequestBody Map<String, String> request) {
        return authService.verifyDeleteOtp(request.get("email"), request.get("otp"));
    }

    
    // ✅ Get user profile
    @GetMapping("/profile/{email}")
    public Users getUserProfile(@PathVariable String email) {
        return authService.getUserProfile(email);
    }

    // ✅ Update user profile
    @PutMapping("/profile/update")
    public String updateUserProfile(@RequestBody UpdateUserRequest request) {
        return authService.updateUserProfile(request);
    }
    

    @GetMapping("/user-id")
    public ResponseEntity<?> getUserId(@RequestParam(required = true) String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Email parameter is required"));
        }

        Long userId = authService.getUserIdByEmail(email);
        if (userId != null) {
            return ResponseEntity.ok(Collections.singletonMap("userId", userId));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User not found"));
        }
    }
}