package ru.gb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.model.Customer;
import ru.gb.service.DataProcessingService;

import java.util.List;

@RestController
public class TaskController {
    private final DataProcessingService dataProcessingService;

    public TaskController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @GetMapping("filter/{age}")
    public List<Customer> filterCustomersByAge(@PathVariable("age") Integer age){
        return dataProcessingService.filterCustomersByAge(age);
    }

    @GetMapping("/count")
    public Double countAverageAge(){
        return dataProcessingService.countAverageAge();
    }
}
