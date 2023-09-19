package com.notifyme.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDetails {
    private String to;
    private String subject;
    private String text;
    private String category;
}
