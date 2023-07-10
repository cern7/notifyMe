package com.notifyme.application.repository;

import com.notifyme.application.model.User;
import com.notifyme.application.model.VerificationToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    public VerificationToken findByToken(String verificationToken);
    @Query(value = "select token from verificationtoken where user_id = ?1", nativeQuery = true)
    public Optional<String> findByUserId(Long userId);

}
