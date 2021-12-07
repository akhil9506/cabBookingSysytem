package com.akhil.cabBookingSystem.repository;

import com.akhil.cabBookingSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
