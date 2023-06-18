package com.notifyme.application.repository;

import com.notifyme.application.model.Booking;
import com.notifyme.application.model.Customer;
import com.notifyme.application.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.awt.print.Book;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByCustomerAndService(Customer customer, Service service);

}
