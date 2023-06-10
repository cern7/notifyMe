package com.notifyme.application.registration.listener;


import com.notifyme.application.model.User;
import com.notifyme.application.registration.OnRegistrationCompleteEvent;
import com.notifyme.application.registration.email.EmailSender;
import com.notifyme.application.service.RegisterAuthenticationService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private RegisterAuthenticationService registerService;

    private MessageSource messages;

    private EmailSender mailSender;


    public RegistrationListener(RegisterAuthenticationService registerService,
                                MessageSource messages,
                                EmailSender mailSender) {
        this.registerService = registerService;
        this.messages = messages;
        this.mailSender = mailSender;
    }


    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }


    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        registerService.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event,
                                                          final User user,
                                                          final String token) {
        final String recipientAddress = user.getEmailAddress();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = "http://localhost:5173/register/confirmEmail/" + token;
        final String message = messages.getMessage("message.regSuccLink",
                null,
                "You registered successfully. To confirm your " +
                        "registration, please click on the below link.",
                event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("office@notifyme.com");
        return email;
    }
}
