package com.notifyme.application.events.listener.reminder;

import com.notifyme.application.dto.EmailDetails;
import com.notifyme.application.dto.ReminderDTO;
import com.notifyme.application.events.GenericEvent;
import com.notifyme.application.service.email.EmailSender;
import com.notifyme.application.service.BookingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

// https://stackoverflow.com/questions/71452445/eventlistener-for-generic-events-with-spring
@Component
public class GenericReminderListener {
    private final BookingService bookingService;
    @Qualifier("MailtrapService")
    private final EmailSender mailSender;


    public GenericReminderListener(BookingService bookingService,
                                   EmailSender mailSender) {
        this.bookingService = bookingService;
        this.mailSender = mailSender;
    }


    @EventListener
    public void handle(GenericEvent<ReminderDTO> event) {
        this.sendReminder(event);
    }

    public void sendReminder(final GenericEvent<ReminderDTO> event) {
        bookingService.updateBookingStatus(
                event.getType().getBookingId(), true);
        final EmailDetails email = constructEmailMessage(event);
        mailSender.send(email);
    }

    private EmailDetails constructEmailMessage(
            final GenericEvent<ReminderDTO> reminderEvent) {
        final String recipientAddress = reminderEvent.getType().getCustomerEmail();
        final String subject = "Booking Reminder";
        final String message = String.format("Hey there, %s! Just a quick reminder that " +
                        "you are scheduled for a visit to [BUSINESS NAME] on %s." +
                        " If you have any questions or you need to reschedule, " +
                        "don't hesitate to call us at +40-755-xxx-xxx.",
                reminderEvent.getType().getCustomerName(),
                reminderEvent.getType().getBookingDate());

        final EmailDetails email = new EmailDetails();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setCategory("Reminder");
        return email;
    }
}
