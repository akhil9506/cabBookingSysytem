package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Customer;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.exception.RideNotFoundException;
import com.akhil.cabBookingSystem.exception.UserNotFoundException;

import java.util.Map;

public interface CustomerService {
    Customer updateCustomerLocation(long customerId, long lat, long aLong) throws UserNotFoundException;

    Customer fetchUserById(long customerId) throws UserNotFoundException;

    double calculateFare(long customerId, int destinationLat,int destinationLong) throws UserNotFoundException;

    long bookRide(long customerId, int destinationLat, int destinationLong) throws UserNotFoundException;

    long updateDestinationRide(long customerId, int destinationLat, int destinationLong) throws UserNotFoundException, Exception;

    void cancelRide(long customerId) throws UserNotFoundException;

    Map<String, Object> getRideDetails(long customerId) throws RideNotFoundException, Exception;
    Customer saveCustomer(Customer customer);
}
