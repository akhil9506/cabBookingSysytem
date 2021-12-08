package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Driver;
import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.exception.RideNotFoundException;
import com.akhil.cabBookingSystem.exception.UserNotFoundException;
import com.akhil.cabBookingSystem.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private RideService rideService;

    @Autowired
    private DriverRepository driverRepository;

    public Driver fetchDriverById(long driverId)throws UserNotFoundException {
        List<Driver>l=driverRepository.findAll();
        Optional<Driver> driver = driverRepository.findById(driverId);
        if(!driver.isPresent()){
            throw new UserNotFoundException("User Not Available");
        }
        return driver.get();
    }

    @Override
    public Ride getRide(long driverId) throws Exception {
        List<Ride> rides = rideService.fetchRideList();
        if(rides.size()==0){
            throw new RideNotFoundException();
        }
        long latitude = this.fetchDriverById(driverId).getLatitude();
        long longitude =this.fetchDriverById(driverId).getLongitude();
        double distance = Integer.MAX_VALUE;
        Ride nearestRide=new Ride();
        for(Ride ride:rides){
            double dist=Math.pow(Math.abs(latitude-ride.getLatitude()),2)+Math.pow(Math.abs(longitude-ride.getLongitude()),2);
            if(dist<distance){
                nearestRide=ride;
                distance=dist;
            }
        }
        return nearestRide;
    }

    @Override
    public Ride confirmRide(long driverId,long riderId) throws Exception {
        Ride ride = rideService.fetchRideById(riderId);
        ride.setDriverId(driverId);
        rideService.saveRide(ride);
        return ride;
    }
    public void cancelRide(long driverId,long riderId) throws Exception {
        Ride ride = rideService.fetchRideById(riderId);
        ride.setDriverId(null);
        rideService.saveRide(ride);
    }

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
