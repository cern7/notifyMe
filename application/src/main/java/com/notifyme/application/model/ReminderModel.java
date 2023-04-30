package com.notifyme.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.awt.print.Book;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "reminders", itemRelation = "reminder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReminderModel extends RepresentationModel<ReminderModel> {

    private Long IID;
    private String content;
    private String status;
    private String firstSentDate;
    private String lastSentDate;
    private String type;

    private BookingModel booking;
}
