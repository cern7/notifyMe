package com.notifyme.application.events.listener.reminder;

import com.notifyme.application.dto.ReminderDTO;
import com.notifyme.application.events.GenericEvent;
import com.notifyme.application.service.email.EmailSender;
import com.notifyme.application.service.BookingService;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;
// https://stackoverflow.com/questions/71452445/eventlistener-for-generic-events-with-spring
@Component
public class GenericReminderListener {
    private final BookingService bookingService;
    private final EmailSender mailSender;
    private final MessageSource messages;

    public GenericReminderListener(BookingService bookingService,
                                   EmailSender mailSender,
                                   MessageSource messages) {
        this.bookingService = bookingService;
        this.mailSender = mailSender;
        this.messages = messages;
    }


    @EventListener
    public void handle(GenericEvent<ReminderDTO> event) {
        this.sendReminder(event);
    }

    public void sendReminder(final GenericEvent<ReminderDTO> event) {
        bookingService.updateBookingStatus(
                event.getType().getBookingId(), true);
        final SimpleMailMessage email = constructEmailMessage(event);
        mailSender.send(email);
    }

    private final SimpleMailMessage constructEmailMessage(
            final GenericEvent<ReminderDTO> reminderEvent) {
        final String recipientAddress = reminderEvent.getType().getCustomerEmail();
        final String subject = "Booking Reminder";
        final String message = messages.getMessage("message.Reminder", null,
                "Hey there, " + reminderEvent.getType().getCustomerName() + "! Just a quick reminder " +
                        "that you are scheduled for a visit to [BUSINESS NAME] on " +
                        reminderEvent.getType().getBookingDate() + ". If you have any questions or " +
                        "you need to reschedule, don't hesitate to call us at +40-755-570-090.",
                Locale.ROOT);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("office@motifyme.com");

        return email;
    }
}
