package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceImpl implements RideService {

    @Autowired
    private RideRepository rideRepository;

    @Override
    public List<Ride> fetchRideList() {
        return rideRepository.findAll();
    }

    public Ride saveRide(Ride ride) {
        return rideRepository.save(ride);
    }
}
