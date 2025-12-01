# CarEase - Car Rental Platform
## Project Overview
CarEase is a web-based car rental platform built using Java Spring Boot. It allows users to browse available vehicles, book cars for a specified period, and manage their bookings through an intuitive interface.

The platform provides a seamless and interactive rental experience for users.

## Features
### User Module

Browse available vehicles with search filters
Book cars for desired periods
View booking history and details
Update personal information

### Common Features

Secure authentication and authorization
Two-Factor Authentication (2FA) implemented via SMTP email
Transaction and booking history
Error handling and logs
Interactive dashboards

## Technology Stack

Backend: Java, Spring Boot, Spring Security, Spring Data JPA
Frontend: HTML, CSS, JavaScript, Bootstrap, Thymeleaf
Database: H2 (file-based) / MySQL (optional)
Email Service: SMTP (used for 2FA)
Tools: Maven, GitHub, IntelliJ IDEA / Eclipse

## Database Structure
Tables:

users – stores user details
vehicles – stores car details, rent per day, availability
bookings – stores booking details (user, vehicle, from_date, to_date)
audit_logs – optional table for tracking system changes

## Usage

User: Browse vehicles, book cars, view booking history, update profile, and verify 2FA via email

## Future Enhancements

Integration with payment gateways
Mobile app version
Advanced search and recommendation engine
Multi-location and multi-currency support
Enhanced reporting and analytics
