package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.domain.Customer;
import ru.gb.domain.CustomerDTO;
import ru.gb.domain.CustomerRegistrationRequest;
import ru.gb.jwt.JwtUtil;
import ru.gb.service.CustomerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO findCustomerById(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        String token = jwtUtil.issueToken(customerRegistrationRequest.email(), "ROLE_USER");
        customerService.addCustomer(customerRegistrationRequest);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);

        return ResponseEntity.ok().build();
    }
}
