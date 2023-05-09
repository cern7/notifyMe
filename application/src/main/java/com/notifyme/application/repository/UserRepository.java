package com.notifyme.application.repository;

import com.notifyme.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAddress(String email);

    User getUserByEmailAddress(String email);

    Optional<User> findByEmailAddress(String email);
}
