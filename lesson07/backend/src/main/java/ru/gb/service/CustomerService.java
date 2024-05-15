package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.domain.Customer;
import ru.gb.domain.CustomerDTO;
import ru.gb.domain.CustomerRegistrationRequest;
import ru.gb.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDtoMapper customerDtoMapper;


    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerDtoMapper)
                .collect(Collectors.toList());
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                passwordEncoder.encode(customerRegistrationRequest.password())
        );
        customerRepository.save(customer);
    }

    public CustomerDTO findCustomerById(Long id) {
        return customerRepository.findCustomerById(id)
                .map(customerDtoMapper)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
