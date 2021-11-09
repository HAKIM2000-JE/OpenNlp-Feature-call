package com.medicalChatbots.medicalChatbot.resource;

import com.medicalChatbots.medicalChatbot.model.Employee;
import com.medicalChatbots.medicalChatbot.model.Message;
import com.medicalChatbots.medicalChatbot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class EmployeeController {

    @Autowired
    public EmployeeRepository repository ;



    @PostMapping("/addEmployee")
    public  String saveEmployee(@RequestBody Employee employee){
        repository.save(employee);
        return "Created Employee with id : " +  employee.getId();



    }


    @GetMapping("/findAllEmployees")
    public List<Employee> getEmployees(){
        return repository.findAll();

    }



    @GetMapping("/findEmployee/{id}")
    public Optional<Employee> getEmployee(@PathVariable int id){
        return  repository.findById(id);
    }




    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id){
        repository.deleteById(id);
        return "Employee Deleted with id " + id ;
    }


}
