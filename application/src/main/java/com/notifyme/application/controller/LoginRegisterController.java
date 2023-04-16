package com.notifyme.application.controller;

import com.notifyme.application.model.User;
import com.notifyme.application.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//https://projects.wojtekmaj.pl/react-calendar/
//https://www.npmjs.com/package/react-calendar/
@RestController
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "register")
    public String registerForm(Model model) {

        return "register";
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> registerSubmit(@RequestBody User customer) {
        userService.addUser(customer);

        return ResponseEntity.ok(customer);
    }


    @GetMapping(value = "login")
    public String loginForm(Model model) {

        return "login";
    }

    @PostMapping(value = "login")
    public String loginSubmit(@ModelAttribute User user, Model model, HttpSession session) {
        return "loginSubmit";
    }



    /*
     endpoint for changing the password
     */


    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

//        User user = userService.findByEmail(email);

//        if (user == null) {
//            return ResponseEntity.badRequest().body("No user with this email");
//        }

        // Generate a secure token and send it to the user's email

//        String token = userService.createPasswordResetToken(user);

        // sent the token to the user's email
        ////

        return ResponseEntity.ok("a password reset link was sent to your email");
    }

    @GetMapping("reset-password/{token}")
    public ResponseEntity<?> showPasswordResetForm(@PathVariable String token) {

//        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
//
//        if (passwordResetToken == null) {
//            return ResponseEntity.badRequest().body("Invalid token");
//        }

        // Return the password reset form with the token as a hidden field

//        return ResponseEntity.ok(passwordResetToken);
        return ResponseEntity.ok("dummy");
    }


    @PostMapping("reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestBody Map<String, String> request) {
//        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
//
//        if (passwordResetToken == null) {
//            return ResponseEntity.badRequest().body("Invalid token");
//        }
//
//        String password = request.get("password");
        return ResponseEntity.ok().body("dummy");
    }
}
