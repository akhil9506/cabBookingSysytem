package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.Customer;
import com.akhil.cabBookingSystem.entity.Driver;
import com.akhil.cabBookingSystem.entity.Ride;

import com.akhil.cabBookingSystem.exception.RideNotFoundException;
import com.akhil.cabBookingSystem.exception.UserNotFoundException;
import com.akhil.cabBookingSystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService{

    private final int BASEFARE =5;

    private final  double CGST=2.5;
    private final  double SGST =2.5;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RideService rideService;

    public Customer updateCustomerLocation(long customerId, long latitude, long longitude) throws UserNotFoundException {
        Customer customer = this.fetchUserById(customerId);
        customer.setLatitude(latitude);
        customer.setLongitude(longitude);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer fetchUserById(long customerId) throws UserNotFoundException {
        System.out.println(customerId);
        Optional<Customer> user=customerRepository.findById(customerId);
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Available");
        }
        return user.get();
    }

    @Override
    public double calculateFare(long customerId, int destinationLat,int destinationLong) throws UserNotFoundException {
        Optional<Customer> user=customerRepository.findById(customerId);
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Available");
        }
        double distance = Math.pow(Math.abs(destinationLat-user.get().getLatitude()),2)+Math.pow(Math.abs(destinationLong-user.get().getLatitude()),2);
        double fare = distance*BASEFARE;
        fare += (fare *(CGST+SGST))/100;
        return fare;
    }

    @Override
    public long bookRide(long customerId, int destinationLat, int destinationLong) throws UserNotFoundException {
        Optional<Customer> user=customerRepository.findById(customerId);
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Available");
        }
        Ride ride = new Ride();
        ride.setUserId(customerId);
        ride.setLatitude((long) destinationLat);
        ride.setLongitude((long)destinationLong);
        long rideId= rideService.saveRide(ride).getId();
        user.get().setRideId(rideId);
        customerRepository.save(user.get());
        return rideId;
    }

    @Override
    public long updateDestinationRide(long customerId, int destinationLat, int destinationLong) throws Exception {
        Optional<Customer> user=customerRepository.findById(customerId);
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Available");
        }
        long rideId=user.get().getRideId();
        Ride ride = rideService.fetchRideById(rideId);
        ride.setLatitude((long) destinationLat);
        ride.setLongitude((long) destinationLong);
        rideService.saveRide(ride);
        return ride.getId();
    }

    @Override
    public void cancelRide(long customerId) throws UserNotFoundException {
        Optional<Customer> user=customerRepository.findById(customerId);
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Available");
        }
        long rideId=user.get().getRideId();
        rideService.deleteRideById(rideId);
    }

    @Override
    public Map<String, Object> getRideDetails(long customerId) throws Exception {
        Optional<Customer> user=customerRepository.findById(customerId);
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Available");
        }
        long rideId=user.get().getRideId();
        Ride ride =rideService.fetchRideById(rideId);
        if(ride.getDriverId()==null){
            throw new UserNotFoundException();
        }
        Driver driver = driverService.fetchDriverById(ride.getDriverId());
        HashMap<String, Object>details = new HashMap<String, Object>();
        details.put("driverName",driver.getDriverName());
        details.put("driverRating",driver.getRating());
        details.put("driver Mobile NUmber",driver.getPhoneNUmber());
        details.put("Detination location latitude",ride.getLatitude());
        details.put("Destination location longitude",ride.getLongitude());
        return details;
    }
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
