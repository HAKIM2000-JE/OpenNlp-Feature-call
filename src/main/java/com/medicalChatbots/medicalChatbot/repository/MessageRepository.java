package com.medicalChatbots.medicalChatbot.repository;


import com.medicalChatbots.medicalChatbot.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, Integer> {


}
