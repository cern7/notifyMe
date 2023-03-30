package com.notifyme.application.controller;

import com.notifyme.application.model.Customer;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value="/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to home page");
        return "home";

    }
}
