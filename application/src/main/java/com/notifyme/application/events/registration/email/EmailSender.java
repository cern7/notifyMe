package com.notifyme.application.events.registration.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSender {
    void send(SimpleMailMessage email);

}
