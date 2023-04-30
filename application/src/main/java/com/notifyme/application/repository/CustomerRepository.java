package com.notifyme.application.repository;

import com.notifyme.application.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    public Optional<Customer> getCustomerByIID(Long IID);

}
