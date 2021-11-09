package com.medicalChatbots.medicalChatbot.repository;

import com.medicalChatbots.medicalChatbot.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends MongoRepository<Employee , Integer> {
}
