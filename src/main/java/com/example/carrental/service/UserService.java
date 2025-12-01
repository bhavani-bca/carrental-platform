package com.example.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.carrental.model.Users;
import com.example.carrental.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(Users user) {
        userRepository.save(user);
    }

}
