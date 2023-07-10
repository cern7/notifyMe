package com.notifyme.application.repository;

import com.notifyme.application.model.Booking;
import com.notifyme.application.model.Customer;
import com.notifyme.application.model.Employee;
import com.notifyme.application.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByCustomerAndService(Customer customer, Service service);

    List<Booking> getAllByCustomer(Customer customer);

    List<Booking> getAllByEmployee(Employee employee);

    List<Booking> getAllByStartDateTimeAfter(Long today);

    @Query(value = "select * from booking b where b.startdatetime > ?1 and b.enddatetime <= ?2 and notified = false",
            nativeQuery = true)
    Optional<List<Booking>> getAllBetweenStartAndEndDate(Long startDate, Long endDate);

    // inform Spring Data JPA this is an update operation
    // and should return number of affected rows
    @Modifying
    @Query(value = "update booking set notified = ?2 where iid = ?1", nativeQuery = true)
    void updateNotifiedByBookingIID(Long bookingId, boolean isNotified);
}
