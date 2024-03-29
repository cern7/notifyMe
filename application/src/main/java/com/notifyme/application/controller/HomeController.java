package com.notifyme.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to home page");
        return "home";

    }

    // dashboard or user profile page ("/dashboard" "/profile")
}
