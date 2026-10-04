package com.nishant.portfolio.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nishant.portfolio.Entity.ContactMsg;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;

@Service
public class EmailService {

    private final Resend resend;

    public EmailService(@Value("${RESEND_API_KEY}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    public void sendContactNotification(ContactMsg contactMsg) {

        try {
            CreateEmailOptions email = CreateEmailOptions.builder()
                    .from("Portfolio <onboarding@resend.dev>")
                    .to("uparkar.nishant20022@gmail.com")
                    .subject("New Contact Message - Portfolio")
                    .html("""
                            <h2>New Contact Message</h2>

                            <p><strong>Name:</strong> %s</p>
                            <p><strong>Email:</strong> %s</p>

                            <p><strong>Message:</strong></p>
                            <p>%s</p>
                            """.formatted(
                                    contactMsg.getName(),
                                    contactMsg.getEmail(),
                                    contactMsg.getMessage()
                            ))
                    .build();

            resend.emails().send(email);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email notification", e);
        }
    }
}