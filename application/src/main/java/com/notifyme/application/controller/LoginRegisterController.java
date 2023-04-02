package com.notifyme.application.controller;

import com.notifyme.application.model.Customer;
import com.notifyme.application.model.User;
import com.notifyme.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "new-register")
    public String registrationForm(Model model) {

        return "register";
    }

    @PostMapping(value = "register")
//    public String processRegistration(@RequestBody Customer customer) {
    public String processRegistration(@ModelAttribute("customer") Customer customer, BindingResult result) {
//        if (result.hasErrors()) {
//            return "register";
//        }

//        userService.saveUser(user);

        return "redirect:/register/success_";
    }

    @GetMapping(value = "register/success")
    public String successRegistration() {
        return "registration_success";
    }

}
