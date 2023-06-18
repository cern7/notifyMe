package com.notifyme.application.service;

import com.notifyme.application.dto.ServiceRequest;
import com.notifyme.application.model.Employee;
import com.notifyme.application.model.Service;
import com.notifyme.application.repository.EmployeeRepository;
import com.notifyme.application.repository.ServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;

    public ServiceService(ServiceRepository serviceRepository,
                          EmployeeRepository employeeRepository) {
        this.serviceRepository = serviceRepository;
        this.employeeRepository = employeeRepository;
    }

    public Optional<List<Service>> getAllAvailableServices() {
        return serviceRepository.getAllByAvailability(true);
    }

    @Transactional
    public ResponseEntity<?> addNewService(ServiceRequest serviceRequest) {
        // TODO: add more sanity check

        Service newService = new Service(null, serviceRequest.getServiceName(),
                serviceRequest.getDescription(),
                serviceRequest.getPrice(),
                serviceRequest.getDuration(),
                serviceRequest.isAvailability(),
                serviceRequest.getCategory(),
                serviceRequest.getImageUrl());

        serviceRepository.save(newService);

        if (serviceRequest.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(serviceRequest.getEmployeeId()).orElse(null);
            if (employee != null) {
                employee.setProvidedServices(Set.of(newService));
                employeeRepository.save(employee);
            }
        }

        return ResponseEntity.ok("New Service added successfully");
    }
}
