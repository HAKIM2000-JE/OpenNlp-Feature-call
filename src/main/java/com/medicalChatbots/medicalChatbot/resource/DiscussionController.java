package com.medicalChatbots.medicalChatbot.resource;

import com.medicalChatbots.medicalChatbot.model.Discussion;
import com.medicalChatbots.medicalChatbot.repository.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class DiscussionController {


     @Autowired
     private DiscussionRepository repository ;


     @PostMapping("/addDiscussion")
     public  String saveDisscuion(@RequestBody Discussion discussion){
          repository.save(discussion);
          return "Created Disscussion with id : " +  discussion.getId();



     }

     @GetMapping("/findAllDisscusions")
     public List<Discussion> getDiscussion(){
          return repository.findAll();

     }

     @GetMapping("/findAllDiscussion/{id}")
     public Optional<Discussion> getDiscussion(@PathVariable int id){
           return  repository.findById(id);
     }


     @DeleteMapping("/deleteDiscussion/{id}")
     public String deleteDiscussion(@PathVariable int id){
          repository.deleteById(id);
          return "Discussion Deleted with id " + id ;
     }
}
