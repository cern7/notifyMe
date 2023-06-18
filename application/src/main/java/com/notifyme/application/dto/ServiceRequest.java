package com.notifyme.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequest {
    private boolean availability;
    private String category;
    private String description;
    private String duration;
    private String imageUrl;
    private String serviceName;
    private String price;
    private Long employeeId;
}
