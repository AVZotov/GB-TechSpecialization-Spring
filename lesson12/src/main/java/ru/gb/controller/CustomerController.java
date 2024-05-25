package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.domain.Customer;
import ru.gb.service.CustomerService;
import ru.gb.service.FileGateway;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final FileGateway fileGateway;

    @GetMapping("/get_all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        ResponseEntity<List<Customer>> responseEntity =
                new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);

        fileGateway.writeToFile(
                "get-all-users.txt",
                "clients count in database: %d"
                        .formatted((long) Objects.requireNonNull(responseEntity.getBody()).size()));

        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
    }

    @PostMapping("/add_new")
    public ResponseEntity<?> addNewCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
