package com.akhil.cabBookingSystem.repository;

import com.akhil.cabBookingSystem.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
}
