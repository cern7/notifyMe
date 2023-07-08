package com.notifyme.application.events.reminder;

import com.notifyme.application.dto.ReminderDTO;
import com.notifyme.application.events.GenericEvent;
import com.notifyme.application.events.registration.email.EmailSender;
import com.notifyme.application.service.BookingService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.util.Locale;

@Component
@Async
public class ReminderListener implements
        ApplicationListener<GenericEvent<ReminderDTO>> {

    private final BookingService bookingService;
    private final EmailSender mailSender;
    private final MessageSource messages;

    public ReminderListener(BookingService bookingService,
                            EmailSender mailSender,
                            MessageSource messages) {
        this.bookingService = bookingService;
        this.mailSender = mailSender;
        this.messages = messages;
    }

    @Override
    public void onApplicationEvent(@NotNull final GenericEvent<ReminderDTO> reminderEvent) {
        this.sendReminder(reminderEvent);
    }

    public void sendReminder(final GenericEvent<ReminderDTO> reminderEvent) {
        bookingService.updateBookingStatus(
                reminderEvent.getType().getBookingId(),
                true);
        final SimpleMailMessage email = constructEmailMessage(reminderEvent);
        mailSender.send(email);
    }

    private final SimpleMailMessage constructEmailMessage(
            final GenericEvent<ReminderDTO> reminderEvent) {
        final String recipientAddress = reminderEvent.getType().getCustomerEmail();
        final String subject = "Booking Reminder";
        final String message = messages.getMessage("message.Reminder", null,
                "Hey there, " + reminderEvent.getType().getCustomerName() + "! Just a quick reminder " +
                        "that you are scheduled for a visit to [BUSINESS NAME] on" +
                        reminderEvent.getType().getBookingDate() + ". If you have any questions or " +
                        "you need to reschedule, don't hesitate to call us at +40-755-570-090.)",
                Locale.ROOT);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("office@motifyme.com");

        return email;
    }
}
