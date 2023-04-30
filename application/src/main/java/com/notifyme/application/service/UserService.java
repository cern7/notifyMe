package com.notifyme.application.service;

import com.notifyme.application.assembler.CustomerModelAssembler;
import com.notifyme.application.entity.Customer;
import com.notifyme.application.entity.User;
import com.notifyme.application.model.CustomerModel;
import com.notifyme.application.repository.CustomerRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final CustomerRepository customerRepository;

    private CustomerModelAssembler customerModelAssembler;


    public UserService(CustomerRepository customerRepository,
                       CustomerModelAssembler customerModelAssembler) {
        this.customerRepository = customerRepository;
        this.customerModelAssembler = customerModelAssembler;
    }

    public CollectionModel<CustomerModel> getAllCustomers() {
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();

        return customerModelAssembler.toCollectionModel(customerList);
    }

    public Optional<CustomerModel> getCustomerById(Long iid) {
        return customerRepository.getCustomerByIID(iid)
                .map(customer -> customerModelAssembler.toModel(customer));
    }

}
