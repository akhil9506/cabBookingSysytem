package com.akhil.cabBookingSystem.controller;

import com.akhil.cabBookingSystem.entity.Customer;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.exception.RoleMisMatchException;
import com.akhil.cabBookingSystem.exception.UserNotFoundException;
import com.akhil.cabBookingSystem.service.CustomerService;
import com.akhil.cabBookingSystem.service.UserService;
import com.akhil.cabBookingSystem.util.JwtUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static sun.net.InetAddressCachePolicy.get;

@RestController
public class CustomerContoller {

    private static final Logger logger= LoggerFactory.getLogger(CustomerContoller.class);

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/updateLocation")
    public Customer updateCustomerLocation(@RequestBody Map<String,Object> payload) throws Exception{
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        System.out.println("dad");

        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new ReflectiveOperationException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        logger.info(String.valueOf(customerId));
        return customerService.updateCustomerLocation(customerId,(int)payload.get("lat"),(int)payload.get("long") );
    }

    @PostMapping("/calculateFare")
    public double calculateFare(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        return customerService.calculateFare(customerId,(int)payload.get("destinationLat"),(int)payload.get("destinationLong"));

    }

    @PostMapping("/bookRide")
    public long bookRide(@RequestBody Map<String,Object> payload)throws Exception{
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        return customerService.bookRide(customerId,(int)payload.get("destinationLat"),(int)payload.get("destinationLong"));
    }

    @PostMapping("/updateDestination")
    public long updateDestinationRide(@RequestBody Map<String,Object> payload)throws Exception{
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        return customerService.updateDestinationRide(customerId,(int)payload.get("destinationLat"),(int)payload.get("destinationLong"));
    }

    @PostMapping("/cancelRide")
    public void cancelRide(@RequestBody Map<String,Object> payload)throws Exception{
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  customerId = user.getRoleId();
        System.out.println("sdada");
        customerService.cancelRide(customerId);
    }
}
