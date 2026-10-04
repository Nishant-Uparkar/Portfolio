package com.nishant.portfolio.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nishant.portfolio.DTO.ContactMsgRequest;
import com.nishant.portfolio.Entity.ContactMsg;
import com.nishant.portfolio.Repository.ContactMsgRepository;


import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class ContactMsgService {

    private final ContactMsgRepository contactMsgRepository;
    private final EmailService emailService;

    public ContactMsg saveMessage(ContactMsgRequest request) {

        Optional<ContactMsg> latestMessage =
                contactMsgRepository
                        .findTopByEmailOrderBySubmittedAtDesc(request.getEmail());

        if (latestMessage.isPresent()) {

            LocalDateTime lastSubmittedAt =
                    latestMessage.get().getSubmittedAt();

            LocalDateTime nextAllowedTime =
                    lastSubmittedAt.plusDays(7);

            if (LocalDateTime.now().isBefore(nextAllowedTime)) {
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("MMMM dd, yyyy");

                String formattedDate =
                        nextAllowedTime.format(formatter);

                throw new IllegalStateException(
                        "You can submit another message after " + formattedDate
                );
            }
        }

        ContactMsg contactMsg = new ContactMsg();

        contactMsg.setName(request.getName());
        contactMsg.setEmail(request.getEmail());
        contactMsg.setMessage(request.getMessage());
        contactMsg.setSubmittedAt(LocalDateTime.now());

        ContactMsg savedMessage = contactMsgRepository.save(contactMsg);
        emailService.sendContactNotification(savedMessage);

        return savedMessage;
    }


    
}
