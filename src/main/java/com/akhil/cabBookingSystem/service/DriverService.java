package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Driver;
import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.exception.RideNotFoundException;
import com.akhil.cabBookingSystem.exception.UserNotFoundException;

public interface DriverService {

    Ride getRide(long driverId) throws Exception;
    Driver fetchDriverById(long driverId)throws Exception;
    Ride confirmRide(long driverId,long riderId) throws Exception;
    void cancelRide(long driverId,long riderId) throws Exception;
}
