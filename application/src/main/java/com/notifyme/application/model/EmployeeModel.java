package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "employees", itemRelation = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeModel extends RepresentationModel<EmployeeModel> {

    private Long IID;
    private String department;
    private String jobTitle;
    private String salary;


    @JsonUnwrapped
    private UserModel user;

    private Set<BookingModel> bookings;
    private Set<ServiceModel> providedServices;
}
