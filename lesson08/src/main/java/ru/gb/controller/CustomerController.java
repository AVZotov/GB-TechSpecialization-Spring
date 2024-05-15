package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.domain.Customer;
import ru.gb.service.CustomerService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/customers/")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id){
        return new ResponseEntity<>(customerService.getCustomerById(id), OK);
    }

    @PostMapping
    public ResponseEntity<Void> addNewCustomer(@RequestBody Customer customer){
        customerService.addNewCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody Customer customer){
        customerService.updateCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("id") Long id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok().build();
    }
}
