package com.notifyme.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    private String email;
    private String confirmEmail;
    private String firstName;
    private String lastName;
    private String geographicAddress;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String type;


}
