package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface UserService {
    User saveUser(User user);
    List<User> fetchUserList();
    String getPassword(String username);

}
