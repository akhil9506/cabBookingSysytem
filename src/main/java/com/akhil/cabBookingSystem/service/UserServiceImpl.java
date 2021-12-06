package com.akhil.cabBookingSystem.service;

import com.akhil.cabBookingSystem.entity.User;
import com.akhil.cabBookingSystem.exception.UserNotFoundException;
import com.akhil.cabBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> fetchUserList() {
        return userRepository.findAll();
    }

    public User fetchUserById(Long userId) throws UserNotFoundException {
        Optional<User>user=userRepository.findById(userId);
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Available");
        }
        return user.get();
    }
}