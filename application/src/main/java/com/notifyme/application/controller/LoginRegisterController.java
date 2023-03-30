package com.notifyme.application.controller;

import com.notifyme.application.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "api/")
public class LoginRegisterController {

    @GetMapping(value = "register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "form/register")
    public ResponseEntity<Map<String, String>> getRegister(@RequestBody User user) {
        String userEmail = user.getEmailAddress();
        return ResponseEntity.ok(Collections.singletonMap("email", userEmail));
    }

}
