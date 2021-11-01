package com.medicalChatbots.medicalChatbot.resource;

import com.medicalChatbots.medicalChatbot.model.Discussion;
import com.medicalChatbots.medicalChatbot.model.Message;
import com.medicalChatbots.medicalChatbot.repository.DiscussionRepository;
import com.medicalChatbots.medicalChatbot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class MessageController {

    @Autowired
    private MessageRepository repository ;


    @PostMapping("/addMessage")
    public  String saveMessage(@RequestBody Message message){
        repository.save(message);
        return "Created Message with id : " +  message.getId();



    }


    @GetMapping("/findAllMessages")
    public List<Message> getMessage(){
        return repository.findAll();

    }


    @GetMapping("/findAllMessages/{id}")
    public Optional<Message> getMessage(@PathVariable int id){
        return  repository.findById(id);
    }



    @DeleteMapping("/deleteMessage/{id}")
    public String deleteMessage(@PathVariable int id){
        repository.deleteById(id);
        return "Message Deleted with id " + id ;
    }


}



