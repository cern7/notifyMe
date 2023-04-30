package com.notifyme.application.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reminder")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IID;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String content;
    private String status;
    private String firstSentDate;
    private String lastSentDate;
    private String type;

    public Reminder(Long IID, Booking booking, String content, String status, String firstSentDate, String lastSentDate, String type) {
        this.IID = IID;
        this.booking = booking;
        this.content = content;
        this.status = status;
        this.firstSentDate = firstSentDate;
        this.lastSentDate = lastSentDate;
        this.type = type;
    }

    public Reminder() {

    }

    public Long getIID() {
        return IID;
    }

    public void setIID(Long IID) {
        this.IID = IID;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstSentDate() {
        return firstSentDate;
    }

    public void setFirstSentDate(String firstSentDate) {
        this.firstSentDate = firstSentDate;
    }

    public String getLastSentDate() {
        return lastSentDate;
    }

    public void setLastSentDate(String lastSentDate) {
        this.lastSentDate = lastSentDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reminder reminder = (Reminder) o;
        return Objects.equals(IID, reminder.IID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IID);
    }
}
