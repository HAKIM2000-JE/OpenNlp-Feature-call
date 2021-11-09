package com.medicalChatbots.medicalChatbot.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "Employee")
public class Employee {
    private String name;
    @Id
    private int id;
    private int salary;


    public Employee(String name, int id, int salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public void computeSalary(int s, boolean b) {
        if (b == true)
            salary += 2000;
    }

    public void printInfo() {
        System.out.print("Name: " + name + " ID: " + id + " Salary: " + salary );
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


}