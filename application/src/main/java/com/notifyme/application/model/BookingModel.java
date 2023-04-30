package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.notifyme.application.entity.Customer;
import com.notifyme.application.entity.Employee;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "bookings", itemRelation = "booking")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingModel extends RepresentationModel<BookingModel> {

    private Long IID;
    private String startDateTime;
    private String endDateTime;
    private String status;
    private String paymentStatus;
    private String notes;

    private CustomerModel customerModel;
    private EmployeeModel employeeModel;
    private ServiceModel serviceModel;
    private ReminderModel reminderModel;
}
