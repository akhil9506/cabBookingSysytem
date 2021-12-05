package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RideService {

    List<Ride> fetchRideList();
    Ride saveRide(Ride ride);
}
