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
@Relation(collectionRelation = "customers", itemRelation = "customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerModel extends RepresentationModel<CustomerModel> {

    private Long IID;
    private String paymentID;
    private String invoice;

    @JsonUnwrapped
    private UserModel user;

    private Set<BookingModel> bookings;
}
