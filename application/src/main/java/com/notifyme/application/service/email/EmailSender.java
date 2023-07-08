package com.notifyme.application.service.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSender {
    void send(SimpleMailMessage email);

}
