package com.notifyme.application.repository;

import com.notifyme.application.entity.Booking;
import com.notifyme.application.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {

    public Optional<Booking> getBookingByIID(Long iid);
}
