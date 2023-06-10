package com.notifyme.application.registration.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSender {
    void send(SimpleMailMessage email);

}
