🚗 Car Rental Platform
A fully functional Car Rental Platform built using Spring Boot for backend and Thymeleaf for the frontend. The platform allows users to browse cars, book rentals, manage bookings, and perform authentication with Spring Security and 2FA email verification.

✨ Features

🔹 User Features

Create account & login with secure authentication

Email-based Two-Factor Authentication (2FA)

Browse list of available cars

View car details with pricing

Book a car for selected dates

Manage or cancel bookings

🔹 Admin Features

Add / Edit / Delete car details

Manage user bookings

View customer activity

Dashboard for platform management

🛠️ Technology Stack
Backend

Java

Spring Boot

Spring Security (Authentication, 2FA)

Spring Data JPA

H2 (file-based DB) / MySQL (optional, production-ready)

Frontend

HTML

CSS

JavaScript

Bootstrap

Thymeleaf (templating engine)

Email Service

SMTP (Used for 2FA email verification)

System Architecture
[Frontend (HTML/CSS/JS)] --> [Spring Boot Backend (REST APIs)] --> [Database (H2/MySQL)]
                               |
                               --> [SMTP Service for 2FA Email Verification]
MVC Architecture for backend structure
Service layer handles business logic
Controller layer manages client requests
Repository layer handles database operations
Database Structure
Tables:

users – stores user details
vehicles – stores car details, rent per day, availability
bookings – stores booking details (user, vehicle, from_date, to_date)
audit_logs – optional table for tracking system changes
API Endpoints
Endpoint	Method	Description
/api/users	GET	List all users
/api/users	POST	Register a new user
/api/vehicles	GET	List all vehicles
/api/bookings	POST	Create a booking
/api/bookings/{id}	GET	View booking details
All APIs are secured with authentication and 2FA via SMTP.


🛠️ Installation
1️⃣ Clone the Repository
git clone https://github.com/bhavani-bca/carrental-platform

2️⃣ Open the Project
Open the folder in IntelliJ IDEA or Eclipse.
3️⃣ Configure Application Settings

Update the following in src/main/resources/application.properties:

Database Configuration
(H2 default or MySQL credentials)

SMTP Email Settings
(For sending OTP in 2FA)

4️⃣ Build the Project
mvn clean install

5️⃣ Run the Application
mvn spring-boot:run

6️⃣ Open in Browser
http://localhost:8080

🚀 Future Enhancements

Online payment integration

Real-time car availability

SMS OTP support

Advanced admin dashboard

User analytics



