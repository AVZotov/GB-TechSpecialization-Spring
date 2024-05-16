package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.aop.annotation.TrackUserAction;
import ru.gb.domain.Customer;
import ru.gb.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @TrackUserAction
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @TrackUserAction
    public Customer getCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer not found")
        );
    }

    @TrackUserAction
    public void addNewCustomer(Customer customer){
        customerRepository.save(customer);
    }

    @TrackUserAction
    public void updateCustomer(Customer customer){
        customerRepository.save(customer);
    }

    @TrackUserAction
    public void deleteCustomerById(Long id){
        customerRepository.deleteById(id);
    }
}
