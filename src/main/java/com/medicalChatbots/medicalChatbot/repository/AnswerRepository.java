package com.medicalChatbots.medicalChatbot.repository;


import com.medicalChatbots.medicalChatbot.model.Answer;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends MongoRepository<Answer, Integer> {
}
