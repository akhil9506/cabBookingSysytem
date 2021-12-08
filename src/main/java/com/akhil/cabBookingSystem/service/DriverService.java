package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Driver;
import com.akhil.cabBookingSystem.entity.Ride;

public interface DriverService {

    Ride getRide(long driverId) throws Exception;
    Driver fetchDriverById(long driverId)throws Exception;
    Ride confirmRide(long driverId,long riderId) throws Exception;
    void cancelRide(long driverId,long riderId) throws Exception;

    Driver saveDriver(Driver driver);
}
