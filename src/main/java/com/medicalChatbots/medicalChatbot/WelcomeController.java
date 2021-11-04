package com.medicalChatbots.medicalChatbot;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WelcomeController {


    @GetMapping("/welcome")
    public String Welcome(){
        return  "RH chatBot API";
    }
}
