package com.akhil.cabBookingSystem.controller;

import com.akhil.cabBookingSystem.entity.AuthenticationRequest;
import com.akhil.cabBookingSystem.entity.AuthenticationResponse;
import com.akhil.cabBookingSystem.entity.Ride;
import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.service.MyUserDetailsService;
import com.akhil.cabBookingSystem.service.RideService;
import com.akhil.cabBookingSystem.service.UserService;
import com.akhil.cabBookingSystem.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RideService rideService;


    @Autowired
    private AuthenticationManager authenticationManager;
//
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @RequestMapping({"/hello"})
    public String hello(){
        return "Hello";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping({"/users"})
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
