package com.akhil.cabBookingSystem.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @NotNull
    private Long userId;
    @NotNull
    @Positive(message ="Please Provide Location")
    private Long latitude;
   @NotNull
    @Positive(message ="Please Provide Location")
    private Long longitude;

    public Ride() {
    }

    public Ride(Long id, Long userId, Long latitude, Long longitude) {
        Id = id;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }
}
