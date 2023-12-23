package com.notifyme.application.events.listener.registration;

import com.notifyme.application.dto.EmailDetails;
import com.notifyme.application.dto.RegisterEventDTO;
import com.notifyme.application.events.GenericEvent;
import com.notifyme.application.model.User;
import com.notifyme.application.service.RegisterAuthenticationService;
import com.notifyme.application.service.email.EmailSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenericRegistrationListener {
    private final RegisterAuthenticationService registerService;

    
    @Value("${domain.url}")
    private String subdomain;

    @Qualifier("MailtrapService")
    private final EmailSender mailSender;

    public GenericRegistrationListener(RegisterAuthenticationService registerService,
                                       EmailSender mailSender) {
        this.registerService = registerService;
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
        final EmailDetails email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    private EmailDetails constructEmailMessage(final GenericEvent<RegisterEventDTO> event,
                                               final User user,
                                               final String token) {
        final String recipientAddress = user.getEmailAddress();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = subdomain + "/register/confirmEmail/" + token;
        final String message = "You registered successfully. To confirm your " +
                "registration, please click on the below link.";
        final EmailDetails email = new EmailDetails();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " " + confirmationUrl);
        email.setCategory("Registration");
        return email;
    }
}
