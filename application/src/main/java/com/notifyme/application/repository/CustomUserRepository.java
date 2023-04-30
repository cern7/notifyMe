package com.notifyme.application.repository;

import com.notifyme.application.model.User;

public interface CustomUserRepository {
    void insertAll(Iterable<User> users);
}
