package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.domain.Customer;
import ru.gb.service.CustomerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers/")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.selectAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Integer id) {
        return new ResponseEntity<>(customerService.selectCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewCustomer(@RequestBody Customer customer) {
        customerService.insertNewCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        customerService.updateCustomer(id, customer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Integer id) {
        customerService.deleteCustomer(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
