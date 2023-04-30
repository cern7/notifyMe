package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "admins", itemRelation = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminModel extends RepresentationModel<AdminModel> {

    private Long IID;
    private String role;
    private List<String> permissions;
    @JsonUnwrapped
    private UserModel user;

}
