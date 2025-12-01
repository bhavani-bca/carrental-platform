package com.example.carrental.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String model;
    private String fuelType;
    
    @Column(nullable = false)
    private Long rent;  // This represents rent per day

    private Boolean isAvailable;
    private String noPlate;
    private Integer seatingCapacity;
    private Float mileage;
    private String imgUrl;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // 🚀 Prevents infinite loop	
    private List<Reviews> reviews; // ✅ Add this relationship

    
    @Column(updatable = false)
    private Instant createdAt = Instant.now();

    private Instant modifiedAt = Instant.now();

    // Getters
    public Long getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getModel() {
        return model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public Long getRent() {
        return rent;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public String getNoPlate() {
        return noPlate;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public Float getMileage() {
        return mileage;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setRent(Long rent) {
        this.rent = rent;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setNoPlate(String noPlate) {
        this.noPlate = noPlate;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public void setMileage(Float mileage) {
        this.mileage = mileage;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    
    public List<Reviews> getReviews() { return reviews; }
    public void setReviews(List<Reviews> reviews) { this.reviews = reviews; }
    
    // ✅ Corrected version using "isAvailable"
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    

}
