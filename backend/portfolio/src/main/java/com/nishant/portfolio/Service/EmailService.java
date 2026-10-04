package com.nishant.portfolio.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nishant.portfolio.Entity.ContactMsg;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendContactNotification(ContactMsg contactMsg) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo("uparkarnishant4@gmail.com");

        mail.setSubject("New Contact Message - Portfolio");

        mail.setText(
                "You received a new message from your portfolio website.\n\n"
                + "Name: " + contactMsg.getName() + "\n"
                + "Email: " + contactMsg.getEmail() + "\n\n"
                + "Message:\n"
                + contactMsg.getMessage()
        );

        mailSender.send(mail);
    }
    
}
