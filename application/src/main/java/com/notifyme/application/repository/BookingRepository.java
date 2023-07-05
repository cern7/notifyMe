package com.notifyme.application.repository;

import com.notifyme.application.model.Booking;
import com.notifyme.application.model.Customer;
import com.notifyme.application.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByCustomerAndService(Customer customer, Service service);

    List<Booking> getAllByCustomer(Customer customer);

    List<Booking> getAllByStartDateTimeAfter(String today);

    @Query(value = "select * from booking b where b.startdatetime >= ?1 and b.enddatetime <= ?2",
            nativeQuery = true)
    Optional<List<Booking>> getAllBetweenStartAndEndDate(String startDate, String endDate);
}
