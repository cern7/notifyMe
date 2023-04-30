package com.notifyme.application.service;

import com.notifyme.application.model.User;

import java.util.List;


public interface UserService {

    List<User> findAll();

    void saveAll(Iterable<User> entities);

    void insertAll(Iterable<User> entities);
}
