package com.notifyme.application.service.email;


import com.notifyme.application.dto.EmailDetails;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailtrapService implements EmailSender {

    @Value("${mailtrap.token}")
    private String bearerToken;

    @Override
    public void send(EmailDetails email) {
        String URL = "https://send.api.mailtrap.io/api/send";
        String APP_JSON = "application/json";
        String content = String.format(
                "{\"from\":" +
                        "{\"email\":\"office@notifyapp.site\"," +
                        "\"name\":\"NotifyMe App\"}," +
                        "\"to\":[{\"email\":\"%s\"}]," +
                        "\"subject\":\"%s\"," +
                        "\"text\":\"%s\"," +
                        "\"category\":\"%s\"}",
                email.getTo(), email.getSubject(),
                email.getText(), email.getCategory());
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse(APP_JSON);
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(URL)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + bearerToken)
                .addHeader("Content-Type", APP_JSON)
                .build();

        try (Response response = client.newCall(request).execute()) {
            response.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
