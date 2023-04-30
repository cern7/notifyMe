package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.notifyme.application.entity.Employee;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "services", itemRelation = "service")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceModel extends RepresentationModel<ServiceModel> {

    private Long IID;
    private String name;
    private String description;
    private String price;
    private String duration;
    private boolean availability;
    private String category;
    private String imageUrl;

    private Set<BookingModel> bookings;
    private Set<EmployeeModel> employeesForService;
}
