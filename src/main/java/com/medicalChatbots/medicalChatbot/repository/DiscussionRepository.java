package com.medicalChatbots.medicalChatbot.repository;

import com.medicalChatbots.medicalChatbot.model.Discussion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiscussionRepository extends MongoRepository<Discussion, Integer> {


}
