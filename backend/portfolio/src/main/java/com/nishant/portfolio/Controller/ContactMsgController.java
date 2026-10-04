package com.nishant.portfolio.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nishant.portfolio.DTO.ContactMsgRequest;
import com.nishant.portfolio.Entity.ContactMsg;
import com.nishant.portfolio.Service.ContactMsgService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContactMsgController {

    private final ContactMsgService contactMsgService;



    @PostMapping("/contact/submit")
    public ContactMsg submitMessage(@Valid @RequestBody ContactMsgRequest contactMsgRequest) {
        return contactMsgService.saveMessage(contactMsgRequest);
    }

}
