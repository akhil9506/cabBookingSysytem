package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.exception.RideNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RideService {

    List<Ride> fetchRideList();
    Ride fetchRideById(long Id) throws RideNotFoundException;
    Ride saveRide(Ride ride);

    void deleteRideById(long rideId);
}
