package com.notifyme.application.repository;

import com.notifyme.application.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<List<Service>> getAllByAvailability(boolean availability);
}
