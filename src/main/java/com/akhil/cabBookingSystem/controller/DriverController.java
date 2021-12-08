package com.akhil.cabBookingSystem.controller;

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

    @PostMapping("/getRides")
    public Ride getRide(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  driverId = user.getRoleId();
        return driverService.getRide(driverId);
    }

    @PostMapping("/confirmRide")
    public Ride confirmRide(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  driverId = user.getRoleId();
        Long rideID= new Long((String) payload.get("rideId"));
//        long rideID= (long) payload.get("rideId");
        return driverService.confirmRide(driverId,rideID);
    }

    @PostMapping("/cancelbestRide")
    public void cancelRide(@RequestBody Map<String,Object> payload) throws Exception {
        String username = jwtTokenUtil.extractUsername((String) payload.get("jwt"));
        User user =  userService.fetchByUsername(username);
        if("customer".equalsIgnoreCase(user.getRole())){
            throw new RoleMisMatchException("Not Valid Operation");
        }
        long  driverId = user.getRoleId();
        Long rideID= new Long((String) payload.get("rideId"));
        driverService.cancelRide(driverId,rideID);
    }
}
