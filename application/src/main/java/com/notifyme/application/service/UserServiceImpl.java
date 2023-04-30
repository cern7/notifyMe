package com.notifyme.application.service;

import com.notifyme.application.model.User;
import com.notifyme.application.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveAll(Iterable<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    @Transactional
    public void insertAll(Iterable<User> users) {
        userRepository.insertAll(users);
    }
}
