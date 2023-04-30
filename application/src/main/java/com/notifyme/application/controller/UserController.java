package com.notifyme.application.controller;

import com.notifyme.application.model.CustomerModel;
import com.notifyme.application.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/customers")
    public ResponseEntity<CollectionModel<CustomerModel>> getAllCustomers() {
        CollectionModel<CustomerModel> customerEntities = userService.getAllCustomers();

        return new ResponseEntity<>(customerEntities, HttpStatus.OK);
    }

    @GetMapping(path = "/customer/{iid}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable("iid") Long iid) {
        return userService.getCustomerById(iid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
