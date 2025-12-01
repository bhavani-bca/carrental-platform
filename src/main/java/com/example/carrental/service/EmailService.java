package com.example.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private EmailService emailService;
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ✅ Existing method: Send Registration Email
    public void sendRegistrationEmail(String toEmail, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to Car Rental Service!");
        message.setText("Dear " + userName + ",\n\nThank you for registering with our Car Rental service. We are excited to have you on board!\n\nBest regards,\nCar Rental Team");

        mailSender.send(message);
    }

    // ✅ New method: Send Booking Confirmation Email
    public void sendBookingConfirmation(String toEmail, String userName, String vehicleModel, String fromDate, String toDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Booking Confirmation - Car Rental");
        message.setText("Hello " + userName + ",\n\n" +
                        "Your booking for the vehicle **" + vehicleModel + "** has been confirmed.\n\n" +
                        "📅 From: " + fromDate + "\n" +
                        "📅 To: " + toDate + "\n\n" +
                        "Thank you for choosing our service!\n\n" +
                        "Best Regards,\nCar Rental Team");

        mailSender.send(message);
    }
    
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
