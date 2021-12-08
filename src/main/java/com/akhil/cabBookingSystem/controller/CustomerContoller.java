package com.akhil.cabBookingSystem.controller;

import com.akhil.cabBookingSystem.entity.Customer;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.exception.RoleMisMatchException;

import com.akhil.cabBookingSystem.service.CustomerService;
import com.akhil.cabBookingSystem.service.UserService;
import com.akhil.cabBookingSystem.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;



@RestController
public class CustomerContoller {

    private static final Logger logger= LoggerFactory.getLogger(CustomerContoller.class);

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/user/updateLocation")
    public Customer updateCustomerLocation(@RequestBody Map<String,Object> payload) throws Exception{
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        System.out.println("dad");

        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new ReflectiveOperationException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        logger.info("Updating User Location");
        return customerService.updateCustomerLocation(customerId,(int)payload.get("lat"),(int)payload.get("long") );
    }

    @PostMapping("/user/calculateFare")
    public double calculateFare(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        logger.info("calculating Fare");
        return customerService.calculateFare(customerId,(int)payload.get("destinationLat"),(int)payload.get("destinationLong"));

    }

    @PostMapping("/user/bookRide")
    public long bookRide(@RequestBody Map<String,Object> payload)throws Exception{
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        logger.info("new Ride Booking");
        return customerService.bookRide(customerId,(int)payload.get("destinationLat"),(int)payload.get("destinationLong"));
    }

    @PostMapping("/user/updateDestination")
    public long updateDestinationRide(@RequestBody Map<String,Object> payload)throws Exception{
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        logger.info("Updating destination location");
        return customerService.updateDestinationRide(customerId,(int)payload.get("destinationLat"),(int)payload.get("destinationLong"));
    }

    @PostMapping("/user/cancelRide")
    public void cancelRide(@RequestBody Map<String,Object> payload)throws Exception{
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        logger.info("Cancelling Ride");
        customerService.cancelRide(customerId);
    }

    @PostMapping("/user/getRideDetails")
    public Map<String,Object> getRideDetails(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        logger.info("Getting Ride details");
        return customerService.getRideDetails(customerId);
    }

    @PostMapping("/user/customerSignIn")
    public User customerSignIn(@RequestBody Map<String,Object> payload) throws Exception {
        Customer customer = new Customer();
        customer.setPhoneNumber((String) payload.get("phoneNumber"));
        customer.setLastName((String) payload.get("firstName"));
        customer.setFirstName((String) payload.get("lastName"));
        long latitude =new Long((String)payload.get("latitude"));
        customer.setLatitude(latitude);
        long longitude =new Long((String) payload.get("longitude"));
        customer.setLongitude(longitude);
        long customerId = customerService.saveCustomer(customer).getId();
        User user = new User();
        user.setUsername((String) payload.get("username"));
        user.setPassword((String) payload.get("password"));
        user.setRole("user");
        user.setRoleId(customerId);
        logger.info("new Customer Signin");
        return  userService.saveUser(user);
    }

}
