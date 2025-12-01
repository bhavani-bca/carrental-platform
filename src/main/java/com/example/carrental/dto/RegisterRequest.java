package com.example.carrental.dto;

public class RegisterRequest {
    private String firstName, lastName, email, address, contact, password, licence, licenceImg;
    
    
    private String otp; // Add this field
    
    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getLicence() { return licence; }
    public void setLicence(String licence) { this.licence = licence; }
    public String getLicenceImg() { return licenceImg; }
    public void setLicenceImg(String licenceImg) { this.licenceImg = licenceImg; }
    
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
}