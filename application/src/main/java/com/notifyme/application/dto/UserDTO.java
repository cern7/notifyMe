package com.notifyme.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long IID;
    private String firsName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String type;

}

