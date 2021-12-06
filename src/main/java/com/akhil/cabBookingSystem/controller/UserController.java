package com.akhil.cabBookingSystem.controller;

import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.service.RideService;
import com.akhil.cabBookingSystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RideService rideService;

    @GetMapping("/users")
    public List<User> home(){
         return userService.fetchUserList();
    }
    @GetMapping("/riders")
    public List<Ride> ride(){
        return rideService.fetchRideList();
    }

    @GetMapping("/driver")
    public String admin(){
        return "This is Admin Page";
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user){
         return userService.saveUser(user);
    }

    @PostMapping("/rides")
    public Ride saveRide(@Valid @RequestBody  Ride ride){
        LOGGER .info("Creating new ride");
        return rideService.saveRide(ride);
    }
}
