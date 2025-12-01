package com.example.carrental.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName, lastName, email, address, contact, password, licence, licenceImg;
    private Instant createdAt = Instant.now(), modifiedAt = Instant.now();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews; // ✅ Add this relationship

    public Users() {}

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
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public Long getId() {
        return id;
    }

    @JsonIgnore  
    public List<Reviews> getReviews() { return reviews; }
    public void setReviews(List<Reviews> reviews) { this.reviews = reviews; }


}
