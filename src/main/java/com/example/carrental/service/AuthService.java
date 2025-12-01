package com.example.carrental.service;

import com.example.carrental.dto.LoginRequest;
import com.example.carrental.dto.RegisterRequest;
import com.example.carrental.dto.UpdateUserRequest;
import com.example.carrental.dto.PasswordResetRequest;
import com.example.carrental.model.Users;
import com.example.carrental.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class AuthService {
    private final UsersRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    
    
    // In-memory storage for OTPs
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Random random = new Random();


    public AuthService(UsersRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.mailSender = mailSender;
    }


    // Step 1: Generate OTP and send it via email
    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        // Generate a 6-digit OTP
        String otp = String.format("%06d", random.nextInt(1000000));

        // Store OTP in memory (linked to email)
        otpStorage.put(request.getEmail(), otp);

        // Send OTP to the user's email
        sendEmail(request.getEmail(), "OTP for Registration", 
                  "Your OTP is: " + otp + "\nPlease verify your email to complete registration.");

        return "OTP sent to email. Please verify to complete registration.";
    }

    public String verifyOtp(RegisterRequest request) {
        String email = request.getEmail();
        String providedOtp = request.getOtp();
        
        if (otpStorage.containsKey(email) && otpStorage.get(email).equals(providedOtp)) {
            Users user = new Users();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(email);
            user.setAddress(request.getAddress());
            user.setContact(request.getContact());
            user.setLicence(request.getLicence());
            user.setLicenceImg(request.getLicenceImg());
            user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash password

            userRepository.save(user);
            otpStorage.remove(email);

            // Send confirmation email after successful registration
            sendEmail(email, "Registration Confirmed", 
                      "Dear " + request.getFirstName() + ",\n\nYour registration has been successfully completed. You can now log in.\n\nBest regards,\nCar Rental Team");

            return "Registration successful! A confirmation email has been sent.";
        }
        return "Invalid OTP!";
    }

   
            

    // Email sending function
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public String login(LoginRequest request) {
        Optional<Users> user = userRepository.findByEmail(request.getEmail());
        return (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) ? 
               "Login successful!" : "Invalid credentials!";
    }

    public String resetPassword(PasswordResetRequest request) {
        Optional<Users> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(request.getNewPassword())); // Hash new password
            userRepository.save(user.get());
            sendEmail(request.getEmail(), "Password Reset Successful", "Your password has been reset successfully.");
            return "Password reset successful!";
        }
        return "User not found!";
    }
    
    
    public String requestDeleteOtp(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        
        if (user.isPresent()) {
            String otp = String.format("%06d", random.nextInt(1000000));
            otpStorage.put(email, otp); // Store OTP in memory
            
            sendEmail(email, "OTP for Account Deletion",
                    "Your OTP to delete your account is: " + otp + "\nEnter this OTP to confirm deletion.");
            
            return "OTP sent to your email. Enter the OTP to confirm account deletion.";
        }
        return "User not found!";
    }

    public String verifyDeleteOtp(String email, String otp) {
        if (otpStorage.containsKey(email) && otpStorage.get(email).equals(otp)) {
            Optional<Users> user = userRepository.findByEmail(email);
            
            if (user.isPresent()) {
                userRepository.delete(user.get());
                otpStorage.remove(email);
                return "Account deleted successfully!";
            }
            return "User not found!";
        }
        return "Invalid OTP!";
    }

    
    
    // ✅ Get User Profile
    public Users getUserProfile(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // ✅ Update User Profile
    public String updateUserProfile(UpdateUserRequest request) {
        Optional<Users> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setAddress(request.getAddress());
            user.setContact(request.getContact());
            user.setLicence(request.getLicence());
            user.setLicenceImg(request.getLicenceImg());

            userRepository.save(user);
            return "User profile updated successfully!";
        }
        return "User not found!";
    }
    
    public Long getUserIdByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null; // Prevents unnecessary DB calls for empty email
        }
        Users user = userRepository.findByEmail(email).orElse(null);
        return (user != null) ? user.getId() : null;
    }
    
    
}