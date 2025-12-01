package com.example.carrental.dto;

public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String contact;
    private String licence;
    private String licenceImg;

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getLicence() { return licence; }
    public void setLicence(String licence) { this.licence = licence; }

    public String getLicenceImg() { return licenceImg; }
    public void setLicenceImg(String licenceImg) { this.licenceImg = licenceImg; }
}
