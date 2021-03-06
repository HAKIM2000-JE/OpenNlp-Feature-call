package com.medicalChatbots.medicalChatbot.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Getter
@Setter
@ToString
@Document(collection = "Discussion")
public class Discussion {
    @Id
    private int id;
    private String userName;
}
