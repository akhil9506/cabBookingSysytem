package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.exception.RideNotFoundException;
import com.akhil.cabBookingSystem.exception.UserNotFoundException;
import com.akhil.cabBookingSystem.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RideServiceImpl implements RideService {

    @Autowired
    private RideRepository rideRepository;

    @Override
    public List<Ride> fetchRideList() {
        return rideRepository.findAll();
    }

    public Ride fetchRideById(long Id) throws RideNotFoundException {
        Optional<Ride> ride=rideRepository.findById(Id);
        if(!ride.isPresent()){
            throw new RideNotFoundException("User Not Available");
        }
        return ride.get();
    }
    public Ride saveRide(Ride ride) {
        return rideRepository.save(ride);
    }

    @Override
    public void deleteRideById(long rideId) {
        rideRepository.deleteById(rideId);
    }
}
