package com.medicalChatbots.medicalChatbot.repository;


import com.medicalChatbots.medicalChatbot.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository

public interface MessageRepository extends MongoRepository<Message, Integer> {


}
