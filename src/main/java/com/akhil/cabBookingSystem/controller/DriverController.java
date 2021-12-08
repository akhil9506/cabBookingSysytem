package com.akhil.cabBookingSystem.controller;

import com.akhil.cabBookingSystem.entity.Driver;
import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.exception.RoleMisMatchException;
import com.akhil.cabBookingSystem.service.DriverService;
import com.akhil.cabBookingSystem.service.RideService;
import com.akhil.cabBookingSystem.service.UserService;
import com.akhil.cabBookingSystem.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DriverController {

    private static final Logger logger= LoggerFactory.getLogger(RestController.class);

    @Autowired
    private DriverService driverService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/driver/getRides")
    public Ride getRide(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  driverId = user.getRoleId();
        logger.info("Getting Best ride for Driver");
        return driverService.getRide(driverId);
    }

    @PostMapping("/driver/confirmRide")
    public Ride confirmRide(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  driverId = user.getRoleId();
        Long rideID= new Long((String) payload.get("rideId"));
        logger.info("Confirming Ride");
        return driverService.confirmRide(driverId,rideID);
    }

    @PostMapping("/driver/cancelbestRide")
    public void cancelRide(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  driverId = user.getRoleId();
        Long rideID= new Long((String) payload.get("rideId"));
        driverService.cancelRide(driverId,rideID);
        logger.info("Cancelling Ride");
    }
    @PostMapping("/driver/signUp")
    public User signIn(@RequestBody Map<String,Object> payload){
        Driver driver = new Driver();
        driver.setPhoneNUmber((String) payload.get("phoneNumber"));
        driver.setRating((Double) payload.get("rating"));
        long latitude =new Long((String)payload.get("latitude"));
        driver.setLatitude(latitude);
        long longitude =new Long((String) payload.get("longitude"));
        driver.setLongitude(longitude);
        long customerId = driverService.saveDriver(driver).getId();
        User user = new User();
        user.setUsername((String) payload.get("username"));
        user.setPassword((String) payload.get("password"));
        user.setRole("user");
        user.setRoleId(customerId);
        logger.info("new User signIn");
        return  userService.saveUser(user);
    }
}
