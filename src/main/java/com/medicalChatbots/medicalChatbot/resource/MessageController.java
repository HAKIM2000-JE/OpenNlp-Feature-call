package com.medicalChatbots.medicalChatbot.resource;


import com.medicalChatbots.medicalChatbot.repository.DiscussionRepository;
import com.medicalChatbots.medicalChatbot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private MessageRepository repository ;


}
