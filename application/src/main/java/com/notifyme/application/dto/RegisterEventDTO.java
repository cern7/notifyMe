package com.notifyme.application.dto;

import com.notifyme.application.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@AllArgsConstructor
@Getter
public class RegisterEventDTO {
    private final User user;
    private final Locale locale;
    private final String appUrl;


}
