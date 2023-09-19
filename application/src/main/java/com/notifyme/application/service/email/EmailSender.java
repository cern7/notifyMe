package com.notifyme.application.service.email;

import com.notifyme.application.dto.EmailDetails;

public interface EmailSender {
    void send(EmailDetails email);

}
