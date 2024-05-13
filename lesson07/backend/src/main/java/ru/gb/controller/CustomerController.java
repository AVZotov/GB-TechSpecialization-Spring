package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.domain.Customer;
import ru.gb.domain.CustomerRegistrationRequest;
import ru.gb.jwt.JwtUtil;
import ru.gb.service.CustomerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final JwtUtil jwtUtil;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
        var book = customerService.findCustomerById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("customers/add")
    public ResponseEntity<Customer> addNewCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        return new ResponseEntity<>(customerService.addCustomer(customerRegistrationRequest), HttpStatus.CREATED);
    }

    @PutMapping("customers/add")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        String token = jwtUtil.issueToken(customer.getId().toString(), "ROLE_USER");

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
    }

    @DeleteMapping("customers/delete/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);

        return ResponseEntity.ok().build();
    }
}
