package com.akhil.cabBookingSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/home")
    public String home(){
        return "This is Home Page";
    }
    @GetMapping("/driver")
    public String admin(){
        return "This is Admin Page";
    }
}
