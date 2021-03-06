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

    public Message(int id, int idDisscussion, String text) {

        this.id = id;
        this.idDisscussion = idDisscussion;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getIdDisscussion() {
        return idDisscussion;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdDisscussion(int idDisscussion) {
        this.idDisscussion = idDisscussion;
    }

    public void setText(String text) {
        this.text = text;
    }
}
