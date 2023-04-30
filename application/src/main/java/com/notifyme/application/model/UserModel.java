package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "users", itemRelation = "user")
@JsonInclude(Include.NON_NULL)
public class UserModel extends RepresentationModel<UserModel> {

    private Long IID;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String geographicAddress;
    private String createDateTime;
    private String lastLoginDateTime;
    private AdminModel adminModel;
    private CustomerModel customerModel;
    private EmployeeModel employeeModel;

}
