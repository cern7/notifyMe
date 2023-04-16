package com.notifyme.application.service;

import com.notifyme.application.model.User;
import com.notifyme.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user){
        userRepository.save(user);
    }

    /*
    1. Logic for creating Reset Password Token
    2. Logic for finding the email in DB
     */
}
