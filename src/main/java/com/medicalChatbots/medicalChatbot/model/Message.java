package com.medicalChatbots.medicalChatbot.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "Message")
public class Message {
    @Id
    private int id;
    private  int idDisscussion ;
    private String text;

}
