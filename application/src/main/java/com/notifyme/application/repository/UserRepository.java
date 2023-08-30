package com.notifyme.application.repository;

import com.notifyme.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByEmailAddress(String email);

    User getUserByEmailAddress(String email);

    Optional<User> findByEmailAddress(String email);

    @Modifying
    @Query(value = "UPDATE public.usertbl SET lastlogindatetime = ?2" +
            " where iid = ?1", nativeQuery = true)
    void updateLoginTime(Long userId, String loginTime);
}
