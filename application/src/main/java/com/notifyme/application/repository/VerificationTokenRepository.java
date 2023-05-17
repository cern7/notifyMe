package com.notifyme.application.repository;

import com.notifyme.application.model.VerificationToken;
import org.hibernate.id.uuid.CustomVersionOneStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    public VerificationToken findByToken(String verificationToken);

}
