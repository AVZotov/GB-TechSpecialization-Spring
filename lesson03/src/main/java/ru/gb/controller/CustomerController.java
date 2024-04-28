package ru.gb.controller;

import org.springframework.web.bind.annotation.*;
import ru.gb.model.Customer;
import ru.gb.model.CustomerRegistrationRequest;
import ru.gb.model.CustomerUpdateRequest;
import ru.gb.service.DataProcessingService;
import ru.gb.service.RegistrationService;

import java.util.List;


@RestController
public class CustomerController {
    private final RegistrationService registrationService;
    private final DataProcessingService dataProcessingService;

    public CustomerController(RegistrationService registrationService, DataProcessingService dataProcessingService) {
        this.registrationService = registrationService;
        this.dataProcessingService = dataProcessingService;
    }

    @GetMapping("/customers")
    public List<Customer> getCustomerService(){
        return dataProcessingService.selectAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Long customerId){
        return dataProcessingService.selectCustomerById(customerId);
    }

    @PostMapping("/add")
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        registrationService.registerCustomer(customerRegistrationRequest);
    }

    @PostMapping("add/param")
    @ResponseBody
    public void registerCustomerThroughParams(@RequestParam("name") String name, @RequestParam("age") Integer age){
        registrationService.registerCustomer(new CustomerRegistrationRequest(name, age));
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        dataProcessingService.deleteCustomer(customerId);
    }

    @PutMapping("/customers/{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId,
                               @RequestBody CustomerUpdateRequest customerUpdateRequest){
        dataProcessingService.updateCustomer(customerId, customerUpdateRequest);
    }
}
