package com.notifyme.application.events.listener.registration;

import com.notifyme.application.dto.RegisterEventDTO;
import com.notifyme.application.events.GenericEvent;

import com.notifyme.application.service.email.EmailSender;
import com.notifyme.application.model.User;
import com.notifyme.application.service.RegisterAuthenticationService;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class GenericRegistrationListener {
    private final RegisterAuthenticationService registerService;

    private final MessageSource messages;

    private final EmailSender mailSender;

    public GenericRegistrationListener(RegisterAuthenticationService registerService,
                                       MessageSource messages,
                                       EmailSender mailSender) {
        this.registerService = registerService;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @EventListener
    public void handle(final GenericEvent<RegisterEventDTO> event) {
        this.confirmRegistration(event);
    }


    synchronized public void confirmRegistration(final GenericEvent<RegisterEventDTO> event) {
        final User user = event.getType().getUser();
        final String token = UUID.randomUUID().toString();
        registerService.createVerificationTokenForUser(user, token);
        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(final GenericEvent<RegisterEventDTO> event,
                                                    final User user,
                                                    final String token) {
        final String recipientAddress = user.getEmailAddress();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = "http://localhost:5173/register/confirmEmail/" + token;
        final String message = messages.getMessage("message.regSuccLink",
                null,
                "You registered successfully. To confirm your " +
                        "registration, please click on the below link.",
                event.getType().getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("office@notifyme.com");
        return email;
    }
}
